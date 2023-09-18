package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Movie;
import com.example.demo.model.Seat;
import com.example.demo.model.User;
import com.example.demo.repository.MovieRepository;

@Service
public class MovieService {
	@Autowired
    private MovieRepository movieRepository;
	
	public Movie createMovie(Movie request) {
		return movieRepository.save(request);
    }
	public Movie updateMovie(Integer id, Movie request) {
		Movie existingMovie = getMovieById(id);

		if (existingMovie!= null) {
			existingMovie.setTitle(request.getTitle());
			existingMovie.setDescription(request.getDescription());
			existingMovie.setReleaseDate(request.getReleaseDate());
			
			return movieRepository.save(existingMovie);
		}
		return null;
	}
	public Movie getMovieById(Integer id) {
		return movieRepository.findById(id).orElse(null);
	}
	public void deleteMovie(Integer id) {
		movieRepository.deleteById(id);
	}
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}
	public List<Seat> getMovieSeats(String title) {
		Movie movie = movieRepository.findByTitle(title).orElse(null);
		if(movie!=null)
			return movie.getSeats();
		return null;
	}
	public String getMovieTitleById(Integer id) {
		return movieRepository.findTitleById(id);
	}
	public Collection<Integer> getMovieByquery(String query) {
		return movieRepository.findByTitleContainingIgnoreCase(query);
	}
	
	
	
	
}
