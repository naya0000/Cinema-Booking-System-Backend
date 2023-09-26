package com.example.demo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import com.example.demo.enums.AccountStatus;
import com.example.demo.exception.APIException;
//import com.example.demo.model.UserAuthority;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.payload.RegisterDto;
import com.example.demo.payload.ResetPasswordByAdminDTO;
import com.example.demo.payload.ResetPasswordDTO;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTService;

@Service
public class AuthService {
	@Autowired
	private UserService userService;

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JWTService jwtService;

	public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

//	public String login(LoginDto loginDto) {
//		System.out.println("loginDto:" + loginDto.getUsername());

//		Authentication authentication = authenticationManager
//				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
//		System.out.println(authentication.isAuthenticated());

//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		String token = jwtService.generateToken(authentication);
//		
//		return token;
//	}

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
			throw new APIException(HttpStatus.UNAUTHORIZED, "原密碼不正確，請重新輸入。");
		}
	}

	public User resetPasswordByAdmin(ResetPasswordByAdminDTO request) {

		User user = userRepository.findById(request.getId()).orElse(null);
		if (user == null) {
			throw new APIException(HttpStatus.NOT_FOUND, "User not found");
		}
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		return userRepository.save(user);
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
		user.setLocked(false);
		// user.setStatus(AccountStatus.正常);
//		List <UserAuthority> userAuthorities = new ArrayList<UserAuthority>();
//		userAuthorities.add(UserAuthority.USER);
//		user.setAuthorities(userAuthorities);
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get(); // default the user is normal user
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
