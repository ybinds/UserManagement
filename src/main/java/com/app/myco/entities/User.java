package com.app.myco.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	private String userEmail;
	private String userFirstName;
	private String userLastName;

	// I have noticed that passwords are usually char[], I couldn't find answer to
	// why
	// I will wait for any explanation on this
	private String userPassword;

	private Long userPhNo;
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate userDob;
	private String userGender;
	private Boolean isLocked;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country userCountryId;

	@ManyToOne
	@JoinColumn(name = "state_id")
	private State userStateId;

	@ManyToOne
	@JoinColumn(name = "city_id")
	private City userCityId;

}
