package com.app.myco.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.app.myco.binding.EmailReq;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender emailSender;
	
	public boolean sendEmail(EmailReq req) {
		/*SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom(req.getEmailFrom());
        message.setTo(req.getEmailTo()); 
        message.setSubject(req.getEmailSubject()); 
        message.setText(req.getEmailText());
        emailSender.send(message);*/
		
		boolean isSent = false;
		
		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			
			helper.setTo(req.getEmailTo());
			helper.setSubject(req.getEmailSubject());
			helper.setText(req.getEmailText(), true);
			helper.setFrom(req.getEmailFrom());
			emailSender.send(mimeMessage);
			isSent = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
        return isSent;
	}
}
