package com.target.trak.system.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.target.trak.system.service.EmailService;

public class EmailServiceImpl implements EmailService {

	private static final Logger logger = Logger.getLogger(EmailServiceImpl.class);
	private JavaMailSender emailSender;

	@Transactional(value = "dwTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public void sendEmailMessage(List<String> recipients, String subject, String message) {
		SimpleMailMessage emailMessage = new SimpleMailMessage();

		String[] recipientArray = new String[recipients.size()];
		recipientArray = recipients.toArray(recipientArray);

		emailMessage.setTo(recipientArray);
		emailMessage.setSubject(subject);
		emailMessage.setText(message);
		emailSender.send(emailMessage);
	}

	public void setEmailSender(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

}
