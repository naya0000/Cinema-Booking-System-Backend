package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CustomerOrder;
import com.example.demo.model.Seat;

import jakarta.persistence.Tuple;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

	List<Seat> findAllByIdIn(List<Long> seatIds);

	@Query("SELECT s FROM Seat s " + "WHERE s.movie.id = :movieId AND s.session.id = :sessionId ORDER BY s.seatRow ASC,s.seatNumber ASC")
	List<Seat> findSeatsByMovieIdAndSessionId(@Param("movieId") Integer movieId, @Param("sessionId") Integer sessionId);

	List<Seat> findBySeatRowAndSeatNumberAndMovieIdAndSessionId(String seatRow, String seatNumber, Integer movieId,
			Integer sessionId);
}
