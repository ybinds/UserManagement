package com.app.myco.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.myco.entities.User;

public interface UserRepository extends JpaRepository<User, Serializable> {

	User findByUserEmail(String email);
	Optional<User> findByUserEmailAndUserPassword(String email, String password);
	
	@Query("SELECT u.userPassword FROM User u WHERE u.userEmail=:email")
	String getUserPasswordByEmail(String email);
}
