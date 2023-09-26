package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.exception.APIException;
//import com.example.demo.enums.AccountStatus;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.payload.UserResponse;
import com.example.demo.payload.UserRolesDTO;
import com.example.demo.payload.UserUpdateRequest;

import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
//	private BCryptPasswordEncoder passwordEncoder;
//	public UserService(BCryptPasswordEncoder passwordEncoder) {
//		this.passwordEncoder=passwordEncoder;
//	}
	public User create(User request) {
		return userRepository.save(request);
	}

	public User getUserByUsername(String username) {
		User user = userRepository.findByUsername(username).orElse(null);
		if (user == null) {
			throw new APIException(HttpStatus.NOT_FOUND, "找不到此帳號，請重新登入。");
		}
		return user;
	}

	public UserResponse getUserById(Integer id) {
		User user = userRepository.findById(id).orElse(null);
		if(user==null) {
			throw new APIException(HttpStatus.NOT_FOUND, "找不到此帳號。");
		}
		UserResponse response = new UserResponse();
		response.setId(id);
		response.setUsername(user.getUsername());
		response.setName(user.getName());
		response.setPassword(user.getPassword());
		response.setPhoneNumber(user.getPhoneNumber());
		return response;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public UserResponse updateUser(UserUpdateRequest request) {
		User existingUser = userRepository.findById(request.getId()).orElse(null);
		if (existingUser == null) {
			throw new APIException(HttpStatus.NOT_FOUND, "找不到該用戶");
		}
		
		existingUser.setName(request.getName());
		//existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
		existingUser.setPhoneNumber(request.getPhoneNumber());
		userRepository.save(existingUser);
			
		UserResponse response = new UserResponse();
		response.setId(existingUser.getId());
		response.setUsername(existingUser.getUsername());
		response.setName(existingUser.getName());
		response.setPhoneNumber(existingUser.getPhoneNumber());
		response.setPassword(existingUser.getPassword());
		return response;
	}

//	public User updateUserPassword(Integer id, String password,String newPassword) {
//		User existingUser = getUserById(id);
//		
//		if (existingUser != null) {
//			authService.
//			existingUser.setStatus(status);
//			return userRepository.save(existingUser);
//		}
//		return null;
//	}
//	public User updateUserStatus(Integer id, AccountStatus status) {
//		User existingUser = getUserById(id);
//
//		if (existingUser != null) {
//			existingUser.setStatus(status);
//			return userRepository.save(existingUser);
//		}
//		return null;
//	}
	public User updateUserStatus(Integer id, boolean locked) {
		User existingUser = userRepository.findById(id).orElse(null);

		if (existingUser != null) {
			existingUser.setLocked(locked);
			return userRepository.save(existingUser);
		}
		return null;
	}

	public User getByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	public Integer getIdByUsername(String username) {
		return userRepository.findIdByUsername(username);
	}

	public String getNameByUsername(String username) {
		return userRepository.findNameByUsername(username);
	}

	public void delete(Integer id) {
		userRepository.deleteById(id);
	}

	public boolean authenticateUser(String username, String password) {

		User user = userRepository.findByUsername(username).orElse(null);
		return user != null && user.getPassword().equals(password);
	}

	public Set<Role> getUserRoles(String username) {

		User user = userRepository.findByUsername(username).orElse(null);

		Set<Role> roles = user.getRoles();

//		System.out.println("roles: " + roles);

		return roles;

	}
}