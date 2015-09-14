package com.target.trak.system.validations.rules;

import com.target.trak.system.dto.common.TargetTrakValidationError;

public interface CompanyRules {
	
	public TargetTrakValidationError isCompanyIdEmpty(final Long companyId);

	public TargetTrakValidationError isCompanyNameEmpty(final String companyName);

	public TargetTrakValidationError isCompanyNameValidLength(final String companyName);

	public TargetTrakValidationError companyNameContainsValidChars(final String companyName);

}
