package com.user.UserService.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.user.UserService.entities.Rating;

@FeignClient(name = "RATINGSERVICE")
public interface RatingService {

    // GET RATINGS BY USER ID
    @GetMapping("/ratings/users/{userId}")
    Rating[] getRatings(
            @PathVariable("userId") String userId);

    // CREATE RATING
    @PostMapping("/ratings")
    ResponseEntity<Rating> createRating(
            @RequestBody Rating rating);

    // UPDATE RATING
    @PutMapping("/ratings/{ratingId}")
    ResponseEntity<Rating> updateRating(
            @PathVariable("ratingId") String ratingId,
            @RequestBody Rating rating);

    // DELETE RATING
    @DeleteMapping("/ratings/{ratingId}")
    ResponseEntity<Void> deleteRating(
            @PathVariable("ratingId") String ratingId);
}