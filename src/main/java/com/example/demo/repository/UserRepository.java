package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	//User findByUsername(String username);
//	Optional<User> findByUsernameOrEmail(String username, String email);
	Optional<User> findByUsername(String username);
//	Optional<User> findByEmail(String email);
	Boolean existsByUsername(String username);
//    Boolean existsByEmail(String email);

}
