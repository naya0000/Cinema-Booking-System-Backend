package com.example.demo.controller;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Movie;
import com.example.demo.model.Seat;
import com.example.demo.model.User;
import com.example.demo.repository.MovieRepository;
import com.example.demo.service.MovieService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@PostMapping
	public ResponseEntity<Movie> createMovie(@RequestBody Movie request) {
		Movie movie = movieService.createMovie(request);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(movie.getId())
				.toUri();
		return ResponseEntity.created(location).body(movie);// 201 created
	}
	@PutMapping("/{id}")
	public ResponseEntity<Movie> updateMovie(@PathVariable Integer id, @RequestBody Movie request) {

		Movie movie = movieService.updateMovie(id, request);
		if (movie != null)
			return ResponseEntity.ok(movie);
		else {
			return ResponseEntity.notFound().build(); // 404 Not Found
			// throw new NotFoundException("User with ID " + id + " not found");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
		movieService.deleteMovie(id);
		return ResponseEntity.noContent().build(); // 204 No Content
	}

	@GetMapping("/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
		Movie movie = movieService.getMovieById(id);

		if (movie != null) {
			return ResponseEntity.ok(movie);
		} else {
			return ResponseEntity.notFound().build(); // 404 Not Found
			// throw new NotFoundException("User with ID " + id + " not found");
		}
	}
	//@PreAuthorize("hasRole('USER')")
	@SecurityRequirement(name = "Bear Authentication")
	@GetMapping
	public ResponseEntity<List<Movie>> getAllMovies() {
		List<Movie> movies = movieService.getAllMovies();
		return ResponseEntity.ok(movies);
	}
	
	@GetMapping("/{title}/seats")
	public ResponseEntity<List<Seat>> getMovieSeats(@PathVariable String title) {
		
		List<Seat> seats = movieService.getMovieSeats(title);
		if(seats!=null)
			return ResponseEntity.ok(seats);
		else
			return ResponseEntity.notFound().build();
	}
	@GetMapping("/title/{id}")
	public ResponseEntity<String> getMovieTitleById(@PathVariable Integer id) {
		String title = movieService.getMovieTitleById(id);

		if (title != null) {
			return ResponseEntity.ok(title);
		} else {
			return ResponseEntity.notFound().build(); // 404 Not Found
			// throw new NotFoundException("User with ID " + id + " not found");
		}
	}
	@GetMapping("/search") // GET `/movies/search?query=${query}`
	public ResponseEntity<Collection<Integer>> searchMovies(@RequestParam String query) {
		Collection<Integer> movie= movieService.getMovieByquery(query);

		if (movie != null) {
			return ResponseEntity.ok(movie);
		} else {
			return ResponseEntity.notFound().build(); // 404 Not Found
			// throw new NotFoundException("User with ID " + id + " not found");
		}
	}
	
}
