package com.target.trak.system.dto.security.menu;

import com.target.trak.system.dto.common.BaseTargetTrakApiRequest;
import com.target.trak.system.dto.security.UserDto;

public class MenuApiRequest extends BaseTargetTrakApiRequest {
	
	private UserDto currentUser;

	public UserDto getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserDto currentUser) {
		this.currentUser = currentUser;
	}
}
