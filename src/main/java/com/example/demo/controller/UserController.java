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
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.payload.JWTAuthResponse;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.UserRolesDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;

@CrossOrigin(origins = "*") // allow request from any different url (port)
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

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

	
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User request) {

		User user = userService.update(id, request);
		if (user != null)
			return ResponseEntity.ok(user);
		else {
			return ResponseEntity.notFound().build(); // 404 Not Found
			// throw new NotFoundException("User with ID " + id + " not found");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		userService.delete(id);
		return ResponseEntity.noContent().build(); // 204 No Content
	}
	
	@GetMapping("/id")
	public ResponseEntity<User> getUserById(@RequestParam Integer id) {
		User user = userService.getUserById(id);

		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build(); // 404 Not Found
		}
	}
	@GetMapping("/{username}")
	public ResponseEntity <User> getByUsername(@PathVariable String username) {
		 User user =  userService.getByUsername(username);
		 if(user!=null)
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
	public ResponseEntity <Set<Role>> getRolesByUser(@PathVariable String username) {
		System.out.println("username: "+username);
		
		Set<Role>roles =  userService.getUserRoles(username);
		 
		return ResponseEntity.ok(roles);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {

		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

}
