package com.user.UserService.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.user.UserService.entities.Rating;

@Service
@FeignClient(name = "RATINGSERVICE")
public interface RatingService {

    // get
    @GetMapping("/ratings/users/{userId}")
    Rating[] getRatings(@PathVariable("userId") String userId);

    // create
    @PostMapping("/ratings")
    public ResponseEntity<Rating> createRating(Rating rating);

    // update
    @PutMapping("/ratings/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable("ratingId") String ratingId, Rating rating);

    // delete
    @DeleteMapping("/ratings/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable("ratingId") String ratingId);

}