package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.exception.APIException;
import com.example.demo.payload.LoginDto;
import com.example.demo.service.UserService;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JWTService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

//	public JWTService(AuthenticationManager authenticationManager) {
//	    this.authenticationManager = authenticationManager;
//	}

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	// generate JWT token
	public String generateToken(LoginDto request) {
//    		System.out.println("4");
//    	  Authentication authentication =
//                  new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
////    	  System.out.println("5");
//    	  try {
		Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(),
				request.getPassword());
		authentication = authenticationManager.authenticate(authentication); // 驗證帳密，若錯誤 throw exception
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		if (request.getLoginType().equals(
				userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(""))) {

			System.out.println("userDetails:" + userDetails);
			Date currentDate = new Date();
			Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
			Claims claims = Jwts.claims();
			claims.put("username", userDetails.getUsername());
			claims.setExpiration(expireDate);
			claims.setIssuer("Movie Theater");
			claims.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList()));
			claims.put("userId", userService.getIdByUsername(userDetails.getUsername()));
			claims.put("name", userService.getNameByUsername(userDetails.getUsername()));
			System.out.println("userDetails:" + userDetails);
			Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());

			return Jwts.builder().setClaims(claims).signWith(secretKey).compact();

		} else {
			System.out.println("yo");
			throw new APIException(HttpStatus.UNAUTHORIZED, "此帳號沒有權限，請重新登入。");
		}

//    	  }catch(AuthenticationException e) {
//    		  throw new APIException(HttpStatus.UNAUTHORIZED,e.getMessage());
//    	  }catch (APIException e) {
//    		  throw new APIException(e.getStatus(),e.getMessage());
//    	   }

//    	  System.out.println("6");
//          UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//          System.out.println("userDetails:"+userDetails);
//          Date currentDate = new Date();
//          Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
//          Claims claims = Jwts.claims();
//          claims.put("username", userDetails.getUsername());
//          claims.setExpiration(expireDate);
//          claims.setIssuer("Movie Theater");
//          claims.put("roles", userDetails.getAuthorities().stream()
//        	        .map(GrantedAuthority::getAuthority)
//        	        .collect(Collectors.toList()));
//          
//          Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
//
//          return Jwts.builder()
//                  .setClaims(claims)
//                  .signWith(secretKey)
//                  .compact();
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	// 解析 JWT
	public Map<String, Object> parseToken(String token) {
		Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());

		JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();

		Claims claims = parser.parseClaimsJws(token).getBody();

		return claims.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
}