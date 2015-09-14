package com.target.trak.system.service.dto.security.menu;

import com.target.trak.system.service.dto.common.BaseTargetTrakApiRequest;
import com.target.trak.system.service.dto.security.UserDto;

public class MenuApiRequest extends BaseTargetTrakApiRequest {
	
	private UserDto currentUser;

	public UserDto getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserDto currentUser) {
		this.currentUser = currentUser;
	}
}
