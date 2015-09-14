package com.target.trak.system.web.ui.helpers.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

import com.target.trak.system.service.validations.TargetTrakValidationError;
import com.target.trak.system.web.ui.UiValidationError;
import com.target.trak.system.web.ui.helpers.UiErrorHelper;

public class UiErrorHelperImpl implements UiErrorHelper {

	private MessageSource messageSource;
	
	@Override
	public List<UiValidationError> buildUiValidationErrors(List<TargetTrakValidationError> apiErrors) {
		List<UiValidationError> list = new ArrayList<UiValidationError>();
		if (apiErrors == null || apiErrors.isEmpty()) {
			return list;
		}

		String msg = null;
		for (TargetTrakValidationError apiError : apiErrors) {
			msg = messageSource.getMessage(apiError.getErrorMessage(), new Object[] {}, Locale.US);
			list.add(new UiValidationError(apiError.getFieldName(), msg));
		}
		return list;
	}

	@Override
	public String buildErrorMessage(String errorCode) {
		if (StringUtils.isEmpty(errorCode)) {
			return "";
		}
		return messageSource.getMessage(errorCode, new Object[] {}, Locale.US);
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
