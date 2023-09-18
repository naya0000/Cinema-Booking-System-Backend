package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String seatNumber;

	private int isAvailable;
	
	@JsonBackReference(value="movie-seat")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id") // foreign key column in the Seat table
	private Movie movie;
	

	@JsonBackReference(value="session-seat")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "session_id") // foreign key column in the Seat table
	private Session session;

//	@ManyToMany(mappedBy="seats", fetch = FetchType.LAZY)
//	 private List<CustomerOrder> orders;
	
	@Override
    public String toString() {
        return "Seat {" +
                " id= " + id +
                " seatNumber= "+seatNumber+
                " isAvailable= "+isAvailable+
                '}';
    }
	
}
