package com.example.demo.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Movie;
import com.example.demo.model.Seat;
import com.example.demo.payload.MovieSeatDTO;
import com.example.demo.repository.MovieRepository;

@Mapper(
		componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
		)
public interface MovieSeatMapper {
//	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "seatNumber", ignore = true)
//	@Mapping(target = "movie", ignore = true)
//	Seat updateSeat(MovieSeatDTO movieSeatDTO,@MappingTarget Seat seat,
//			@Context MovieRepository movieRepository);
//	
//	@AfterMapping
//	default void afterUpdateSeat(MovieSeatDTO movieSeatDTO, @MappingTarget Seat seat,
//			@Context MovieRepository movieRepository) {
//	    if (movieSeatDTO.getMovieId() != null && (seat.getMovie() == null || !seat.getMovie().getId().equals(movieSeatDTO.getMovieId()))) {
//	        final Movie movie = movieRepository.findById(movieSeatDTO.getMovieId())
//	                .orElseThrow(() -> new NotFoundException("movie not found"));
//	        seat.setMovie(movie);
//	    }
//	}
}
