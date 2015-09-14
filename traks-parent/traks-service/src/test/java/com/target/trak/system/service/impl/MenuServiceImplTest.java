package com.target.trak.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.ConversionService;

import com.target.trak.system.entity.Menu;
import com.target.trak.system.persistence.MenuDao;
import com.target.trak.system.service.dto.security.PrivilegeDto;
import com.target.trak.system.service.dto.security.RoleDto;
import com.target.trak.system.service.dto.security.UserDto;
import com.target.trak.system.service.dto.security.menu.MenuApiRequest;
import com.target.trak.system.service.dto.security.menu.MenuApiResponse;
import com.target.trak.system.service.dto.security.menu.MenuDto;

public class MenuServiceImplTest {

	private Mockery mockery;
	private MenuDao menuDaoMock;
	private ConversionService conversionServiceMock;
	private MenuServiceImpl menuService;

	@Before
	public void setup() {
		mockery = new JUnit4Mockery();
		menuService = new MenuServiceImpl();
		menuDaoMock = mockery.mock(MenuDao.class);
		conversionServiceMock = mockery.mock(ConversionService.class);
		menuService.setConversionService(conversionServiceMock);
		menuService.setMenuDao(menuDaoMock);
	}

	@Test
	public void getMenuItemsForUserWithNoRoles() {
		final MenuApiRequest request = new MenuApiRequest();
		request.setCurrentUser(buildCurrentUser(false));
		MenuApiResponse response = menuService.getMenuItemsForUser(request);
		Assert.assertNotNull("Menu Api Response is not null", response);
		Assert.assertFalse("Menu Api Response is un-successful", response.isSuccess());
	}
	
	@Test
	public void getMenuItemsForUserWithEmptyRoleList() {
		final MenuApiRequest request = new MenuApiRequest();
		UserDto userDto = buildCurrentUser(false);
		userDto.setRoles(new ArrayList<RoleDto>());
		request.setCurrentUser(userDto);
		
		MenuApiResponse response = menuService.getMenuItemsForUser(request);
		Assert.assertNotNull("Menu Api Response is not null", response);
		Assert.assertFalse("Menu Api Response is un-successful", response.isSuccess());
	}
	
	@Test
	public void getMenuItemsForUserWithRoles() {
		final MenuApiRequest request = new MenuApiRequest();
		request.setCurrentUser(buildCurrentUser(true));
		final List<Long> privilegeIds = Collections.singletonList(1020L);
		
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(menuDaoMock).selectMenuItemsByPrivileges(with(privilegeIds));
				will(returnValue(buildMenuList()));
				
				atLeast(1).of(conversionServiceMock).convert(with(any(Menu.class)), with(MenuDto.class));
				will(returnValue(new MenuDto()));
			}
		});
		
		MenuApiResponse response = menuService.getMenuItemsForUser(request);
		Assert.assertNotNull("Menu Api Response is not null", response);
		Assert.assertTrue("Menu Api Response is successful", response.isSuccess());
	}
	
	private List<Menu> buildMenuList() {
		List<Menu> menuList = new ArrayList<Menu>();
		Menu menu = new Menu();
		menu.setMenuId(1L);
		menu.setParentMenuId(null);
		menu.setDisplayOrder(1);
		menu.setText("Security Administration");
		menu.setPrivilegeNeeded(1020L);
		menuList.add(menu);
		return menuList;
	}

	private UserDto buildCurrentUser(final boolean withRoles) {
		UserDto user = new UserDto();
		user.setUsername("testUser123");
		if (withRoles) {
			user.setRoles(buildRoles());
		}
		return user;
	}

	private List<RoleDto> buildRoles() {
		List<RoleDto> roles = new ArrayList<RoleDto>();
		RoleDto role = new RoleDto();
		PrivilegeDto privilegeDto = new PrivilegeDto();
		privilegeDto.setPrivilegeId(1020L);
		privilegeDto.setPrivilegeName("ADMIN_PRIVILEGE");
		role.setPrivileges(Collections.singletonList(privilegeDto));
		roles.add(role);
		return roles;
	}
}