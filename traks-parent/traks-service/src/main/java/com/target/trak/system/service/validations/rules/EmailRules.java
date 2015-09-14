package com.target.trak.system.service.validations.rules;

import com.target.trak.system.service.validations.TargetTrakValidationError;

public interface EmailRules {

	public TargetTrakValidationError isEmailEmpty(final String email);
	
	public TargetTrakValidationError isEmailValidLength(final String email);
	
	public TargetTrakValidationError isEmailValid(final String email);
	
	public TargetTrakValidationError emailAlreadyExists(final String email);
	
	public TargetTrakValidationError isEmailRegistered(final String email);
}
