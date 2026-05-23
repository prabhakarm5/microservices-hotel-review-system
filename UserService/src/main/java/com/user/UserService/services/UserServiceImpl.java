package com.user.UserService.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.UserService.entities.Hotel;
import com.user.UserService.entities.Rating;
import com.user.UserService.entities.User;
import com.user.UserService.exception.ResourceNotFoundException;
import com.user.UserService.external.services.HotelService;
import com.user.UserService.external.services.RatingService;
import com.user.UserService.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    private final HotelService hotelService;

    private final RatingService ratingService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, RestTemplate restTemplate, HotelService hotelService,
            RatingService ratingService) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.hotelService = hotelService;
        this.ratingService = ratingService;
    }

    @Override
    public User saveUser(User user) {
        // generate unique user id using uuid
        String randomUserid = UUID.randomUUID().toString();
        user.setUserId(randomUserid);
        return userRepository.save(user);
    }

    // get all user
    @Override
    public List<User> getAllUser() {
        // implement Rating Service to get the ratings of the user
        List<User> user = userRepository.findAll();

        // fetch rating of the above user from rating service\

        return user;
    }

    // get single user of given id
    @SuppressWarnings("unchecked")
    @Override
    public User getUser(String userId) {
        // get user from database with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));

        // fetch rating of the above user from rating service
        // api call to rating service to get rating
        // http://localhost:8083/ratings/users/fe68b333-e9a0-4fef-befe-421d3529c95c
        // Rating[] ratingOfUser = restTemplate.getForObject(
        // "http://RATINGSERVICE/ratings/users/" + user.getUserId(),
        // Rating[].class);
        Rating[] ratingOfUser = ratingService.getRatings(user.getUserId());

        List<Rating> ratings = Arrays.stream(ratingOfUser).toList();

        logger.info("Rating for user {}: {}", userId, ratingOfUser);

        List<Rating> ratingList = ratings.stream().map(rating -> {
            // api call to hotel service to get hotel
            // http://localhost:8082/hotels/ + rating.getHotelId()
            // ResponseEntity<Hotel> hotelResponse = restTemplate
            // .getForEntity("http://HOTELSERVICE/hotels/" + rating.getHotelId(),
            // Hotel.class);
            Hotel hotel = hotelService.getHotel(rating.getHotelId().trim());
            // rating.setHotel(hotel);
            // logger.info("response hotel code", hotelResponse.getStatusCode());
            // set the hotel to rating
            rating.setHotel(hotel);
            // return the rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }

    @Override
    public User updateUser(User user, String userId) {
        // get user from database with the help of user repository
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAbout(user.getAbout());

        return userRepository.save(existingUser);
    }

    @Override
    public User deleteUser(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    // @Override
    // public User deleteUser(String userId) {
    // // get user from database with the help of user repository
    // User existingUser = userRepository.findById(userId).orElseThrow(
    // () -> new ResourceNotFoundException("User with given id is not found on
    // server !! : " + userId));

    // userRepository.delete(existingUser);
    // }

}
