package com.rameshj.moviecatalogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rameshj.moviecatalogservice.model.CatalogItem;
import com.rameshj.moviecatalogservice.model.Movie;
import com.rameshj.moviecatalogservice.model.Rating;

public class MovieInfoService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="getMovieInfoFallback")
	public CatalogItem getMovieInfo(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		/*Movie movie=webClientBuilder.build()
		.get()
		.uri("http://movie-info-service/movies/"+ rating.getMovieId())
		.retrieve()
		.bodyToMono(Movie.class)
		.block();*/
		return new CatalogItem(movie.getMovieName(), "Test", rating.getRating());
	}
	
	public CatalogItem getMovieInfoFallback(Rating rating) {
		return new CatalogItem("Fallback Name", "Fallback desc ", rating.getRating());
	}
}
