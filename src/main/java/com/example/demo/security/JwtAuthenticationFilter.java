package com.example.demo.security;

import java.io.IOException;
import java.security.Key;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
//	@Autowired
//	private JWTService jwtService;
	
	@Value("${app.jwt-secret}")
    private String jwtSecret;
	
	@Autowired
	private UserDetailsService userDetailsService;

//    public JwtAuthenticationFilter(
//    		//JWTService jwtService, 
//    		UserDetailsService userDetailsService) {
//        //this.jwtService = jwtService;
//        this.userDetailsService = userDetailsService;
//    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		System.out.println("headers:"+request);
		String authHeader = request.getHeader("Authorization");
		System.out.println("hey authHeader:"+authHeader);
	    if (authHeader != null) {
	        String accessToken = authHeader.replace("Bearer ", "");
	        Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());

	        JwtParser parser = Jwts.parserBuilder()
	                .setSigningKey(secretKey)
	                .build();
	        
	        Claims claimsPre = parser
	                .parseClaimsJws(accessToken)
	                .getBody();
	        
	        Map<String, Object> claims=
	        		claimsPre.entrySet().stream()
	                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//	        Map<String, Object> claims = jwtService.parseToken(accessToken);
	        String username = (String) claims.get("username");
	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	        System.out.println("userDetails.getAuthorities(): "+ userDetails.getAuthorities());
	        Authentication authentication =
	                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	    }
	    chain.doFilter(request, response);
//		String accessToken = getTokenFromRequest(request);
//
//		Map<String, Object> claims = jwtService.parseToken(accessToken);
//		String username = (String) claims.get("username");
//		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//		System.out.println("userDetails.getAuthorities():"+userDetails.getAuthorities());
//		
//		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
//				userDetails.getAuthorities());
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);

		
		// get JWT token from http request
//        String token = getTokenFromRequest(request);
//
//        // validate token
//        if(StringUtils.hasText(token) && jwtService.validateToken(token)){
//
//            // get username from token
////            String username = jwtTokenProvider.getUsername(token);
////            System.out.println("username :"+username);
//        	 String username = jwtService.getUsername(token);
//             System.out.println("username :"+username);
//            // load the user associated with token
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                userDetails,
//                userDetails,//null,
//                userDetails.getAuthorities()
//            );
//
//            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//        }
//
	}

//	private String getTokenFromRequest(HttpServletRequest request) {
//
//		String bearerToken = request.getHeader("Authorization");
//
//		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//			return bearerToken.substring(7, bearerToken.length());
//		}
//
//		return null;
//	}

}