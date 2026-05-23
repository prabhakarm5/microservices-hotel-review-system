package com.user.UserService.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.UserService.entities.Rating;
import com.user.UserService.entities.User;
import com.user.UserService.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

        private final UserService userService;

        private static final Logger logger = LoggerFactory.getLogger(UserController.class);

        public UserController(UserService userService) {

                this.userService = userService;
        }

        // CREATE USER
        @PreAuthorize("isAuthenticated()")
        @PostMapping
        public ResponseEntity<User> createUser(
                        @RequestBody User user) {

                User savedUser = userService.saveUser(user);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(savedUser);
        }

        // RETRY COUNTER
        int retryCount = 1;

        // GET SINGLE USER
        @PreAuthorize("isAuthenticated()")
        @GetMapping("/{userId}")

        @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")

        @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")

        @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")

        public ResponseEntity<User> getSingleUser(
                        @PathVariable String userId) {

                logger.info(
                                "Get Single User Handler: UserController");

                logger.info(
                                "Retry count: {}",
                                retryCount++);

                User user = userService.getUser(userId);

                return ResponseEntity.ok(user);
        }

        // FALLBACK FOR CIRCUIT BREAKER & RETRY
        public ResponseEntity<User> ratingHotelFallback(
                        String userId,
                        Exception ex) {

                ex.printStackTrace();

                logger.info(
                                "Fallback executed because service is down: {}",
                                ex.getMessage());

                User user = User.builder()

                                .userId("1234")

                                .name("Dummy User")

                                .email("dummy@gmail.com")

                                .about(
                                                "This is a dummy user because some service is down")

                                .build();

                return ResponseEntity.ok(user);
        }

        // FALLBACK FOR RATE LIMITER
        public ResponseEntity<User> ratingHotelFallback(
                        String userId,
                        RequestNotPermitted ex) {

                logger.info(
                                "Rate limit exceeded: {}",
                                ex.getMessage());

                User user = User.builder()

                                .userId("RATE_LIMIT")

                                .name("Too Many Requests")

                                .email("limit@gmail.com")

                                .about("API rate limit exceeded")

                                .build();

                return ResponseEntity
                                .status(HttpStatus.TOO_MANY_REQUESTS)
                                .body(user);
        }

        // GET ALL USERS
        @PreAuthorize("isAuthenticated()")
        @GetMapping
        public ResponseEntity<List<User>> getAllUser() {

                List<User> allUsers = userService.getAllUser();

                return ResponseEntity.ok(allUsers);
        }

        // delete user
        // @PreAuthorize("isAuthenticated()")
        // @DeleteMapping("/{userId}")
        // public ResponseEntity<User> deleteUser(
        // @PathVariable String userId) {

        // User deletedUser = userService.deleteUser(userId);

        // return ResponseEntity.ok(deletedUser);
        // }
}