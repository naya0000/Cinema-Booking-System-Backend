//package com.example.demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//@Configuration
//@EnableWebMvc
//public class CorsConfig implements WebMvcConfigurer {
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**") // Allow requests from any origin for all endpoints
//				.allowedOrigins("http://localhost:3001") // Replace with your frontend URL
//				.allowedMethods("GET", "POST", "PUT", "DELETE")
//				.allowedHeaders("Authorization", "Content-Type")
//				.allowCredentials(true);
//
//	}
//}
