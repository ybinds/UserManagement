package com.app.myco.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.app.myco.binding.EmailReq;
import com.app.myco.binding.LoginReq;
import com.app.myco.binding.UnlockRequest;
import com.app.myco.binding.UserForm;
import com.app.myco.entities.City;
import com.app.myco.entities.Country;
import com.app.myco.entities.State;
import com.app.myco.entities.User;
import com.app.myco.exception.UserNotFoundException;
import com.app.myco.repositories.CityRepository;
import com.app.myco.repositories.CountryRepository;
import com.app.myco.repositories.StateRepository;
import com.app.myco.repositories.UserRepository;
import com.app.myco.util.EmailUtil;

@Service
public class UserServiceImpl implements IUserService  {

	@Autowired
	private UserRepository repo;

	@Autowired
	private CountryRepository crepo;

	@Autowired
	private StateRepository srepo;

	@Autowired
	private CityRepository cirepo;

	@Autowired
	private EmailUtil eutil;

	public String checkUserExists(String email) {
		String msg = "";
		if (email.isEmpty() || email == null)
			msg = "Email cannot be empty";
		else {
			User user = new User();
			user.setUserEmail(email);
			Example<User> example = Example.of(user);
			if (repo.exists(example) == false) {
				msg = "Email is available";
			} else
				msg = "Email is already in use";
		}
		return msg;
	}

	public String createUser(UserForm userForm) {

		User user = new User();

		BeanUtils.copyProperties(userForm, user);
		
		// set country id
		Optional<Country> country = crepo.findById(userForm.getUserCountryId());
		if (country.isPresent()) {
			user.setUserCountryId(country.get());
		}

		// set state id
		Optional<State> state = srepo.findById(userForm.getUserStateId());
		if (state.isPresent()) {
			user.setUserStateId(state.get());
		}

		// set city id
		Optional<City> city = cirepo.findById(userForm.getUserCityId());
		if (city.isPresent()) {
			user.setUserCityId(city.get());
		}
		
		// set password
		String password = generateRandomPassword();
		user.setUserPassword(password);
		
		// set locked status
		user.setIsLocked(true);
		
		// save the user
		repo.save(user);

		// logic to send email if everything goes well
		EmailReq req = new EmailReq();
		req.setEmailFrom("noreply@ies.com");
		req.setEmailTo(user.getUserEmail());
		req.setEmailSubject("Unlock IES Account");
		req.setEmailText(setEmailBody("Register_Email.txt", user));
		eutil.sendEmail(req);
		return "User created successfully";
	}

	public String unlockUser(UnlockRequest request) {
		// logic to test if the user email and password match
		System.out.println(request);
		Optional<User> user = repo.findByUserEmailAndUserPassword(request.getEmail(), request.getOldPassword());
		if (user.isPresent()) {
			User u = user.get();
			u.setUserPassword(request.getNewPassword());
			u.setIsLocked(false);
			repo.save(u);
		} else {
			throw new UserNotFoundException("User not found. Email and Password does not match.");
		}
		return "Account unlocked, please proceed with login";
	}

	public void forgotPassword(String email) {
		Optional<User> user = repo.findByUserEmail(email);
		if (user.isPresent()) {
			User u = user.get();
			// logic to send email
			EmailReq req = new EmailReq();
			req.setEmailFrom("noreply@myuserm.com");
			req.setEmailTo(email);
			req.setEmailSubject("Forgot Password Email");
			req.setEmailText(setEmailBody("Forgot_Password.txt", u));
			eutil.sendEmail(req);
		}
	}

	// method to generate Random password with length 10 characters and Alphanumeric
	// String
	public String generateRandomPassword() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		// Without Java 8 Streams
		/*
		 * StringBuilder sb = new StringBuilder(targetStringLength);
		 * 
		 * for(int i=0; sb.length() < targetStringLength; i++) { int randomInt =
		 * leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit+1));
		 * if((randomInt <= 57 || randomInt >= 65) && (randomInt <=90 || randomInt >=
		 * 97)) sb.appendCodePoint(randomInt); }
		 */

		return generatedString;
	}

	public String userLogin(LoginReq req) {
		String msg = "";
		Optional<User> user = repo.findByUserEmailAndUserPassword(req.getEmail(), req.getPassword());
		if (!user.isPresent()) {
			msg = "Invalid Credentials";
		} else if (user.get().getIsLocked()) {
			msg = "Your account is locked";
		} else {
			msg = "success";
		}
		return msg;
	}

	public String setEmailBody(String filename, User user) {
		StringBuffer sb = new StringBuffer();
		try (Stream<String> lines = Files.lines(Paths.get(filename))) {
			lines.forEach(line -> {
				line = line.replace("${FName}", user.getUserFirstName());
				line = line.replace("${LName}", user.getUserLastName());
				line = line.replace("${Email}", user.getUserEmail());
				line = line.replace("${Pwd}", user.getUserPassword());
				sb.append(line);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
