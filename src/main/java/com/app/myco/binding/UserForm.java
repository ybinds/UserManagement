package com.app.myco.binding;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserForm {

	private String userEmail;
	private String userFirstName;
	private String userLastName;
	private String userPassword;
	private Long userPhNo;
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate userDob;
	private String userGender;
	private Boolean isLocked;
	private Integer userCountryId;
	private Integer userStateId;
	private Integer userCityId;
}
