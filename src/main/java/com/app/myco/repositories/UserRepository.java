package com.app.myco.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.myco.entities.User;

public interface UserRepository extends JpaRepository<User, Serializable> {

	Optional<User> findByUserEmail(String email);
	Optional<User> findByUserEmailAndUserPassword(String email, String password);
}
