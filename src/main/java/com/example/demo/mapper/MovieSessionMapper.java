package com.example.demo.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Movie;
import com.example.demo.model.Session;
import com.example.demo.payload.MovieSessionDTO;
import com.example.demo.repository.MovieRepository;

@Mapper(
		componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
		)
public interface MovieSessionMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "movie", ignore = true)
	@Mapping(target = "orders", ignore = true)
	@Mapping(target = "seats", ignore = true)
	Session updateSession(MovieSessionDTO movieSessionDTO,@MappingTarget Session session,
			@Context MovieRepository movieRepository);
	
	@AfterMapping
	default void afterUpdateSession(MovieSessionDTO movieSessionDTO, @MappingTarget Session session,
			@Context MovieRepository movieRepository) {
	    if (movieSessionDTO.getMovieId() != null && (session.getMovie() == null || !session.getMovie().getId().equals(movieSessionDTO.getMovieId()))) {
	        final Movie movie = movieRepository.findById(movieSessionDTO.getMovieId())
	                .orElseThrow(() -> new NotFoundException("movie not found"));
	        session.setMovie(movie);
	    }
	}
	
	
	
	
	
	
}
