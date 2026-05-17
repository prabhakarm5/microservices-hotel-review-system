package com.rating.RatingService.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rating.RatingService.entities.Rating;
import com.rating.RatingService.repositories.RatingRepository;
import com.rating.RatingService.services.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

    public RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating createRating(Rating rating) {

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getRatingByUserId(String id) {
        return ratingRepository.findByUserId(id);
    }

    @Override
    public List<Rating> getRatingsByHotelId(String hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }

    @Override
    public Rating updateRating(String ratingId, Rating rating) {
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id: " + ratingId));

        existingRating.setRating(rating.getRating());
        existingRating.setFeedback(rating.getFeedback());

        return ratingRepository.save(existingRating);
    }

    @Override
    public Rating deleteRating(String ratingId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id: " + ratingId));
        ratingRepository.deleteById(ratingId);
        return rating;
    }

}
