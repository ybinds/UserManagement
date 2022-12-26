package com.app.myco.service;

import com.app.myco.binding.UnlockRequest;
import com.app.myco.entities.User;

public interface IUserService {

	User getUserByEmail(String email);
	String checkUserExists(String email);
	String createUser(User user);
	String unlockUser(UnlockRequest request);
	void forgotPassword(String email);	
}
