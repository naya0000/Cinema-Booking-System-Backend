package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Session {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate sessionDate;
    
//    @JsonIgnore 
    @JsonBackReference(value="movie-session")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id") // Specify the name of the foreign key column
    private Movie movie;
    
    @JsonManagedReference(value="session-order")
    @OneToMany(mappedBy = "session")
    private List <CustomerOrder> orders;
    
	@JsonManagedReference(value="session-seat")
	@OneToMany(mappedBy = "session")
    private List <Seat> seats; 
	
}
