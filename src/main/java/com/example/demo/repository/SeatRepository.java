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

//	@Query("SELECT s FROM Seat s " + 
//	       "INNER JOIN Session se ON s.movie.id = se.movie.id " + // Assuming seatNumber is a common field
//		   "INNER JOIN Movie m ON se.movie.id = m.id " + "WHERE m.id = :movieId AND se.id = :sessionId")
	@Query("SELECT s FROM Seat s " +
	           "WHERE s.session.movie.id = :movieId AND s.session.id = :sessionId")
	List<Seat> findSeatsByMovieIdAndSessionId(@Param("movieId") Integer movieId, @Param("sessionId") Integer sessionId);

}
