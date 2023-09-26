//package com.example.demo.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//@Service
//public class AuthenticationManagerImp implements AuthenticationManager{
//	private UserDetailsService userDetailsService;
//    private PasswordEncoder passwordEncoder;
//
//    public AuthenticationManagerImp(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        this.userDetailsService = userDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		// Get the username and password from the provided authentication object
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//       // Load user details from your user database
//     UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//     System.out.println("testtest");
//     // Compare the provided password with the stored password (after encoding)
//     if (passwordEncoder.matches(password, userDetails.getPassword())) {
//         return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
//     } else {
//         throw new BadCredentialsException("密碼錯誤，請重新登入。");
//     }
//	}
//	
//}
