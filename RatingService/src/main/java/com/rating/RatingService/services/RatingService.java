package com.rating.RatingService.services;

import java.util.List;

import com.rating.RatingService.entities.Rating;

public interface RatingService {

    // create
    public Rating createRating(Rating rating);

    // get all ratings
    List<Rating> getAllRatings();

    // get single rating by UserId
    List<Rating> getRatingByUserId(String id);

    // get all ratings by hotel id
    List<Rating> getRatingsByHotelId(String hotelId);
}
