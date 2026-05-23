package com.rating.RatingService.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rating.RatingService.entities.Rating;
import com.rating.RatingService.services.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

        private RatingService ratingService;

        public RatingController(
                        RatingService ratingService) {

                this.ratingService = ratingService;
        }

        // CREATE RATING
        @PreAuthorize("isAuthenticated()")
        @PostMapping
        public ResponseEntity<Rating> createRating(
                        @RequestBody Rating rating) {

                return ResponseEntity

                                .status(HttpStatus.CREATED)

                                .body(
                                                ratingService
                                                                .createRating(rating));
        }

        // GET ALL RATINGS
        @PreAuthorize("isAuthenticated()")
        @GetMapping
        public ResponseEntity<List<Rating>> getAllRatings() {

                return ResponseEntity.ok(
                                ratingService.getAllRatings());
        }

        // GET RATINGS BY USER ID
        @PreAuthorize("isAuthenticated()")
        @GetMapping("/users/{userId}")
        public ResponseEntity<List<Rating>> getRatingByUserId(
                        @PathVariable String userId) {

                return ResponseEntity.ok(
                                ratingService
                                                .getRatingByUserId(userId));
        }

        // GET RATINGS BY HOTEL ID
        @PreAuthorize("isAuthenticated()")
        @GetMapping("/hotels/{hotelId}")
        public ResponseEntity<List<Rating>> getRatingsByHotelId(
                        @PathVariable String hotelId) {

                return ResponseEntity.ok(
                                ratingService
                                                .getRatingsByHotelId(hotelId));
        }

        // UPDATE RATING
        @PreAuthorize("isAuthenticated()")
        @PutMapping("/{ratingId}")
        public ResponseEntity<Rating> updateRating(

                        @PathVariable String ratingId,

                        @RequestBody Rating rating) {

                return ResponseEntity.ok(

                                ratingService.updateRating(
                                                ratingId,
                                                rating));
        }

        // DELETE RATING
        @PreAuthorize("isAuthenticated()")
        @DeleteMapping("/{ratingId}")
        public ResponseEntity<Rating> deleteRating(
                        @PathVariable String ratingId) {

                Rating deletedRating =

                                ratingService
                                                .deleteRating(ratingId);

                return ResponseEntity.ok(
                                deletedRating);
        }
}