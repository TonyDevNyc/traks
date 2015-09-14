package com.target.trak.system.dto.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.target.trak.system.entity.Privilege;
import com.target.trak.system.entity.Role;
import com.target.trak.system.entity.User;

public class UserEntityToDtoConverter implements Converter<User, UserDto> {

	@SuppressWarnings("unchecked")
	@Override
	public UserDto convert(User entity) {
		UserDto dto = new UserDto();
		dto.setUsername(entity.getUsername());
		dto.setEmail(entity.getEmail());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setRoles(buildRoles((List<Role>) entity.getAuthorities()));
		return dto;
	}

	private List<RoleDto> buildRoles(final List<Role> targetTrakRoles) {
		List<RoleDto> roles = new ArrayList<RoleDto>();
		RoleDto role = null;
		if (targetTrakRoles != null && !targetTrakRoles.isEmpty()) {
			for (Role targetTrakRole : targetTrakRoles) {
				role = new RoleDto();
				role.setRoleId(targetTrakRole.getRoleId());
				role.setRoleName(targetTrakRole.getName());
				role.setPrivileges(buildPrivilegeList(targetTrakRole.getPrivileges()));
				roles.add(role);
			}
		}
		return roles;
	}

	private List<PrivilegeDto> buildPrivilegeList(final List<Privilege> targetTrakPrivileges) {
		List<PrivilegeDto> privileges = new ArrayList<PrivilegeDto>();
		PrivilegeDto privilege = null;
		for (Privilege targetTrakPrivilege : targetTrakPrivileges) {
			privilege = new PrivilegeDto();
			privilege.setPrivilegeId(targetTrakPrivilege.getId());
			privilege.setPrivilegeName(targetTrakPrivilege.getName());
			privileges.add(privilege);
		}
		return privileges;
	}
}