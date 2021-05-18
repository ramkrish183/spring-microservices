package com.rameshj.moviecatalogservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.rameshj.moviecatalogservice.model.CatalogItem;
import com.rameshj.moviecatalogservice.model.Movie;
import com.rameshj.moviecatalogservice.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@RequestMapping("/{userId}")
	@ResponseBody
	public List<CatalogItem> movieRequest(@PathVariable(name = "userId") String userId) {
		
		
		UserRating userRating=restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
		
		//List<Rating> ratings = Arrays.asList(new Rating("1234", 4), new Rating("100", 5));
		return userRating.getRatingList().stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			/*Movie movie=webClientBuilder.build()
			.get()
			.uri("http://movie-info-service/movies/"+ rating.getMovieId())
			.retrieve()
			.bodyToMono(Movie.class)
			.block();*/
			return new CatalogItem(movie.getMovieName(), "Test", rating.getRating());
		}).collect(Collectors.toList());

		//List<CatalogItem> list = Arrays.asList(new CatalogItem("Interstellar", "Test", "5"));
		//return list;
	}
}
