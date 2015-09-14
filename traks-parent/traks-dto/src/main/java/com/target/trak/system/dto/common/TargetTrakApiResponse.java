package com.target.trak.system.dto.common;

import java.util.List;

public interface TargetTrakApiResponse {

	public boolean isSuccess();

	public void setSuccess(final boolean success);

	public List<TargetTrakValidationError> getErrors();

	public void setErrors(final List<TargetTrakValidationError> errors);

	public String getMessage();

	public void setMessage(final String message);

	public int getTotalSize();

	public void setTotalSize(int totalSize);

	public TargetTrakErrorTypeEnum getErrorType();

	public void setErrorType(final TargetTrakErrorTypeEnum errorType);

}
