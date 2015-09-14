package com.target.trak.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.convert.ConversionService;

import com.target.trak.system.entity.Menu;
import com.target.trak.system.persistence.MenuDao;
import com.target.trak.system.service.BaseTargetTrakService;
import com.target.trak.system.service.MenuService;
import com.target.trak.system.service.dto.security.PrivilegeDto;
import com.target.trak.system.service.dto.security.RoleDto;
import com.target.trak.system.service.dto.security.UserDto;
import com.target.trak.system.service.dto.security.menu.MenuApiRequest;
import com.target.trak.system.service.dto.security.menu.MenuApiResponse;
import com.target.trak.system.service.dto.security.menu.MenuDto;

public class MenuServiceImpl extends BaseTargetTrakService implements MenuService {

	private MenuDao menuDao;

	private ConversionService conversionService;
	
	@Override
	public MenuApiResponse getMenuItemsForUser(final MenuApiRequest request) {
		MenuApiResponse response = new MenuApiResponse();
		List<MenuDto> menuList = new ArrayList<MenuDto>();
		
		UserDto currentUser = request.getCurrentUser();
		List<RoleDto> roles = currentUser.getRoles();
		if (roles == null || roles.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("User [" + currentUser.getUsername() + "] does not have any roles");
			response.setMenuItems(menuList);
			return response;
		}

		List<Long> privilegeIds = buildPrivilegeList(roles);
		List<Menu> menuItems = menuDao.selectMenuItemsByPrivileges(privilegeIds);
		menuList = buildMenuList(menuItems);
		response.setSuccess(true);
		response.setMenuItems(menuList);
		return response;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}
	
	private List<MenuDto> buildMenuList(List<Menu> menuItems) {
		List<MenuDto> dtos = new ArrayList<MenuDto>();
		for (Menu menuItem : menuItems) {
			dtos.add(conversionService.convert(menuItem, MenuDto.class));
		}
		return dtos;
	}

	private List<Long> buildPrivilegeList(List<RoleDto> roles) {
		List<Long> privilegeIds = new ArrayList<Long>();
		Map<Long, Long> map = new HashMap<Long, Long>();
		for (RoleDto role : roles) {
			for (PrivilegeDto privilege : role.getPrivileges()) {
				map.put(privilege.getPrivilegeId(), privilege.getPrivilegeId());
			}
		}
		privilegeIds.addAll(map.values());
		return privilegeIds;
	}
}