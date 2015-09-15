package com.target.trak.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.ConversionService;

import com.target.trak.system.dto.common.TargetTrakValidationError;
import com.target.trak.system.dto.security.UserDto;
import com.target.trak.system.dto.security.registration.RegistrationApiRequest;
import com.target.trak.system.dto.security.registration.RegistrationApiResponse;
import com.target.trak.system.entity.User;
import com.target.trak.system.persistence.UserDetailsDao;
import com.target.trak.system.service.exception.TargetTrakException;
import com.target.trak.system.validations.TargetTrakValidator;

public class RegistrationServiceImplTest {

	private Mockery mockery;
	private UserDetailsDao userDetailsDaoMock;
	private ConversionService conversionServiceMock;
	private TargetTrakValidator<RegistrationApiRequest> validatorMock;
	private RegistrationServiceImpl registrationService;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		mockery = new JUnit4Mockery();
		userDetailsDaoMock = mockery.mock(UserDetailsDao.class);
		conversionServiceMock = mockery.mock(ConversionService.class);
		validatorMock = mockery.mock(TargetTrakValidator.class);
		registrationService = new RegistrationServiceImpl();
		registrationService.setConversionService(conversionServiceMock);
		registrationService.setUserDetailsDao(userDetailsDaoMock);
		registrationService.setValidator(validatorMock);
	}

	@Test(expected = TargetTrakException.class)
	public void registerUserWithNullRequestObject() throws Exception {
		final RegistrationApiRequest request = null;
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(throwException(new IllegalArgumentException("API request is null")));
			}
		});
		registrationService.registerUser(request);
	}

	@Test(expected = TargetTrakException.class)
	public void registerUserWithNullUserDto() throws Exception {
		final RegistrationApiRequest request = new RegistrationApiRequest();
		request.setUserRegistration(null);
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(throwException(new IllegalArgumentException("User DTO is null")));
			}
		});
		registrationService.registerUser(request);
	}

	@Test
	public void registerUserWithValidationErrors() {
		final RegistrationApiRequest request = new RegistrationApiRequest();
		request.setUserRegistration(buildUserDto("tony", "tony123", "tony123", "", "Smith", "user@gmail.com", "212-123-1234"));

		final List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		errors.add(buildValidationError("firstName", "FIRST_NAME_001"));

		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(errors));
			}
		});

		RegistrationApiResponse response = registrationService.registerUser(request);
		Assert.assertNotNull("Response is not null", response);
		Assert.assertTrue("Validation Errors is not empty", !response.getErrors().isEmpty());
		Assert.assertFalse("Api call was successful", response.isSuccess());
	}

	@Test
	public void registerUserWithNoValidationErrors() {
		final RegistrationApiRequest request = new RegistrationApiRequest();
		request.setUserRegistration(buildUserDto("tony", "tony123", "tony123", "Tony", "Smith", "user@gmail.com", "212-123-1234"));
		final List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();

		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(errors));
				
				oneOf(conversionServiceMock).convert(with(request.getUserRegistration()), with(equal(User.class)));
				will(returnValue(new User()));
				
				oneOf(userDetailsDaoMock).insertUser(with(any(User.class)));
			}
		});
		
		RegistrationApiResponse response = registrationService.registerUser(request);
		Assert.assertNotNull("Response is not null", response);
		Assert.assertNull("Validation Errors is null", response.getErrors());
		Assert.assertTrue("Api call was successful", response.isSuccess());
	}

	private TargetTrakValidationError buildValidationError(final String fieldName, final String fieldError) {
		return new TargetTrakValidationError(fieldName, fieldError);
	}

	private UserDto buildUserDto(final String username, final String password, final String repeatedPassword, final String firstName, final String lastName, final String email, final String number) {
		UserDto dto = new UserDto();
		dto.setUsername(username);
		dto.setPassword(password);
		dto.setRepeatedPassword(repeatedPassword);
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail(email);
		dto.setTelephoneNumber(number);
		return dto;
	}	
}