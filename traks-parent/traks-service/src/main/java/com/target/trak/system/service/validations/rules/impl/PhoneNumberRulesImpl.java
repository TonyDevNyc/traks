package com.target.trak.system.service.validations.rules.impl;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.target.trak.system.service.validations.TargetTrakValidationError;
import com.target.trak.system.service.validations.rules.PhoneNumberRules;
import com.target.trak.system.service.validations.util.ValidationsUtil;

public class PhoneNumberRulesImpl implements PhoneNumberRules {

	private Properties genericValidationProps;

	@Override
	public TargetTrakValidationError isPhoneNumberEmpty(final String telephoneNumber) {
		if (StringUtils.isEmpty(telephoneNumber)) {
			return new TargetTrakValidationError("telephoneNumber", genericValidationProps.getProperty("phoneNumber.empty.error"));
		}
		return null;
	}

	@Override
	public TargetTrakValidationError isPhoneNumberValidLength(final String telephoneNumber) throws IllegalArgumentException {
		int maxLength = Integer.parseInt((String) genericValidationProps.get("phonenumber.maxlength"));
		if (telephoneNumber.length() > maxLength) {
			return new TargetTrakValidationError("telephoneNumber", genericValidationProps.getProperty("phoneNumber.maxlength.error"));
		}
		return null;
	}

	@Override
	public TargetTrakValidationError phoneContainsDigitsAndDashOnly(final String telephoneNumber) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(telephoneNumber)) {
			String allowableChars = genericValidationProps.getProperty("phoneNumber.allowable.chars");

			if (!StringUtils.isAlpha(telephoneNumber)) {
				String nonAlphaChars = ValidationsUtil.getNonAlphaCharacters(telephoneNumber);
				char[] allowableSpecialChars = allowableChars.toCharArray();

				if (!ValidationsUtil.containsAllowableSpecialChars(nonAlphaChars, allowableSpecialChars)) {
					return new TargetTrakValidationError("telephoneNumber", genericValidationProps.getProperty("phoneNumber.allowable.chars.error"));
				}
			}
		}
		return null;
	}

	public void setGenericValidationProps(Properties genericValidationProps) {
		this.genericValidationProps = genericValidationProps;
	}
}