package com.app.myco.rest;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.myco.binding.LoginReq;
import com.app.myco.binding.UnlockRequest;
import com.app.myco.binding.UserForm;
import com.app.myco.exception.UserNotFoundException;
import com.app.myco.service.IUserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private IUserService service;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	@PostMapping("/login")
	public ResponseEntity<String> userLogin(
			@RequestBody LoginReq req,
			HttpSession session
			) {
		logger.debug("entering into login method");
		String msg = service.userLogin(req);
		if(msg.equals("success")) {
			session.setAttribute("user", req.getEmail());
			msg = "Welcome to Ashok IT....";
		}
		logger.debug("exiting from login method");
		return ResponseEntity.ok(msg);
	}
	
	@GetMapping("/logout")
	public ResponseEntity<String> userLogout(HttpSession session){
		session.invalidate();
		return ResponseEntity.ok("");
	}
	
	@GetMapping("/checkDuplicate/{email}")
	public ResponseEntity<String> checkDuplicateUser(@PathVariable("email") String email){
		return ResponseEntity.ok(service.checkUserExists(email));
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> createUser(
			@RequestBody UserForm userForm){
		return new ResponseEntity<String>(service.createUser(userForm), HttpStatus.CREATED);
	}
	
	@PostMapping("/unlock")
	public ResponseEntity<String> unlockUserAccount(
			@RequestBody UnlockRequest request){
		logger.debug("entering into unlock method");
		ResponseEntity<String> response = null;
		try {
			response = ResponseEntity.ok(service.unlockUser(request));
		} catch(UserNotFoundException unfe) {
			logger.error("Error occurred in unlock method " + unfe.getMessage());
			unfe.printStackTrace();
			throw unfe;
		}
		logger.debug("exiting from unlock method");
		return response;
	}
	
	@GetMapping("/forgotPassword/{email}")
	public ResponseEntity<String> forgotPassword(
			@PathVariable("email") String email){
		service.forgotPassword(email);
		return ResponseEntity.ok("Password is sent to your email");
	}
}
