package com.target.trak.system.validations.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.target.trak.system.dto.common.TargetTrakValidationError;
import com.target.trak.system.dto.contact.ContactApiRequest;
import com.target.trak.system.dto.contact.ContactDto;
import com.target.trak.system.validations.TargetTrakValidator;
import com.target.trak.system.validations.rules.ContactRules;
import com.target.trak.system.validations.rules.EmailRules;
import com.target.trak.system.validations.rules.FirstNameRules;
import com.target.trak.system.validations.rules.LastNameRules;
import com.target.trak.system.validations.rules.PhoneNumberRules;

public class ContactValidatorImpl implements TargetTrakValidator<ContactApiRequest> {

	private FirstNameRules firstNameRules;

	private LastNameRules lastNameRules;

	private EmailRules emailRules;

	private PhoneNumberRules telephoneNumberRules;

	private ContactRules contactRules;

	@Override
	public List<TargetTrakValidationError> validate(final ContactApiRequest request) {
		List<TargetTrakValidationError> validationErrors = new ArrayList<TargetTrakValidationError>();

		if (request == null) {
			throw new IllegalArgumentException("API request is null");
		}

		final ContactDto contactDto = request.getContact();

		switch (request.getRequestType()) {
			case CREATE:
				validationErrors.addAll(validateCreate(contactDto));
				break;
			case UPDATE:
				validationErrors.addAll(validateUpdate(contactDto));
				break;
			default:
				System.out.println("No implementation available");
				break;
			}
		validationErrors.removeAll(Collections.singleton(null));
		return validationErrors;
	}
	
	private List<TargetTrakValidationError> validateCreate(final ContactDto contactDto) {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		errors.addAll(validateContactType(contactDto.getContactType()));
		errors.addAll(validateTitle(contactDto.getTitle()));
		errors.addAll(validateFirstName(contactDto.getFirstName()));
		errors.addAll(validateLastName(contactDto.getLastName()));
		errors.addAll(validateMiddleInitial(contactDto.getMiddleInitial()));
		errors.addAll(validateSuffix(contactDto.getSuffix()));
		errors.addAll(validateTelephoneNumber(contactDto.getTelephoneNumber()));
		errors.addAll(validateEmail(contactDto.getEmailAddress()));
		errors.addAll(validateCompany(contactDto.getCompany().getId()));
		return errors;
	}
	
	private List<TargetTrakValidationError> validateUpdate(final ContactDto contactDto) {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		errors.addAll(validateId(contactDto.getId()));
		errors.addAll(validateContactType(contactDto.getContactType()));
		errors.addAll(validateTitle(contactDto.getTitle()));
		errors.addAll(validateFirstName(contactDto.getFirstName()));
		errors.addAll(validateLastName(contactDto.getLastName()));
		errors.addAll(validateMiddleInitial(contactDto.getMiddleInitial()));
		errors.addAll(validateSuffix(contactDto.getSuffix()));
		errors.addAll(validateTelephoneNumber(contactDto.getTelephoneNumber()));
		errors.addAll(validateEmail(contactDto.getEmailAddress()));
		errors.addAll(validateCompany(contactDto.getCompany().getId()));
		return errors;
	}

	private List<TargetTrakValidationError> validateId(final Long id) {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		errors.add(contactRules.isIdEmpty(id));
		return errors;
	}

	private List<TargetTrakValidationError> validateContactType(final String contactType) {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		TargetTrakValidationError error = contactRules.isContactTypeEmpty(contactType);
		if (error == null) {
			errors.add(contactRules.isContactTypeValidLength(contactType));
			errors.add(contactRules.contactTypeContainsAllowableChars(contactType));
		} else {
			errors.add(error);
		}
		return errors;
	}

	private List<TargetTrakValidationError> validateTitle(final String title) {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		if (!StringUtils.isEmpty(title)) {
			errors.add(contactRules.isTitleValidLength(title));
			errors.add(contactRules.titleContainsAllowableChars(title));
		}
		return errors;
	}

	private List<TargetTrakValidationError> validateMiddleInitial(final String middleInitial) {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		if (!StringUtils.isEmpty(middleInitial)) {
			errors.add(contactRules.isMiddleInitialValidLength(middleInitial));
			errors.add(contactRules.middleInitialContainsAllowableChar(middleInitial));
		}
		return errors;
	}

	private List<TargetTrakValidationError> validateSuffix(final String suffix) {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		if (!StringUtils.isEmpty(suffix)) {
			errors.add(contactRules.isSuffixValidLength(suffix));
			errors.add(contactRules.suffixContainsAllowableChar(suffix));
		}
		return errors;
	}

	private List<TargetTrakValidationError> validateFirstName(final String firstName) {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		TargetTrakValidationError error = firstNameRules.isFirstNameEmpty(firstName);
		if (error == null) {
			errors.add(firstNameRules.isFirstNameValidLength(firstName));
			errors.add(firstNameRules.firstNameIsAlphabeticOnly(firstName));
		} else {
			errors.add(error);
		}
		return errors;
	}

	private List<TargetTrakValidationError> validateLastName(final String lastName) {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		TargetTrakValidationError error = lastNameRules.isLastNameEmpty(lastName);
		if (error == null) {
			errors.add(lastNameRules.isLastNameValidLength(lastName));
			errors.add(lastNameRules.lastNameIsAlphabeticOnly(lastName));
		} else {
			errors.add(error);
		}
		return errors;
	}

	private List<TargetTrakValidationError> validateEmail(final String email) {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		TargetTrakValidationError error = emailRules.isEmailEmpty(email);
		if (error == null) {
			errors.add(emailRules.isEmailValidLength(email));
			errors.add(emailRules.isEmailValid(email));
		} else {
			errors.add(error);
		}
		return errors;
	}

	private List<TargetTrakValidationError> validateTelephoneNumber(final String telephoneNumber) {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		TargetTrakValidationError error = telephoneNumberRules.isPhoneNumberEmpty(telephoneNumber);
		if (error == null) {
			errors.add(telephoneNumberRules.isPhoneNumberValidLength(telephoneNumber));
			errors.add(telephoneNumberRules.phoneContainsDigitsAndDashOnly(telephoneNumber));
		} else {
			errors.add(error);
		}
		return errors;
	}

	private List<TargetTrakValidationError> validateCompany(final Long companyId) {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		errors.add(contactRules.isCompanyEmpty(companyId));
		return errors;
	}

	public void setFirstNameRules(FirstNameRules firstNameRules) {
		this.firstNameRules = firstNameRules;
	}

	public void setLastNameRules(LastNameRules lastNameRules) {
		this.lastNameRules = lastNameRules;
	}

	public void setEmailRules(EmailRules emailRules) {
		this.emailRules = emailRules;
	}

	public void setTelephoneNumberRules(PhoneNumberRules telephoneNumberRules) {
		this.telephoneNumberRules = telephoneNumberRules;
	}

	public void setContactRules(ContactRules contactRules) {
		this.contactRules = contactRules;
	}
}