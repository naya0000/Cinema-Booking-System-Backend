package com.example.demo.service;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.payload.UserRolesDTO;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public User create(User request) {
		return userRepository.save(request);
	}
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}
	public User getUserById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User update(Integer id, User request) {
		User existingUser = getUserById(id);

		if (existingUser != null) {
			existingUser.setName(request.getName());
			existingUser.setUsername(request.getUsername());
			existingUser.setPassword(request.getPassword());
//			existingUser.setEmail(request.getEmail());

			return userRepository.save(existingUser);
		}
		return null;
	}

	public User getByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	public void delete(Integer id) {
		userRepository.deleteById(id);
	}

	public boolean authenticateUser(String username, String password) {

		User user = userRepository.findByUsername(username).orElse(null);
		return user != null && user.getPassword().equals(password);
	}

//	@Transactional
	public Set<Role> getUserRoles(String username) {

		User user = userRepository.findByUsername(username).orElse(null);
		
//		Hibernate.initialize(user.getRoles());
//		System.out.println("user: " + user);

		Set<Role> roles = user.getRoles();
		
//		System.out.println("roles: " + roles);
		
		return roles;

	}
}