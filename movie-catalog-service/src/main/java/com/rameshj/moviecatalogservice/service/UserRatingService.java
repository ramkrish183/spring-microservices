package com.rameshj.moviecatalogservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rameshj.moviecatalogservice.model.Rating;
import com.rameshj.moviecatalogservice.model.UserRating;

public class UserRatingService {
	
	@Autowired
	private RestTemplate restTemplate;
	
/*	@HystrixCommand(fallbackMethod="getUserRatingsFallback",
	commandProperties= {
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="2000"),
			@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="5"),
			@HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="50"),
			@HystrixProperty(name="circuitbreaker.sleepWindowInMilliseconds",value="5000")
	})	*/
	@HystrixCommand(fallbackMethod="getUserRatingsFallback")
	public UserRating getUserRatings(String userId) {
		UserRating userRating=restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
		return userRating;
	}
	
	public UserRating getUserRatingsFallback(String userId) {
		UserRating userRating=new UserRating();
		Rating rating=new Rating("0", 0);
		userRating.setRatingList(Arrays.asList(rating));
		return userRating;		
		
	}
}
