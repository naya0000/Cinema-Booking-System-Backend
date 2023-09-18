package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import com.example.demo.enums.MovieGenre;
import com.example.demo.enums.MovieLevel;
import com.example.demo.enums.MovieStatus;
import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.SeatStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
public class Movie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String title;
	
	private String description;
	
	private LocalDate releaseDate;
	
	@Enumerated(EnumType.STRING)
	private MovieGenre genre;
	
	@Enumerated(EnumType.STRING)
    private MovieStatus status;
	
	@Enumerated(EnumType.STRING)
	private MovieLevel level;
	
	//@JsonIgnore //no serialization: won't get orders data when getAllMovies()
	@JsonManagedReference(value="movie-order")
	@OneToMany(mappedBy = "movie")
    private List <CustomerOrder> orders;
	
//	@JsonIgnore 
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonManagedReference(value="movie-session")
	@OneToMany(mappedBy = "movie")
    private List <Session> sessions; 
	
	@JsonManagedReference(value="movie-seat")
	@OneToMany(mappedBy = "movie")
    private List <Seat> seats; 
	
	private String coverUrl;
}
