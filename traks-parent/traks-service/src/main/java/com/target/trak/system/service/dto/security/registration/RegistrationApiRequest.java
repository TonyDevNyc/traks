package com.target.trak.system.service.dto.security.registration;

import com.target.trak.system.service.dto.common.BaseTargetTrakApiRequest;
import com.target.trak.system.service.dto.security.UserDto;

public class RegistrationApiRequest extends BaseTargetTrakApiRequest {

	private UserDto userRegistration;

	public UserDto getUserRegistration() {
		return userRegistration;
	}

	public void setUserRegistration(UserDto userRegistration) {
		this.userRegistration = userRegistration;
	}
}
