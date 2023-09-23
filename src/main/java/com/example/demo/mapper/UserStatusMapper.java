package com.example.demo.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Movie;
import com.example.demo.model.Session;
import com.example.demo.model.User;
import com.example.demo.payload.MovieSessionDTO;
import com.example.demo.payload.UserOrderDTO;
import com.example.demo.payload.UserStatusDTO;
import com.example.demo.repository.MovieRepository;

@Mapper(
		componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
		)
public interface UserStatusMapper {
//	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "username", ignore = true)
//	@Mapping(target = "name", ignore = true)
//	@Mapping(target = "password", ignore = true)
//	@Mapping(target = "orders", ignore = true)
//	@Mapping(target = "roles", ignore = true)
//	User updateUser(UserStatusDTO userStatusDTO, @MappingTarget User user); 
	
	
//	@Mapping(target = "username", ignore = true)
//	@Mapping(target = "name", ignore = true)
//	@Mapping(target = "password", ignore = true)
//	@Mapping(target = "orders", ignore = true)
//	@Mapping(target = "roles", ignore = true)
//	UserStatusDTO updateUserStatusDTO( User user,@MappingTarget UserStatusDTO userStatusDTO); 
	
	@AfterMapping
	default void afterUpdateUser(UserStatusDTO userStatusDTO, @MappingTarget User user) {
//		user.setName(userStatusDTO.getId());
//	    if (userOrderDTO.getMovieId() != null && (session.getMovie() == null || !session.getMovie().getId().equals(movieSessionDTO.getMovieId()))) {
//	        final User user = 
//	        		
//	        		movieRepository.findById(movieSessionDTO.getMovieId())
//	                .orElseThrow(() -> new NotFoundException("movie not found"));
//	        session.setMovie(movie);
//	    }
	}
}
