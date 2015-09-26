package com.target.trak.system.service;

import java.util.List;

public interface EmailService {

	public void sendEmailMessage(List<String> recipients, String subject, String message);
	
}
