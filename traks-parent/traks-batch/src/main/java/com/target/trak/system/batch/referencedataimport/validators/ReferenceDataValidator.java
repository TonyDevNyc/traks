package com.target.trak.system.batch.referencedataimport.validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

import com.target.trak.system.dto.common.TargetTrakValidationError;
import com.target.trak.system.entity.ReferenceData;
import com.target.trak.system.validations.rules.ReferenceDataRules;

public class ReferenceDataValidator implements Validator<ReferenceData> {

	private static final Logger logger = Logger.getLogger(ReferenceDataValidator.class);

	private ReferenceDataRules referenceDataRules;

	@Override
	public void validate(ReferenceData referenceData) throws ValidationException {
		String type = referenceData.getReferenceDataType();
		String label = referenceData.getReferenceDataLabel();
		String value = referenceData.getReferenceDataValue();
		String status = referenceData.getStatus();

		boolean validType = isTypeValid(type);
		if (!validType) {
			logger.error("Type [" + type + "] is invalid");
			throw new ValidationException("Type [" + type + "] is invalid");
		}

		boolean validLabel = isLabelValid(label);
		if (!validLabel) {
			logger.error("Label [" + label + "] is invalid");
			throw new ValidationException("Label [" + label + "] is invalid");
		}

		boolean validValue = isValueValid(value);
		if (!validValue) {
			logger.error("Value [" + value + "] is invalid");
			throw new ValidationException("Value [" + value + "] is invalid");
		}

		boolean validStatus = isStatusValid(status);
		if (!validStatus) {
			logger.error("Status [" + status + "] is invalid");
			throw new ValidationException("Status [" + status + "] is invalid");
		}
	}

	private boolean isTypeValid(final String type) {
		List<TargetTrakValidationError> validationErrors = new ArrayList<TargetTrakValidationError>();
		TargetTrakValidationError typeError = referenceDataRules.isTypeEmpty(type);
		if (typeError == null) {
			validationErrors.add(referenceDataRules.isTypeValidLength(type));
			validationErrors.add(referenceDataRules.typeContainsAllowableChars(type));
		} else {
			validationErrors.add(typeError);
		}
		validationErrors.removeAll(Collections.singleton(null));
		return validationErrors.isEmpty();
	}

	private boolean isLabelValid(final String label) {
		List<TargetTrakValidationError> validationErrors = new ArrayList<TargetTrakValidationError>();
		TargetTrakValidationError labelError = referenceDataRules.isLabelEmpty(label);
		if (labelError == null) {
			validationErrors.add(referenceDataRules.isLabelValidLength(label));
			validationErrors.add(referenceDataRules.labelContainsAllowableChars(label));
		} else {
			validationErrors.add(labelError);
		}
		validationErrors.removeAll(Collections.singleton(null));
		return validationErrors.isEmpty();
	}

	private boolean isValueValid(final String value) {
		List<TargetTrakValidationError> validationErrors = new ArrayList<TargetTrakValidationError>();
		TargetTrakValidationError valueError = referenceDataRules.isValueEmpty(value);
		if (valueError == null) {
			validationErrors.add(referenceDataRules.isValueValidLength(value));
			validationErrors.add(referenceDataRules.valueContainsAllowableChars(value));
		} else {
			validationErrors.add(valueError);
		}
		validationErrors.removeAll(Collections.singleton(null));
		return validationErrors.isEmpty();
	}

	private boolean isStatusValid(final String status) {
		List<TargetTrakValidationError> validationErrors = new ArrayList<TargetTrakValidationError>();
		TargetTrakValidationError statusError = referenceDataRules.isStatusEmpty(status);
		if (statusError == null) {
			validationErrors.add(referenceDataRules.isStatusValidLength(status));
			validationErrors.add(referenceDataRules.statusContainsAllowableChars(status));
		} else {
			validationErrors.add(statusError);
		}
		validationErrors.removeAll(Collections.singleton(null));
		return validationErrors.isEmpty();
	}

	public void setReferenceDataRules(ReferenceDataRules referenceDataRules) {
		this.referenceDataRules = referenceDataRules;
	}
}