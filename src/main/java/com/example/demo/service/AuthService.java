package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import com.example.demo.exception.APIException;
import com.example.demo.model.User;
import com.example.demo.model.Role;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.RegisterDto;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;

@Service
public class AuthService {
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;

	public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public String login(LoginDto loginDto) {
		System.out.println("loginDto:"+loginDto.getUsername());
//		UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
//		System.out.println("UsernamePasswordAuthenticationToken: "+u);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		System.out.println(authentication);
		System.out.println("1");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("2");
		String token = jwtTokenProvider.generateToken(authentication);
		System.out.println("3");
		return token;
	}

	public String register(RegisterDto registerDto) {

		// add check for username exists in database
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new APIException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
		}
//		if (userRepository.existsByEmail(registerDto.getEmail())) {
//			throw new APIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
//		}

		// add check for email exists in database
//		if (userRepository.existsByEmail(registerDto.getEmail())) {
//			throw new APIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
//		}

		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
//		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);

		userRepository.save(user);

		return "User registered successfully!.";
	}

//	public void logout(String token) {
//		 if (JwtTokenProvider.validateToken(token)) {
//		        // The token is valid, so the user can be logged out
//		        return ResponseEntity.ok("Logged out successfully");
//		      } else {
//		        // Invalid token, unauthorized
//		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//		      }
//		
//	}
}
