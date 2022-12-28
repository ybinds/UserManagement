package com.app.myco.util;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.app.myco.binding.EmailReq;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender emailSender;
	
	public void sendEmail(EmailReq req) throws AddressException, MessagingException, IOException {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom(req.getEmailFrom());
        message.setTo(req.getEmailTo()); 
        message.setSubject(req.getEmailSubject()); 
        message.setText(req.getEmailText());
        emailSender.send(message);
	}
}
