package com.example.demo.payload;

import lombok.Data;

@Data
public class LoginDto {
	private String username;
//	private String email;
    private String password;
    private String loginType;
}
