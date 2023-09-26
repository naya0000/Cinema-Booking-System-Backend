//package com.example.demo.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import org.springframework.stereotype.Service;
//
//import com.example.demo.exception.APIException;
//import com.example.demo.exception.NotFoundException;
//import com.example.demo.model.User;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.service.UserService;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//	@Autowired
//	private UserService userService;
//
////	public CustomUserDetailsService(UserService userService,UserRepository userRepository) {
////		this.userRepository = userRepository;
////		this.userService=userService;
////	}
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////		try {
////		System.out.println("hi " + username);
//		User user = userService.getUserByUsername(username);
////		User user = userRepository.findByUsername(username);
//		if (user == null) {
//			throw new APIException(HttpStatus.NOT_FOUND, "此帳號未註冊，請重新登入。");
//		}
//		// Check if the account is blocked
////        if (user.isAccountBlocked()) {
////            throw new APIException(HttpStatus.UNAUTHORIZED, "此帳號已被凍結，請聯繫客服。");
////        }
//
//		List<String> roles = user.getRoles().stream()
//								.map(auth -> new String(auth.getName())).collect(Collectors.toList());
////		System.out.println("roles:" + roles);
//		List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
//				.map(auth -> new SimpleGrantedAuthority(auth.getName())).collect(Collectors.toList());
////		System.out.println("authorities:" + authorities);
//		
////		if(roles.contains("ROLE_ADMIN")) {
////			
////		}
//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//				authorities);
//
////		} catch (NotFoundException e) {
////			throw new UsernameNotFoundException("Username is wrong.");
////		} catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
////        }
//
//	}
//}