package com.example.demo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CustomerOrder;
import com.example.demo.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer>{

	Optional<Movie> findByTitle(String title);
	
	@Query("SELECT m.title FROM Movie m WHERE m.id=:id")
	String findTitleById(@Param("id") Integer id);
	
	@Query("SELECT m.id FROM Movie m WHERE LOWER(m.title) LIKE %:keyword%")
	Collection<Integer> findByTitleContainingIgnoreCase(@Param("keyword") String keyword);
	
	@Query(value = "SELECT * FROM movie ORDER BY CASE WHEN status = 'RELEASED' THEN release_date END DESC, CASE WHEN status = 'UPCOMING' THEN release_date END ASC", nativeQuery = true)
	List<Movie>findAllOrderedByReleaseDate();
	
}
