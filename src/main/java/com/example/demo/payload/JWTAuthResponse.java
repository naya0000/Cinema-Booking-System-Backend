package com.example.demo.payload;

import lombok.Data;

@Data
public class JWTAuthResponse {
	private String accessToken;
	private String tokenType = "Bearer";
}
