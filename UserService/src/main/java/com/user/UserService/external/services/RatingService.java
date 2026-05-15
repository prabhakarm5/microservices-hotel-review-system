package com.user.UserService.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user.UserService.entities.Rating;

@FeignClient(name = "RATINGSERVICE")
public interface RatingService {

    @GetMapping("/ratings/users/{userId}")
    Rating[] getRatings(@PathVariable("userId") String userId);
}