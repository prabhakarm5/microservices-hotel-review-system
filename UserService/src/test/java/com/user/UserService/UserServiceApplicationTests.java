package com.user.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.user.UserService.entities.Rating;
import com.user.UserService.external.services.RatingService;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	// @Autowired
	// private RatingService ratingService;

	// @Test
	// void createRating() {
	// Rating rating =
	// Rating.builder().rating(10).userId("").hotelId("").feedback("this is created
	// feign client")
	// .build();
	// ResponseEntity<Rating> response = ratingService.createRating(rating);
	// Rating savedRating = response.getBody();
	// System.out.println("savedRating: " + savedRating);

	// }

	// @Test
	// void updateRating() {
	// Rating rating =
	// Rating.builder().rating(10).userId("").hotelId("").feedback("this is updated
	// design client")
	// .build();
	// ResponseEntity<Rating> response =
	// ratingService.updateRating("6a083e565ce45fc6fa4ab478", rating);

	// Rating updatedRating = response.getBody();
	// System.out.println("updatedRating: " + updatedRating);

	// }

	// @Test
	// void deleteRating() {
	// ResponseEntity<Void> response =
	// ratingService.deleteRating("6a01b154215c4e893ad402c5");
	// System.out.println("delete response status code: " +
	// response.getStatusCode());
	// }

}
