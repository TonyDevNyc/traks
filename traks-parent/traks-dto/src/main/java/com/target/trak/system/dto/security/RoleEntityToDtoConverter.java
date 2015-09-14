package com.target.trak.system.dto.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.target.trak.system.entity.Privilege;
import com.target.trak.system.entity.Role;

public class RoleEntityToDtoConverter implements Converter<Role, RoleDto> {

	@Override
	public RoleDto convert(Role role) {
		RoleDto dto = new RoleDto();
		dto.setRoleId(role.getRoleId());
		dto.setRoleName(role.getName());
		dto.setPrivileges(convertPrivileges(role.getPrivileges()));
		return dto;
	}

	private List<PrivilegeDto> convertPrivileges(final List<Privilege> privileges) {
		List<PrivilegeDto> dtos = new ArrayList<PrivilegeDto>();
		if (privileges != null && !privileges.isEmpty()) {
			PrivilegeDto dto = null;
			for (Privilege privilege : privileges) {
				dto = new PrivilegeDto();
				dto.setPrivilegeId(privilege.getId());
				dto.setPrivilegeName(privilege.getName());
				dtos.add(dto);
			}
		}
		return dtos;
	}
}