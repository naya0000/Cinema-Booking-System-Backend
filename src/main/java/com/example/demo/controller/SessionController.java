package com.example.demo.controller;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.example.demo.model.CustomerOrder;
import com.example.demo.model.Session;
import com.example.demo.model.User;
import com.example.demo.payload.MovieSessionDTO;
import com.example.demo.payload.UserOrderDTO;
import com.example.demo.repository.SessionRepository;
import com.example.demo.service.SessionService;

@RestController
@RequestMapping("/sessions")
@CrossOrigin(origins = "*")
public class SessionController {
	@Autowired
	private SessionService sessionService;

	@PostMapping
	public ResponseEntity<Session> createSession(@RequestBody MovieSessionDTO request) {
		Session session = sessionService.createSession(request);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(session.getId())
				.toUri();

		return ResponseEntity.created(location).body(session);// 201 created
	}
	@PutMapping("/{id}")
	public ResponseEntity<Session> updateSession(@PathVariable Integer id, @RequestBody Session request) {

		Session session = sessionService.updateSession(id, request);
		if (session != null)
			return ResponseEntity.ok(session);
		else {
			return ResponseEntity.notFound().build(); // 404 Not Found
			// throw new NotFoundException("User with ID " + id + " not found");
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSession(@PathVariable Integer id) {
		sessionService.deleteSession(id);
		return ResponseEntity.noContent().build(); // 204 No Content
	}
	@GetMapping("/{id}")
	public ResponseEntity<Session> getSessionById(@PathVariable Integer id) {
		Session session = sessionService.getSessionById(id);

		if (session != null) {
			return ResponseEntity.ok(session);
		} else {
			return ResponseEntity.notFound().build(); // 404 Not Found
			// throw new NotFoundException("User with ID " + id + " not found");
		}
	}

	@GetMapping
	public ResponseEntity<List<Session>> getAllSessions() {

		List<Session> sessions = sessionService.getAllSessions();
		return ResponseEntity.ok(sessions);
	}
	@GetMapping("/time/{id}")
	public ResponseEntity<Collection<Object[]> > getTimeAndDateById(@PathVariable Integer id) {

		Collection<Object[]> times = sessionService.getTimeAndDateById(id);
		return ResponseEntity.ok(times);
	}
	@GetMapping("/search")
	public ResponseEntity<Collection <MovieSessionDTO>> getSessionsByMovieId(@RequestParam Integer movie) {

		Collection <MovieSessionDTO> sessions = sessionService.getSessionsByMovieId(movie);
		return ResponseEntity.ok(sessions);
	}

	
}
