package com.target.trak.system.service.validations.rules;

import com.target.trak.system.service.validations.TargetTrakValidationError;

public interface PasswordRules {

	public TargetTrakValidationError isPasswordEmpty(final String password);

	public TargetTrakValidationError repeatedPasswordEmpty(final String repeatedPassword);

	public TargetTrakValidationError isPasswordValidLength(final String password);

	public TargetTrakValidationError passwordEqualsRepeatedPassword(final String password, final String repeatedPassword);
}
