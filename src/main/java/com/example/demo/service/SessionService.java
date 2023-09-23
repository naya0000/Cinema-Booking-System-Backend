package com.example.demo.service;

import java.util.Collection;
//import java.util.Collection;
import java.util.List;

//import org.apache.commons.collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.exception.APIException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.SeatAlreadyExistsException;
import com.example.demo.exception.SessionAlreadyExistsException;
import com.example.demo.mapper.MovieSessionMapper;
import com.example.demo.model.Movie;
import com.example.demo.model.Seat;
import com.example.demo.model.Session;
import com.example.demo.payload.MovieSessionDTO;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.SessionRepository;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SessionService {
	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private MovieSessionMapper movieSessionMapper;

	public Session createSession(MovieSessionDTO request) {
		Session session = movieSessionMapper.updateSession(request, new Session(), movieRepository);
		Collection<Session> existingSession = sessionRepository.findByStartTimeAndSessionDateAndMovieId(
				session.getStartTime(), session.getSessionDate(), session.getMovie().getId());
		System.out.println("existingSession:" + existingSession.isEmpty());

		if (!existingSession.isEmpty()) {
			throw new SessionAlreadyExistsException(session.getStartTime(), session.getSessionDate(),
					session.getMovie().getId());
		}
		return sessionRepository.save(session);
	}

	public Session updateSession(Integer id, MovieSessionDTO request) {
		Session existingSession = getSessionById(id);

		if (existingSession == null) {
			throw new APIException(HttpStatus.NOT_FOUND, "找不到該場次");
		}
		existingSession.setStartTime(request.getStartTime());
		existingSession.setEndTime(request.getEndTime());
		existingSession.setSessionDate(request.getSessionDate());
		Movie movie = movieRepository.findById(request.getMovieId()).orElse(null);
		if (movie == null) {
			throw new APIException(HttpStatus.NOT_FOUND, "找不到該部電影");
		}
		existingSession.setMovie(movie);
		return sessionRepository.save(existingSession);
	}

	public Session getSessionById(Integer id) {
		return sessionRepository.findById(id).orElse(null);
	}

	public void deleteSession(Integer id) {
		sessionRepository.deleteById(id);
	}

	public List<Session> getAllSessions() {
		return sessionRepository.findAll();
	}

	public Collection<Object[]> getTimeAndDateById(Integer id) {
		return sessionRepository.findTimeAndDateById(id);
	}

	public Collection<MovieSessionDTO> getSessionsByMovieId(Integer movie) {
		return sessionRepository.findByMovieId(movie);
	}
}
