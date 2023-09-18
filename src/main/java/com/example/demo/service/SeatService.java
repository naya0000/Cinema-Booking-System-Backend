package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.MovieSeatMapper;
import com.example.demo.mapper.MovieSessionMapper;
import com.example.demo.model.Movie;
import com.example.demo.model.Seat;
import com.example.demo.model.Session;
import com.example.demo.payload.MovieSeatDTO;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.SessionRepository;

@Service
public class SeatService {
	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private MovieSeatMapper movieSeatMapper;

	// available=0:seats are not available, available=1:seats are available
	public List<Seat> updateSeatsAvailable(List<Long> Ids, Integer available) {
		List<Seat> seats = seatRepository.findAllByIdIn(Ids);
		for (Seat seat : seats) {
			seat.setIsAvailable(available);
		}
		return seats;
	}

	public List<Seat> createSeats(MovieSeatDTO request) {
		String row = request.getRow();
		Integer fromSeatNum = request.getSeatNumber().get(0);
		Integer toSeatNum = request.getSeatNumber().get(1);
		Integer movieId = request.getMovieId();
		Integer sessionId = request.getSessionId();
		final Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new NotFoundException("movie not found"));
		final Session session = sessionRepository.findById(sessionId)
				.orElseThrow(() -> new NotFoundException("session not found"));
	
		List<Seat> seats = new ArrayList<Seat>();
		
		for (Integer i = fromSeatNum; i <= toSeatNum; i++) {
			Seat seat = new Seat();
			seat.setSeatNumber(row + i.toString());
			seat.setIsAvailable(1);
			seat.setMovie(movie);
			seat.setSession(session);
			seatRepository.save(seat);
			seats.add(seat);

			System.out.println(i);
		}
		return seats;
	}

	public Collection<Seat> getSeatsByMovieIdAndSessionId(Integer movieId, Integer sessionId) {
		return seatRepository.findSeatsByMovieIdAndSessionId(movieId, sessionId);
	}

}
