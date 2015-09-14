package com.target.trak.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import com.target.trak.system.entity.Role;
import com.target.trak.system.entity.User;
import com.target.trak.system.persistence.RolePrivilegesDao;
import com.target.trak.system.persistence.UserDetailsDao;
import com.target.trak.system.persistence.UserRoleDao;
import com.target.trak.system.service.UsersService;
import com.target.trak.system.service.dto.security.UserDto;
import com.target.trak.system.service.dto.security.user.UserApiRequest;
import com.target.trak.system.service.dto.security.user.UserApiResponse;

public class TargetTrakUserDetailsServiceImpl implements UserDetailsService, UsersService {

	private Logger logger = Logger.getLogger(getClass());

	private UserDetailsDao userDetailsDao;

	private UserRoleDao userRoleDao;

	private RolePrivilegesDao rolePrivilegesDao;

	private ConversionService conversionService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (StringUtils.isEmpty(username)) {
			logger.error("Username is empty");
			throw new UsernameNotFoundException("Username is empty");
		}

		User user = userDetailsDao.getUserByUsername(username.toLowerCase());
		if (user == null) {
			logger.error("Username: [" + username + "] not found");
			throw new UsernameNotFoundException("Username: [" + username + "] not found");
		}

		List<Role> roles = userRoleDao.getUserRoles(username.toLowerCase());
		user.setAuthorities(roles);

		if (roles != null && !roles.isEmpty()) {
			for (Role role : roles) {
				role.setPrivileges(rolePrivilegesDao.getPrivilegesByRoleId(role.getRoleId()));
			}
		}
		return user;
	}

	@Override
	public UserApiResponse getDistinctUsers(final UserApiRequest request) {
		UserApiResponse response = new UserApiResponse();
		List<User> userEntities = userDetailsDao.selectDistinctUsers();
		response.setUsers(convertUserEntity(userEntities));
		response.setSuccess(true);
		return response;
	}

	private List<UserDto> convertUserEntity(final List<User> entities) {
		List<UserDto> dtos = new ArrayList<UserDto>();
		if (entities != null && !entities.isEmpty()) {
			for (User entity : entities) {
				dtos.add(conversionService.convert(entity, UserDto.class));
			}
		}
		return dtos;
	}

	public void setUserDetailsDao(UserDetailsDao userDetailsDao) {
		this.userDetailsDao = userDetailsDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public void setRolePrivilegesDao(RolePrivilegesDao rolePrivilegesDao) {
		this.rolePrivilegesDao = rolePrivilegesDao;
	}

	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}
}