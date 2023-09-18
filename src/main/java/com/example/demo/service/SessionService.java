package com.example.demo.service;

import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.MovieSessionMapper;
import com.example.demo.model.Session;
import com.example.demo.payload.MovieSessionDTO;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.SessionRepository;

@Service
public class SessionService {
	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private MovieSessionMapper movieSessionMapper;
	
	public Session createSession(MovieSessionDTO request) {
		 Session session = movieSessionMapper.updateSession(request,new Session(),movieRepository);
		return sessionRepository.save(session);
	}
	public Session updateSession(Integer id, Session request) {
		Session existingSession = getSessionById(id);

		if (existingSession != null) {
			existingSession.setStartTime(request.getStartTime());
			existingSession.setEndTime(request.getEndTime());
			existingSession.setSessionDate(request.getSessionDate());
			existingSession.setMovie(request.getMovie());

			return sessionRepository.save(existingSession);
		}
		return null;
	}
	public Session getSessionById(Integer id) {
		return sessionRepository.findById(id).orElse(null);
	}
	
	public void deleteSession(Integer id) {
		sessionRepository.deleteById(id);
	}
	public List <Session> getAllSessions(){
		return sessionRepository.findAll();
	}
	public Collection<Object[]> getTimeAndDateById(Integer id){
		return sessionRepository.findTimeAndDateById(id);
	}
	public Collection <MovieSessionDTO> getSessionsByMovieId(Integer movie) {
		return sessionRepository.findByMovieId(movie);
	}
}
