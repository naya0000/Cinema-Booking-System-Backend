package com.example.demo.payload;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.model.Movie;

import lombok.Data;

@Data
public class MovieSessionDTO {
	private Integer id;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDate sessionDate;
	private Integer movieId;
	public MovieSessionDTO(Integer id,LocalTime startTime,LocalTime endTime,LocalDate sessionDate,Integer movieId) {
	   this.id=id;
	   this.startTime=startTime;
	   this.endTime=endTime;
	   this.sessionDate=sessionDate;
	   this.movieId=movieId;
	}
}
