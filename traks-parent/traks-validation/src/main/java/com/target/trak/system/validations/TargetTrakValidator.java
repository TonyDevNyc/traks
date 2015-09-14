package com.target.trak.system.validations;

import java.util.List;

import com.target.trak.system.dto.common.TargetTrakValidationError;

public interface TargetTrakValidator<T> {

	public List<TargetTrakValidationError> validate(T object);
}
