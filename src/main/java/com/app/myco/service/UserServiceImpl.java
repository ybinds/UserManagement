package com.app.myco.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.app.myco.binding.EmailReq;
import com.app.myco.binding.LoginReq;
import com.app.myco.binding.UnlockRequest;
import com.app.myco.entities.User;
import com.app.myco.exception.UserNotFoundException;
import com.app.myco.repositories.UserRepository;
import com.app.myco.util.EmailUtil;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private EmailUtil eutil;
	
	public String checkUserExists(String email) {
		String msg = "";
		if(email.isEmpty() || email == null) 
			msg = "Email cannot be empty";
		else {
			User user = new User();
			user.setUserEmail(email);
			Example<User> example = Example.of(user);
			if(repo.exists(example) == false) {
				msg = "Email is available";
			} else
				msg = "Email is already in use";
		}
		return msg;
	}
	
	public String createUser(User user) {
		
		// logic to generate random password
		// for now just a test string
		String password = generateRandomPassword(); 
		user.setUserPassword(password);
		user.setIsLocked(true);
		System.out.println(user);
		repo.save(user);
		
		//logic to send email
		EmailReq req = new EmailReq();
		req.setEmailFrom("noreply@ies.com");
		req.setEmailTo(user.getUserEmail());
		req.setEmailSubject("Unlock IES Account");
		req.setEmailText(
					"Hi "+ user.getUserFirstName() + ", " + user.getUserLastName()+ ":\n"
							+ "Welcome to IES family, your registration is almost complete.\n"
							+ "Please unlock your account using below details.\n"
							+ "Temporary Password:" + password
							+ "\n <a href='http://localhost:4200/unlock/"+user.getUserEmail()+"'>Link to unlock account</a>Thank you,\nTeam"
				);
		try {
			eutil.sendEmail(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "User created successfully";
	}

	public String unlockUser(UnlockRequest request) {
		// logic to test if the user email and password match
		System.out.println(request);
		Optional<User> user = repo.findByUserEmailAndUserPassword(request.getEmail(), request.getOldPassword());
		if(user.isPresent()) {
			User u = user.get();
			u.setUserPassword(request.getNewPassword());
			u.setIsLocked(false);
			repo.save(u);
		}else {
			throw new UserNotFoundException("User not found. Email and Password does not match.");
		}
		return "Account unlocked, please proceed with login";
	}

	public void forgotPassword(String email) {
		String pwd = repo.getUserPasswordByEmail(email);
		System.out.println(pwd);

		//logic to send email
		EmailReq req = new EmailReq();
		req.setEmailFrom("noreply@myuserm.com");
		req.setEmailTo(email);
		req.setEmailSubject("Forgot Password Email");
		req.setEmailText(
					"Dear "+ email + ",\nHere is your Password " + pwd + ".\nThank you,\nTeam"
				);
		try {
			eutil.sendEmail(req);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// method to generate Random password with length 10 characters and Alphanumeric String
	public String generateRandomPassword() {
	    int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    //Without Java 8 Streams
/*	    StringBuilder sb = new StringBuilder(targetStringLength);
	    
	    for(int i=0; sb.length() < targetStringLength; i++) {
	    	int randomInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit+1));
	    	if((randomInt <= 57 || randomInt >= 65) && (randomInt <=90 || randomInt >= 97)) 
	    		sb.appendCodePoint(randomInt);
	    }*/

	    return generatedString;
	}

	public String userLogin(LoginReq req) {
		String msg = "";
		Optional<User> user = repo.findByUserEmailAndUserPassword(req.getEmail(), req.getPassword());
		if(!user.isPresent()) {
			msg = "Invalid Credentials";
		}else if(user.get().getIsLocked()) {
			msg = "Your account is locked";
		} else {
			msg = "success";
		}
		return msg;
	}


}
