package com.target.trak.system.service;

import java.util.List;

import com.target.trak.system.dto.common.TargetTrakValidationError;
import com.target.trak.system.dto.security.registration.RegistrationApiRequest;
import com.target.trak.system.dto.security.registration.RegistrationApiResponse;
import com.target.trak.system.validations.TargetTrakValidationException;

public interface RegistrationService {

	public RegistrationApiResponse registerUser(final RegistrationApiRequest request);

	public List<TargetTrakValidationError> validateRequest(final RegistrationApiRequest request) throws TargetTrakValidationException;
}
