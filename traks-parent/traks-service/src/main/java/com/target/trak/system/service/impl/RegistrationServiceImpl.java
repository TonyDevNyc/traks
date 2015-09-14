package com.target.trak.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.convert.ConversionService;

import com.target.trak.system.entity.User;
import com.target.trak.system.persistence.UserDetailsDao;
import com.target.trak.system.service.BaseTargetTrakService;
import com.target.trak.system.service.RegistrationService;
import com.target.trak.system.service.dto.common.TargetTrakErrorTypeEnum;
import com.target.trak.system.service.dto.security.registration.RegistrationApiRequest;
import com.target.trak.system.service.dto.security.registration.RegistrationApiResponse;
import com.target.trak.system.service.exception.TargetTrakException;
import com.target.trak.system.service.validations.TargetTrakValidationError;
import com.target.trak.system.service.validations.TargetTrakValidator;

public class RegistrationServiceImpl extends BaseTargetTrakService implements RegistrationService {

	private Logger logger = Logger.getLogger(getClass());
	
	private UserDetailsDao userDetailsDao;

	private ConversionService conversionService;
	
	private TargetTrakValidator<RegistrationApiRequest> validator;
	
	@Override
	public RegistrationApiResponse registerUser(final RegistrationApiRequest request) {
		RegistrationApiResponse response = new RegistrationApiResponse();

		try {
			List<TargetTrakValidationError> validationErrors = validateRequest(request);
			
			if (!validationErrors.isEmpty()) {
				response.setSuccess(Boolean.FALSE);
				response.setErrorType(TargetTrakErrorTypeEnum.VALIDATION);
				response.setErrors(validationErrors);
				response.setMessage("A validation error has occurred. Please fix the errors below");
				return response;
			} 
			
			User user = conversionService.convert(request.getUserRegistration(), User.class);
			userDetailsDao.insertUser(user);
			response.setSuccess(Boolean.TRUE);
		} catch (RuntimeException t) {
			logger.error(t.getMessage(), t);
			TargetTrakException exception = generateServiceException(response, null, TargetTrakErrorTypeEnum.ERROR, "An error has occurred processing your request. <br /> If the error still occurs, contact your administrator");
			throw exception;
		}
		return response;
	}

	@Override
	public List<TargetTrakValidationError> validateRequest(final RegistrationApiRequest request)  {
		List<TargetTrakValidationError> validationErrors = new ArrayList<TargetTrakValidationError>();
		validationErrors.addAll(validator.validate(request));
		return validationErrors;
	}

	public void setUserDetailsDao(UserDetailsDao userDetailsDao) {
		this.userDetailsDao = userDetailsDao;
	}

	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	public void setValidator(TargetTrakValidator<RegistrationApiRequest> validator) {
		this.validator = validator;
	}
}