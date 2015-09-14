package com.target.trak.system.service.validations;

import java.util.List;

public interface TargetTrakValidator<T> {

	public List<TargetTrakValidationError> validate(T object);
}
