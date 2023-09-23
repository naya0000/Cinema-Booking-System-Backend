package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.CustomerOrder;
import com.example.demo.model.Movie;
import com.example.demo.model.Seat;
import com.example.demo.model.Session;
import com.example.demo.payload.UserOrderDTO;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.SessionRepository;

@Mapper(
		componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
		)
public interface UserOrderMapper {
	
	@Mapping(target = "movie", ignore = true)
	@Mapping(target = "sessionDate", ignore = true)
	@Mapping(target = "startTime", ignore = true)
	@Mapping(target = "username", ignore = true)
	@Mapping(target = "seatsNumber", ignore = true)
	@Mapping(target = "seatsRow", ignore = true)
	UserOrderDTO updateUserOrderDTO(CustomerOrder order, @MappingTarget UserOrderDTO userOrderDTO);
	
//	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "movie", ignore = true)
//	@Mapping(target = "session", ignore = true)
//	@Mapping(target = "user", ignore = true)
//	@Mapping(target = "seats", ignore = true)
//	CustomerOrder updateOrder(UserOrderDTO userOrderDTO,@MappingTarget CustomerOrder order, @Context MovieRepository movieRepository,
//			@Context SessionRepository sessionRepository);
	
	@AfterMapping
	default void afterUpdateUserOrderDTO(CustomerOrder order, @MappingTarget UserOrderDTO userOrderDTO) {
		userOrderDTO.setMovie(order.getMovie() == null ? null : order.getMovie().getTitle());
		userOrderDTO.setSessionDate(order.getSession() == null ? null : order.getSession().getSessionDate());
		userOrderDTO.setStartTime(order.getSession() == null ? null : order.getSession().getStartTime());
		userOrderDTO.setUsername(order.getUser() == null ? null : order.getUser().getUsername());
		  // Convert the list of seats to a list of seat_id values
	    List<String> seatsNumber = new ArrayList<>();
	    List<String> seatsRow = new ArrayList<>();
	    if (order.getSeats() != null) {
	        for (Seat seat : order.getSeats()) {
	        	seatsNumber.add(seat.getSeatNumber());
	        	seatsRow.add(seat.getSeatRow());
	        }
	    }
	    userOrderDTO.setSeatsNumber(seatsNumber);
	    userOrderDTO.setSeatsRow(seatsRow);
	}

//	@AfterMapping
//	default void afterUpdateOrder(UserOrderDTO userOrderDTO, @MappingTarget CustomerOrder order,
//			@Context MovieRepository movieRepository,@Context SessionRepository sessionRepository) {
//	    if (userOrderDTO.getMovie() != null && (order.getMovie() == null || !order.getMovie().getId().equals(userOrderDTO.getMovie()))) {
//	        final Movie movie = movieRepository.findById(userOrderDTO.getMovie())
//	                .orElseThrow(() -> new NotFoundException("movie not found"));
//	        order.setMovie(movie);
//	    }
//	    if (userOrderDTO.getSession() != null && (order.getSession() == null || !order.getSession().getId().equals(userOrderDTO.getSession()))) {
//	        final Session session = sessionRepository.findById(userOrderDTO.getSession())
//	                .orElseThrow(() -> new NotFoundException("session not found"));
//	        order.setSession(session);
//	    }
//	}
}
