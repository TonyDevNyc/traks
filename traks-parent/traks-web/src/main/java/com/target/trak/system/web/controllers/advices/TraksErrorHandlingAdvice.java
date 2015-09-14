package com.target.trak.system.web.controllers.advices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.target.trak.system.service.dto.common.TargetTrakApiResponse;
import com.target.trak.system.service.dto.common.TargetTrakErrorTypeEnum;
import com.target.trak.system.service.exception.TargetTrakException;
import com.target.trak.system.service.validations.TargetTrakValidationError;
import com.target.trak.system.web.ui.UiValidationError;
import com.target.trak.system.web.ui.helpers.UiErrorHelper;

@ControllerAdvice
public class TraksErrorHandlingAdvice {

	private static final String JSON_SUCCESS = "success";
	private static final String JSON_ERRORS = "errors";
	private static final String JSON_MESSAGE = "message";
	private static final Logger logger = Logger.getLogger(TraksErrorHandlingAdvice.class);

	private UiErrorHelper uiErrorHelper;

	@ExceptionHandler({ TargetTrakException.class })
	public @ResponseBody Map<String, Object> handleTargetTrakException(RuntimeException e, WebRequest request) {
		TargetTrakException exception = (TargetTrakException) e;
		logger.error(e.getMessage(), exception);

		TargetTrakApiResponse apiResponse = exception.getResponse();
		TargetTrakErrorTypeEnum errorType = apiResponse.getErrorType();

		Map<String, Object> response = null;
		switch (errorType) {
		case VALIDATION:
			response = handleValidationType(apiResponse);
			break;
		case WARNING:
			response = handleWarningType(apiResponse);
			break;
		case ERROR:
			response = handleErrorType(apiResponse);
			break;
		default:
			response = handleDefaultType(apiResponse);
			break;
		}
		response.put(JSON_SUCCESS, apiResponse.isSuccess());
		return response;
	}

	private Map<String, Object> handleValidationType(TargetTrakApiResponse apiResponse) {
		Map<String, Object> response = new HashMap<String, Object>();
		String apiErrorCode = getApiError(apiResponse.getErrors());

		if (apiErrorCode == null) {
			response.put(JSON_MESSAGE, "A validation error has occurred.  Please fix the errors marked in red.");
		} else {
			response.put(JSON_MESSAGE, uiErrorHelper.buildErrorMessage(apiErrorCode));
		}

		List<UiValidationError> errors = uiErrorHelper.buildUiValidationErrors(apiResponse.getErrors());
		response.put(JSON_ERRORS, errors);
		return response;
	}

	private String getApiError(List<TargetTrakValidationError> errors) {
		if (errors == null || errors.isEmpty()) {
			return null;
		}

		for (TargetTrakValidationError error : errors) {
			if ("api".equals(error.getFieldName())) {
				return error.getErrorMessage();
			}
		}
		return null;
	}

	private Map<String, Object> handleWarningType(TargetTrakApiResponse apiResponse) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(JSON_MESSAGE, apiResponse.getMessage());
		return response;
	}

	private Map<String, Object> handleErrorType(TargetTrakApiResponse apiResponse) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(JSON_MESSAGE, apiResponse.getMessage());
		return response;
	}

	private Map<String, Object> handleDefaultType(TargetTrakApiResponse apiResponse) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(JSON_MESSAGE, apiResponse.getMessage());
		return response;
	}

	public void setUiErrorHelper(UiErrorHelper uiErrorHelper) {
		this.uiErrorHelper = uiErrorHelper;
	}

}