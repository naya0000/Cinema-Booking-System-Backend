package com.example.demo.payload;

import lombok.Data;

@Data
public class UserResponse {
	private Integer id;
	private String username;
	private String name;
	private String password;
	private String phoneNumber;
	
}
