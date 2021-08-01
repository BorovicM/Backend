package com.iktpreobuka.eDnevnik.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.Util.EmailObject;

@Service
public class EmailServiceImpl implements EmailService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public JavaMailSender emailSender;

	@Override
	public void sendSimpleMessage(EmailObject object) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(object.getTo());
		message.setSubject(object.getSubject());
		message.setText(object.getText());
		
		try {
			emailSender.send(message);
		} catch (MailException ex) {
			logger.error("Problem with sending email to {}. Ex: {}", object.getTo(), ex.getMessage());
			return;
		}
		
		logger.info("Email sent to {} with message {}", object.getTo(), object.getText());
	}
}
