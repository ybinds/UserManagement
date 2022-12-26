package com.app.myco.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.app.myco.binding.UnlockRequest;
import com.app.myco.entities.User;
import com.app.myco.exception.UserNotFoundException;
import com.app.myco.repositories.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

	private UserRepository repo;
	
	public User getUserByEmail(String email) {
		if(email.isEmpty() || email == null || !repo.existsById(email))
			throw new UserNotFoundException("EMAIL '"+ email +"' DOES NOT EXIST");
		else
			return repo.findByUserEmail(email);
	}

	public String checkUserExists(String email) {
		if(email.isEmpty() || email == null) {
			User user = new User();
			user.setUserEmail(email);
			Example<User> example = Example.of(user); 
			if(repo.exists(example) == false) {
				return "Email is available";
			} else
				return "Email is already in use";
		}
		else
			return "Email cannot be empty";
	}
	
	public String createUser(User user) {
		
		// logic to generate random password
		// for now just a test string
		String password = "test"; 
		user.setUserPassword(password);
		repo.save(user);
		
		//logic to send email
		return "User created successfully";
	}

	public String unlockUser(UnlockRequest request) {
		// logic to test if the user email and password match
		Optional<User> user = repo.findByUserEmailAndUserPassword(request.getEmail(), request.getOldPassword());
		if(user.isPresent()) {
			User u = user.get();
			u.setUserPassword(request.getNewPassword());
			u.setIsLocked(false);
			repo.save(u);
		}else {
			throw new UserNotFoundException("User not found. Email and Password does not match.");
		}
		return "User unlocked successfully";
	}

	public void forgotPassword(String email) {
		User user = getUserByEmail(email);
		// logic to send email to user
	}

}
