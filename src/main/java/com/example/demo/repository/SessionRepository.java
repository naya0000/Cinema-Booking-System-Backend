package com.example.demo.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Session;
import com.example.demo.payload.MovieSessionDTO;
import java.time.LocalDate;
import java.time.LocalTime;
@Repository
public interface SessionRepository extends JpaRepository<Session,Integer>{
	
	@Query("SELECT s.startTime, s.sessionDate FROM Session s WHERE s.id=:id")
	Collection <Object[]> findTimeAndDateById(@Param("id") Integer id);
	
	//JPA Projections
	@Query("SELECT new com.example.demo.payload.MovieSessionDTO (s.id, s.startTime, s.endTime,s.sessionDate,s.movie.id ) FROM Session s WHERE s.movie.id=:id ORDER BY s.sessionDate ASC, s.startTime ASC")
	Collection <MovieSessionDTO> findByMovieId(@Param("id") Integer id);

	Collection<Session>findByStartTimeAndSessionDateAndMovieId(LocalTime startTime, LocalDate sessionDate,Integer movieId);


}
