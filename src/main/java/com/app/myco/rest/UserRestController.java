package com.app.myco.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.myco.binding.UnlockRequest;
import com.app.myco.entities.User;
import com.app.myco.exception.UserNotFoundException;
import com.app.myco.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private IUserService service;
	
	@GetMapping("/checkDuplicate/{email}")
	public ResponseEntity<String> checkDuplicateUser(@PathVariable("email") String email){
		return ResponseEntity.ok(service.checkUserExists(email));
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> createUser(
			@RequestBody User user){
		return ResponseEntity.ok(service.createUser(user));
	}
	
	@PostMapping("/unlock")
	public ResponseEntity<String> unlockUserAccount(
			@RequestBody UnlockRequest request){
		ResponseEntity<String> response = null;
		try {
			response = ResponseEntity.ok(service.unlockUser(request));
		} catch(UserNotFoundException unfe) {
			unfe.printStackTrace();
			throw unfe;
		}
		return response;
	}
	
	@GetMapping("/getPassword/{email}")
	public ResponseEntity<String> forgotPassword(
			@PathVariable("email") String email){
		service.forgotPassword(email);
		return ResponseEntity.ok("Password is sent to your email");
	}
}
