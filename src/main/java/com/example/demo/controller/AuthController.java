package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.JWTAuthResponse;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.RegisterDto;
import com.example.demo.service.AuthService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "*") // allow request from any different url (port)
@RestController
@RequestMapping("/users")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/login") // Log In Account
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
		//System.out.println("loginDto: "+loginDto);
        String token = authService.login(loginDto);
        
        System.out.println("token:" + token);
        
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }
	
//	@SecurityRequirement(name = "Bear Authentication")
	@PostMapping("/logout") // Log Out Account
	public ResponseEntity<String> logout(){
		System.out.println("logout");
        return ResponseEntity.ok("Logged out successfully");
    }
	
	 // Register REST API
    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    
}
