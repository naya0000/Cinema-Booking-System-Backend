package com.example.demo.controller;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Seat;
import com.example.demo.model.Session;
import com.example.demo.payload.MovieSeatDTO;
import com.example.demo.payload.MovieSessionDTO;
import com.example.demo.repository.SeatRepository;
import com.example.demo.service.SeatService;

import jakarta.persistence.Tuple;


@RestController
@RequestMapping("/seats")
@CrossOrigin(origins = "*")
public class SeatController {
	@Autowired 
	private SeatRepository seatRepository;
	@Autowired 
	private SeatService seatService;
	
	@PostMapping
	public ResponseEntity<List<Seat>> createSeats(@RequestBody MovieSeatDTO request) {
		List<Seat> seats = seatService.createSeats(request);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("seats").build()
				.toUri();

		return ResponseEntity.created(location).body(seats);// 201 created
	}
	@GetMapping("/by-ids") // GET /seats/by-ids?ids=1&ids=2&ids=3
	public List <Seat> getSeatByIds(@RequestParam("ids") List<Long> ids) {
		
		return seatRepository.findAllByIdIn(ids);
	}
	@GetMapping("/search")
	public ResponseEntity<Collection <Seat>> getSeatsByMovieIdAndSessionId(@RequestParam Integer movie,@RequestParam Integer session) {
		Collection <Seat> seats = seatService.getSeatsByMovieIdAndSessionId(movie,session);
		return ResponseEntity.ok(seats);
	}
	
	
	
}
