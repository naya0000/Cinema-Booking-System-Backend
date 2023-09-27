package com.example.demo.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.APIException;
import com.example.demo.model.User;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.RegisterDto;
import com.example.demo.payload.ResetPasswordByAdminDTO;
import com.example.demo.payload.ResetPasswordDTO;
import com.example.demo.security.JWTService;
import com.example.demo.service.AuthService;

import lombok.extern.log4j.Log4j2;

//@RequestMapping("/auth")
//@CrossOrigin(origins = "*") // allow request from any different url (port)
@Log4j2
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JWTService jwtService;
	@Autowired
	private AuthService authService;

	@PostMapping("/UserLogin") // Issue Token
	public ResponseEntity<?> UserLogin(@RequestBody LoginDto request) {
		try {
			String token = jwtService.generateToken(request);
			Map<String, String> response = Collections.singletonMap("token", token);
			return ResponseEntity.ok(response);
		} catch (AuthenticationException authException) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(authException.getMessage());
		} catch (APIException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	@Secured("ROLE_ADMIN")
//	@PreAuthorize("hasAnyAuthority('USER')")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/AdminLogin") // Issue Token
	public ResponseEntity<?> AdminLogin(@RequestBody LoginDto request) {
		try {
			String token = jwtService.generateToken(request);
			log.debug(token); //debug, error
			Map<String, String> response =Collections.singletonMap("token", token);
			return ResponseEntity.ok(response);
		} catch (AuthenticationException authException) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("登入失敗，請重新登入。"); //+ authException.getMessage()
		} catch (APIException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}
	@PostMapping("/parse") // parse token
	public ResponseEntity<Map<String, Object>> parseToken(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		Map<String, Object> response = jwtService.parseToken(token);

		return ResponseEntity.ok(response);
	}
	@PutMapping("/password") //reset password
	public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO request){
        User user = authService.resetPassword(request);
        if (user != null) {
            return ResponseEntity.ok("Password reset successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Password reset failed");
        }
    }
	@PutMapping("/password/byAdmin") //reset password
	public ResponseEntity<String> resetPasswordByAdmin(@RequestBody ResetPasswordByAdminDTO request){
        User user = authService.resetPasswordByAdmin(request);
        if (user != null) {
            return ResponseEntity.ok("Password reset successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Password reset failed");
        }
    }
////	@SecurityRequirement(name = "Bear Authentication")
//	@PostMapping("/logout") // Log Out Account
//	public ResponseEntity<String> logout() {
//		System.out.println("logout");
//		return ResponseEntity.ok("Logged out successfully");
//	}

	// Register REST API
	@PostMapping
	public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
		try {
			User user = authService.register(registerDto);
			return ResponseEntity.status(HttpStatus.CREATED).body("註冊成功");
		} catch (APIException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}

}
