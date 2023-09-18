package com.example.demo.payload;

import java.util.List;

import lombok.Data;

@Data
public class MovieSeatDTO {
//	private Long id;
	private String row;
	private List<Integer> seatNumber;
	private int isAvailable;
	private Integer movieId;
	private Integer sessionId;
}
