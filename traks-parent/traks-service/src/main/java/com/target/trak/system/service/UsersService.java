package com.target.trak.system.service;

import com.target.trak.system.service.dto.security.user.UserApiRequest;
import com.target.trak.system.service.dto.security.user.UserApiResponse;

public interface UsersService {

	public UserApiResponse getDistinctUsers(final UserApiRequest request);
}
