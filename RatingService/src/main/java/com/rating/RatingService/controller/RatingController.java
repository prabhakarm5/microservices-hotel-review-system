package com.rating.RatingService.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rating.RatingService.entities.Rating;
import com.rating.RatingService.services.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    public RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // create
    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.createRating(rating));
    }

    // get all ratings
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    // get rating by user id
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
    }

    // get ratings by hotel id
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId) {
        return ResponseEntity.ok(ratingService.getRatingsByHotelId(hotelId));
    }
}
