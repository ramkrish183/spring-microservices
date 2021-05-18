package com.rameshj.ratingsdataservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rameshj.ratingsdataservice.model.Rating;
import com.rameshj.ratingsdataservice.model.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingService {
	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable(name = "movieId") String movieId) {
		return new Rating(movieId, 4);
	}

	@RequestMapping("users/{userId}")
	public UserRating getUserRating(@PathVariable(name = "userId") String userId) {
		List<Rating> ratings = Arrays.asList(new Rating("1001", 3), new Rating("1002", 4));
		UserRating userRating=new UserRating();
		userRating.setRatingList(ratings);
		return userRating;

	}

}
