package com.example.demo.payload;

import java.util.List;

import lombok.Data;

@Data
public class MovieSeatDTO {
	//private Long id;
	private String row;// A
	private List<Integer> seatNumber;//[(from)seatNum,(to)seatNum]
	private int isAvailable;
	private Integer movieId;
	private Integer sessionId;
}
