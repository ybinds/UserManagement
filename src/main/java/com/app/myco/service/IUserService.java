package com.app.myco.service;

import com.app.myco.binding.LoginReq;
import com.app.myco.binding.UnlockRequest;
import com.app.myco.binding.UserForm;

public interface IUserService {

	String checkUserExists(String email);
	String createUser(UserForm userForm);
	String unlockUser(UnlockRequest request);
	void forgotPassword(String email);
	String userLogin(LoginReq req);
}
