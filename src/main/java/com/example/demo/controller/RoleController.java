package com.example.demo.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.RoleService;

@CrossOrigin(origins = "*") // allow request from any different url (port)
@RestController
@RequestMapping("/roles")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
	
	@GetMapping
	public ResponseEntity <List<Role>> getAllRoles() {
		List <Role> roles = roleService.getAllRoles();
		return ResponseEntity.ok(roles);
	}


}
