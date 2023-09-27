package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.exception.APIException;
import com.example.demo.model.Movie;
import com.example.demo.model.Seat;
import com.example.demo.model.User;
import com.example.demo.payload.MovieStatusDTO;
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
			BeanUtils.copyProperties(request, existingMovie);
//			existingMovie.setTitle(request.getTitle());
//			existingMovie.setDescription(request.getDescription());
//			existingMovie.setReleaseDate(request.getReleaseDate());
//			existingMovie.setGenre(request.getGenre());
//			existingMovie.setStatus(request.getStatus());
//			existingMovie.setLevel(request.getLevel());
//			existingMovie.setCoverUrl(request.getCoverUrl());
			return movieRepository.save(existingMovie);
		}
		return null;
	}
	public Movie updateMovieStatus(MovieStatusDTO request) {
		Movie existingMovie = getMovieById(request.getId());
		if(existingMovie==null) {
			throw new APIException(HttpStatus.NOT_FOUND, "找不到該部電影");
		}
		existingMovie.setStatus(request.getStatus());
		return movieRepository.save(existingMovie);
	}
	public Movie getMovieById(Integer id) {
		return movieRepository.findById(id).orElse(null);
	}
	public void deleteMovie(Integer id) {
		movieRepository.deleteById(id);
	}
	public List<Movie> getAllMovies() {
		return movieRepository.findAllOrderedByReleaseDate();
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
