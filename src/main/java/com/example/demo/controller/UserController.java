package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;

import com.example.demo.exception.APIException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.payload.JWTAuthResponse;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.UserResponse;
//import com.example.demo.payload.UserPasswordDTO;
import com.example.demo.payload.UserRolesDTO;
import com.example.demo.payload.UserStatusDTO;
import com.example.demo.payload.UserUpdateRequest;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;

@Log4j2
@CrossOrigin(origins = "*") // allow request from any different url (port)
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	@PostMapping("/delete") //delete user by Id
	public ResponseEntity<?> deleteUser(@RequestBody Integer id){
		try {
			System.out.println(id);
			log.error(id);
			userService.deleteUser(id);
			return ResponseEntity.ok("");
		} catch (APIException e) {
			return ResponseEntity.notFound().build(); // 404 Not Found
			// throw new NotFoundException("User with ID " + id + " not found");
		} 
	}
//	@PostMapping
//	public ResponseEntity<User> create(@RequestBody User request) {
//		User user = userService.create(request);
//
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
//				.toUri();
//
//		return ResponseEntity.created(location).body(user);// 201 created
//	}

//	@PostMapping("/login") // Log In Account
//	public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
//		String username = request.get("username");
//		String password = request.get("password");
//
//		if (userService.authenticateUser(username, password)) {
//			return ResponseEntity.ok("Login successful");
//		} else {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//			// return ResponseEntity.ok("Login failed");
//		}
//	}
//	@PutMapping("/password") 
//	public ResponseEntity<User> updateUserPassword(@RequestBody UserPasswordDTO request) {
//
//		User user = userService.updateUserPassword(request.getId(),request.getPassword(),request.getNewPassword());
//		if (user != null)
//			return ResponseEntity.ok(user);
//		else {
//			return ResponseEntity.notFound().build(); // 404 Not Found
//			// throw new NotFoundException("User with ID " + id + " not found");
//		}
//	}

//	@PutMapping("/{id}") 
//	public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User request) {
//
//		User user = userService.updateUser(id, request);
//		if (user != null)
//			return ResponseEntity.ok(user);
//		else {
//			return ResponseEntity.notFound().build(); // 404 Not Found
//			// throw new NotFoundException("User with ID " + id + " not found");
//		}
//	}
	@PutMapping("/id")
	public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest request) {
		
		try {
			UserResponse response = userService.updateUser(request);
			return ResponseEntity.ok(response);
		} catch (APIException e) {
			return ResponseEntity.notFound().build(); // 404 Not Found
			// throw new NotFoundException("User with ID " + id + " not found");
		} 
	}

//	@PutMapping("/status") 
//	public ResponseEntity<User> updateUserStatus(@RequestBody UserStatusDTO request) {
//		
//		User user = userService.updateUserStatus(request.getId(),request.getStatus());
//		if (user != null)
//			return ResponseEntity.ok(user);
//		else {
//			return ResponseEntity.notFound().build(); // 404 Not Found
//			// throw new NotFoundException("User with ID " + id + " not found");
//		}
//	}
	@PutMapping("/status")
	public ResponseEntity<User> updateUserStatus(@RequestBody UserStatusDTO request) {

		User user = userService.updateUserStatus(request.getId(), request.isLocked());
		if (user != null)
			return ResponseEntity.ok(user);
		else {
			return ResponseEntity.notFound().build(); // 404 Not Found
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		userService.delete(id);
		return ResponseEntity.noContent().build(); // 204 No Content
	}
	@PostMapping("/id") 
	public ResponseEntity<?> getUserById(@RequestBody Integer id) {
		try {
			UserResponse response = userService.getUserById(id);
			return ResponseEntity.ok(response);
		} catch (APIException e) {
			return ResponseEntity.notFound().build(); // 404 Not Found
			// throw new NotFoundException("User with ID " + id + " not found");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}
//	@PostMapping("/id") // GET `${api}/users/id?id=${id}`
//	public ResponseEntity<User> getUserById(@RequestBody Integer id) {
//		User user = userService.getUserById(id);
//
//		if (user != null) {
//			return ResponseEntity.ok(user);
//		} else {
//			return ResponseEntity.notFound().build(); // 404 Not Found
//		}
//	}

	@GetMapping("/{username}")
	public ResponseEntity<User> getByUsername(@PathVariable String username) {
		User user = userService.getByUsername(username);
		if (user != null)
			return ResponseEntity.ok(user);
		else
			return ResponseEntity.notFound().build(); // 404 Not Found
	}

//	@GetMapping("/{username}/roles")
//	public ResponseEntity <UserRolesDTO> getRolesByUser(@PathVariable String username) {
//		System.out.println("username: "+username);
//		
//		UserRolesDTO dto =  userService.getUserRolesByUsername(username);
//		 
//		return ResponseEntity.ok(dto);
//	}

	@GetMapping("/{username}/roles")
	public ResponseEntity<Set<Role>> getRolesByUser(@PathVariable String username) {
		System.out.println("username: " + username);

		Set<Role> roles = userService.getUserRoles(username);

		return ResponseEntity.ok(roles);
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {

		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

}
