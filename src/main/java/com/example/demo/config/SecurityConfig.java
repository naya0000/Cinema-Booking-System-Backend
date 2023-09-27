package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.example.demo.security.JwtAuthenticationFilter;

//import com.example.demo.security.JwtAuthenticationFilter;

//import com.example.demo.security.JwtAuthenticationEntryPoint;
//import com.example.demo.security.JwtAuthenticationFilter;

//import io.swagger.v3.oas.annotations.security.SecurityScheme;
//import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;


//@SecurityScheme(name = "Bear Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig {
	
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	private UserDetailsService userDetailsService;
	
	public SecurityConfig(
			JwtAuthenticationFilter jwtAuthenticationFilter,
			UserDetailsService userDetailsService) {
	    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	    this.userDetailsService = userDetailsService;
	}
//	@Autowired
//	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

//	 @Bean
//	    public AuthenticationManager authenticationManagerBean() throws Exception {
//	        return super.authenticationManagerBean();
//	    }
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests(authorize -> authorize
						//.requestMatchers("/users/movies/**").hasRole("USER")
						.requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
//						.requestMatchers("/auth/UserLogin/**").permitAll()
//						.requestMatchers("/auth/logout/**").permitAll()
//						.requestMatchers("/auth/AdminLogin/**").permitAll()
						.requestMatchers(HttpMethod.GET,"/movies/**").permitAll()
//						.requestMatchers(HttpMethod.GET,"/seats/**").authenticated()
						.requestMatchers(HttpMethod.POST,"/movies/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT,"/movies/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE,"/movies/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET,"/orders/**").hasRole("ADMIN")
//						.requestMatchers(HttpMethod.PUT,"/orders/canceledStatus/**").hasRole("USER")
//						.requestMatchers(HttpMethod.PUT,"/orders/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET,"/sessions/**").permitAll()
						.requestMatchers(HttpMethod.POST,"/sessions/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT,"/sessions/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE,"/sessions/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST,"/seats/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT,"/seats/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE,"/seats/**").hasRole("ADMIN")
//						.requestMatchers(HttpMethod.PUT,"/users/**").hasRole("USER")
//						.requestMatchers(HttpMethod.PUT,"/users/?*").hasRole("USER")
						.requestMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT,"/users/status").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET,"/users/?*").permitAll()
						//.requestMatchers(HttpMethod.PUT, "/auth/password").hasRole("USER")
						
						//.requestMatchers(HttpMethod.GET, "/movies/**").permitAll()
						//.requestMatchers(HttpMethod.POST, "/seats/**").authenticated()
						//.anyRequest().permitAll()	
						//.requestMatchers(HttpMethod.GET, "/movies/**").hasRole("ADMIN")
						//.requestMatchers(HttpMethod.GET, "/orders/**").hasRole("ADMIN")
						.anyRequest().authenticated()
		                .and()
		                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
						
		                //.anyRequest().permitAll()	
						);

		return http.build();
	}
//	@Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("*"); // Replace with the appropriate origin(s)
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedHeader("Authorization"); // Allow the "Authorization" header
//        configuration.setAllowCredentials(true);
//
//        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }

}
