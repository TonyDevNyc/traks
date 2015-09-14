package com.target.trak.system.web.ui.helpers;

import java.util.List;

import com.target.trak.system.service.validations.TargetTrakValidationError;
import com.target.trak.system.web.ui.UiValidationError;

public interface UiErrorHelper {

	public List<UiValidationError> buildUiValidationErrors(final List<TargetTrakValidationError> apiErrors);
	
	public String buildErrorMessage(final String errorCode);
}
