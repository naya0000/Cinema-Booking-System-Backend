package com.example.demo.controller;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.demo.exception.APIException;
import com.example.demo.model.Seat;
import com.example.demo.model.Session;
import com.example.demo.model.User;
import com.example.demo.payload.MovieSeatDTO;
import com.example.demo.payload.MovieSessionDTO;
import com.example.demo.payload.UpdateSeatDTO;
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
	public ResponseEntity<?> createSeats(@RequestBody MovieSeatDTO request) {
		try {
			List<Seat> seats = seatService.createSeats(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(seats);// 201
        } catch (APIException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
//		List<Seat> seats = seatService.createSeats(request);
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("seats").build().toUri();
//		return ResponseEntity.created(location).body(seats); // 201 created
	}
	@PutMapping
	public ResponseEntity<?> updateSeatAvailable(@RequestBody UpdateSeatDTO request) {
		try {
            Seat seat =  seatService.updateSeatAvailable(request.getId(),request.getIsAvailable());
            return ResponseEntity.status(HttpStatus.OK).body(seat.getSeatRow()+seat.getSeatNumber()+"座位更新成功");
        } catch (APIException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
	}
	@GetMapping("/by-ids") // GET /seats/by-ids?ids=1&ids=2&ids=3
	public List<Seat> getSeatByIds(@RequestParam("ids") List<Long> ids) {

		return seatRepository.findAllByIdIn(ids);
	}

	@GetMapping("/search")
	public ResponseEntity<Collection<Seat>> getSeatsByMovieIdAndSessionId(@RequestParam Integer movie,
			@RequestParam Integer session) {
		Collection<Seat> seats = seatService.getSeatsByMovieIdAndSessionId(movie, session);
		return ResponseEntity.ok(seats);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSeat(@PathVariable Long id) {
		try {
            seatRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("刪除座位成功");
        } catch (APIException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
	}
}
