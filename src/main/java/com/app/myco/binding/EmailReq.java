package com.app.myco.binding;

import lombok.Data;

@Data
public class EmailReq {

	private String emailTo;
	private String emailFrom;
	private String emailSubject;
	private String emailText;
}
