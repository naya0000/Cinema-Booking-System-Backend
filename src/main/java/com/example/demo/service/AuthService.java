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

import com.example.demo.enums.AccountStatus;
import com.example.demo.exception.APIException;
import com.example.demo.model.User;
import com.example.demo.model.Role;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.RegisterDto;
import com.example.demo.payload.ResetPasswordDTO;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;

@Service
public class AuthService {
	@Autowired
	private UserService userService;

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
		System.out.println("loginDto:" + loginDto.getUsername());
//		UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
//		System.out.println("UsernamePasswordAuthenticationToken: "+u);
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		System.out.println(authentication.isAuthenticated());
//		System.out.println(SecurityContextHolder.getContext());
		System.out.println("1");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("2");
		String token = jwtTokenProvider.generateToken(authentication);
		
		System.out.println("3");
		return token;
	}

	public User resetPassword(ResetPasswordDTO request) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		if (authentication.isAuthenticated()) {
			User user = userRepository.findById(request.getId()).orElse(null);
			if (user == null) {
				throw new APIException(HttpStatus.NOT_FOUND, "User not found");
			}
			user.setPassword(passwordEncoder.encode(request.getNewPassword()));
			return userRepository.save(user);
		} else { // Authentication failed (incorrect old password)
			throw new APIException(HttpStatus.UNAUTHORIZED, "Authentication failed");
		}
	}

	public User register(RegisterDto registerDto) {

		// add check for username exists in database
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			System.out.println("此帳號已被註冊");
			throw new APIException(HttpStatus.BAD_REQUEST, "此帳號已被註冊，請改用其他電子郵件。");
		}

		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setPhoneNumber(registerDto.getPhoneNumber());
		user.setStatus(AccountStatus.正常);
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);
		

		return userRepository.save(user);
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
