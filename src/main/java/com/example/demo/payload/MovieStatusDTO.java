package com.example.demo.payload;

import com.example.demo.enums.MovieStatus;

import lombok.Data;

@Data
public class MovieStatusDTO {
	private Integer id;
	private MovieStatus status;
}
