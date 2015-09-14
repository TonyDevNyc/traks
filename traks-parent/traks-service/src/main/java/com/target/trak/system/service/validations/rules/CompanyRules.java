package com.target.trak.system.service.validations.rules;

import com.target.trak.system.service.validations.TargetTrakValidationError;

public interface CompanyRules {
	
	public TargetTrakValidationError isCompanyIdEmpty(final Long companyId);

	public TargetTrakValidationError isCompanyNameEmpty(final String companyName);

	public TargetTrakValidationError isCompanyNameValidLength(final String companyName);

	public TargetTrakValidationError companyNameContainsValidChars(final String companyName);

}
