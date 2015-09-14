package com.target.trak.system.service.dto.security.user;

import java.util.List;

import com.target.trak.system.service.dto.common.BaseTargetTrakApiResponse;
import com.target.trak.system.service.dto.security.UserDto;

public class UserApiResponse extends BaseTargetTrakApiResponse {

	private UserDto user;
	private List<UserDto> users;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

}
