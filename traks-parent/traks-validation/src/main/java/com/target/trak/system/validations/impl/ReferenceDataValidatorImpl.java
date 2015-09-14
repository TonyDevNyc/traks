package com.target.trak.system.validations.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.target.trak.system.dto.common.TargetTrakValidationError;
import com.target.trak.system.dto.referencedata.ReferenceDataApiRequest;
import com.target.trak.system.dto.referencedata.ReferenceDataDto;
import com.target.trak.system.validations.TargetTrakValidator;
import com.target.trak.system.validations.rules.ReferenceDataRules;

public class ReferenceDataValidatorImpl implements TargetTrakValidator<ReferenceDataApiRequest> {

	private ReferenceDataRules referenceDataRules;

	public ReferenceDataValidatorImpl(ReferenceDataRules referenceDataRules) {
		this.referenceDataRules = referenceDataRules;
	}

	@Override
	public List<TargetTrakValidationError> validate(final ReferenceDataApiRequest request) {
		List<TargetTrakValidationError> validationErrors = new ArrayList<TargetTrakValidationError>();

		if (request == null) {
			throw new IllegalArgumentException("API request is null");
		}

		final ReferenceDataDto referenceDataDto = request.getReferenceDataDto();
		if (referenceDataDto == null) {
			throw new IllegalArgumentException("Reference Data is null");
		}

		switch (request.getRequestType()) {
			case CREATE:
				validationErrors.addAll(validateCreate(referenceDataDto));
				break;
			case UPDATE:
				validationErrors.addAll(validateUpdate(referenceDataDto));
				break;
			case DELETE:
				validationErrors.addAll(validateDelete(referenceDataDto));
				break;
			default:
				System.out.println("No implementation available");
				break;
		}
		validationErrors.removeAll(Collections.singleton(null));
		return validationErrors;
	}

	private List<TargetTrakValidationError> validateDelete(final ReferenceDataDto referenceDataDto) {
		List<TargetTrakValidationError> validationErrors = new ArrayList<TargetTrakValidationError>();
		validateId(validationErrors, referenceDataDto.getId());
		return validationErrors;
	}

	private List<TargetTrakValidationError> validateUpdate(final ReferenceDataDto referenceDataDto) {
		List<TargetTrakValidationError> validationErrors = new ArrayList<TargetTrakValidationError>();
		String type = referenceDataDto.getType();
		String label = referenceDataDto.getLabel();
		String value = referenceDataDto.getValue();
		Long id = referenceDataDto.getId();
		String status = referenceDataDto.getStatus();

		validateType(validationErrors, type);
		validateLabel(validationErrors, label);
		validateValue(validationErrors, value);
		validateId(validationErrors, id);
		validateStatus(validationErrors, status);

		validationErrors.add(referenceDataRules.checkReferenceDataConstraint(id, type, label, value));
		return validationErrors;
	}

	private List<TargetTrakValidationError> validateCreate(final ReferenceDataDto referenceDataDto) {
		List<TargetTrakValidationError> validationErrors = new ArrayList<TargetTrakValidationError>();
		String type = referenceDataDto.getType();
		String label = referenceDataDto.getLabel();
		String value = referenceDataDto.getValue();
		String status = referenceDataDto.getStatus();

		validateType(validationErrors, type);
		validateLabel(validationErrors, label);
		validateValue(validationErrors, value);
		validateStatus(validationErrors, status);

		if (!StringUtils.isEmpty(type) && !StringUtils.isEmpty(label) && !StringUtils.isEmpty(value)) {
			validationErrors.add(referenceDataRules.referenceDataAlreadyExists(type, label, value));
		}
		return validationErrors;
	}

	private void validateId(final List<TargetTrakValidationError> validationErrors, final Long id) {
		TargetTrakValidationError idEmptyError = referenceDataRules.isIdEmpty(id);
		if (idEmptyError == null) {
			validationErrors.add(idEmptyError);
		}
	}

	private void validateType(final List<TargetTrakValidationError> validationErrors, final String type) {
		TargetTrakValidationError typeError = referenceDataRules.isTypeEmpty(type);
		if (typeError == null) {
			validationErrors.add(referenceDataRules.isTypeValidLength(type));
			validationErrors.add(referenceDataRules.typeContainsAllowableChars(type));
		} else {
			validationErrors.add(typeError);
		}
	}

	private void validateLabel(final List<TargetTrakValidationError> validationErrors, final String label) {
		TargetTrakValidationError labelError = referenceDataRules.isLabelEmpty(label);
		if (labelError == null) {
			validationErrors.add(referenceDataRules.isLabelValidLength(label));
			validationErrors.add(referenceDataRules.labelContainsAllowableChars(label));
		} else {
			validationErrors.add(labelError);
		}
	}

	private void validateValue(final List<TargetTrakValidationError> validationErrors, final String value) {
		TargetTrakValidationError valueError = referenceDataRules.isValueEmpty(value);
		if (valueError == null) {
			validationErrors.add(referenceDataRules.isValueValidLength(value));
			validationErrors.add(referenceDataRules.valueContainsAllowableChars(value));
		} else {
			validationErrors.add(valueError);
		}
	}

	private void validateStatus(final List<TargetTrakValidationError> validationErrors, final String status) {
		TargetTrakValidationError statusError = referenceDataRules.isStatusEmpty(status);
		if (statusError == null) {
			validationErrors.add(referenceDataRules.isStatusValidLength(status));
			validationErrors.add(referenceDataRules.statusContainsAllowableChars(status));
		} else {
			validationErrors.add(statusError);
		}
	}
}