package com.example.demo.payload;


import lombok.Data;

@Data
public class ResetPasswordDTO {
	private Integer id;
	private String username;
	private String password;
	private String newPassword;
}
