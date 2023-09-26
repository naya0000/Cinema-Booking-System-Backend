package com.example.demo.payload;

import lombok.Data;

@Data
public class UserUpdateRequest {
	private Integer id;
	private String name;
	private String password;
	private String phoneNumber;
}
