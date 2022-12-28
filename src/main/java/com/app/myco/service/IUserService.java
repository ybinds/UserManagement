package com.app.myco.service;

import com.app.myco.binding.LoginReq;
import com.app.myco.binding.UnlockRequest;
import com.app.myco.entities.User;

public interface IUserService {

	String checkUserExists(String email);
	String createUser(User user);
	String unlockUser(UnlockRequest request);
	void forgotPassword(String email);
	String userLogin(LoginReq req);
}
