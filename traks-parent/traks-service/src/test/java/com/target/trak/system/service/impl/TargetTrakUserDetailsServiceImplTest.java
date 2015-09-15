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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.target.trak.system.dto.security.UserDto;
import com.target.trak.system.dto.security.user.UserApiRequest;
import com.target.trak.system.dto.security.user.UserApiResponse;
import com.target.trak.system.entity.Privilege;
import com.target.trak.system.entity.Role;
import com.target.trak.system.entity.User;
import com.target.trak.system.persistence.RolePrivilegesDao;
import com.target.trak.system.persistence.UserDetailsDao;
import com.target.trak.system.persistence.UserRoleDao;

public class TargetTrakUserDetailsServiceImplTest {

	private UserDetailsDao userDetailsDaoMock;
	private UserRoleDao userRoleDaoMock;
	private RolePrivilegesDao rolePrivilegesDaoMock;
	private ConversionService conversionServiceMock;
	private Mockery mockery;
	private TargetTrakUserDetailsServiceImpl userDetailsService;
	
	@Before
	public void setup() {
		mockery = new JUnit4Mockery();
		userDetailsDaoMock = mockery.mock(UserDetailsDao.class);
		userRoleDaoMock = mockery.mock(UserRoleDao.class);
		rolePrivilegesDaoMock = mockery.mock(RolePrivilegesDao.class);
		conversionServiceMock = mockery.mock(ConversionService.class);
		
		userDetailsService = new TargetTrakUserDetailsServiceImpl();
		userDetailsService.setConversionService(conversionServiceMock);
		userDetailsService.setRolePrivilegesDao(rolePrivilegesDaoMock);
		userDetailsService.setUserDetailsDao(userDetailsDaoMock);
		userDetailsService.setUserRoleDao(userRoleDaoMock);
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void loadUserByUsernameForEmptyUsername() {
		userDetailsService.loadUserByUsername("");
		Assert.fail("Exception not thrown, but should have failed for empty username");
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void loadUserByUsernameForNullUsername() {
		userDetailsService.loadUserByUsername(null);
		Assert.fail("Exception not thrown, but should have failed for empty username");
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void loadUserByUsernameForUserNotFound() {
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(userDetailsDaoMock).getUserByUsername(with(any(String.class)));
				will(returnValue(null));
			}
		});
		userDetailsService.loadUserByUsername("user123");
	}
	
	@Test
	public void loadUserByUsernameForUserFoundWithNoRoles() {
		final String username = "user123";
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(userDetailsDaoMock).getUserByUsername(with(any(String.class)));
				will(returnValue(buildUser(username, false)));
				
				oneOf(userRoleDaoMock).getUserRoles(username);
				will(returnValue(null));
			}
		});
		User actualUser = (User) userDetailsService.loadUserByUsername(username);
		Assert.assertNotNull("User is not null", actualUser);
	}
	
	@Test
	public void loadUserByUsernameForUserFoundWithEmptyRolesList() {
		final String username = "user123";
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(userDetailsDaoMock).getUserByUsername(with(any(String.class)));
				will(returnValue(buildUser(username, false)));
				
				oneOf(userRoleDaoMock).getUserRoles(username);
				will(returnValue(new ArrayList<Role>()));
			}
		});
		User actualUser = (User) userDetailsService.loadUserByUsername(username);
		Assert.assertNotNull("User is not null", actualUser);
	}
	
	@Test
	public void loadUserByUsernameForUserFoundWithRoles() {
		final String username = "user123";
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(userDetailsDaoMock).getUserByUsername(with(any(String.class)));
				will(returnValue(buildUser(username, true)));
				
				oneOf(userRoleDaoMock).getUserRoles(username);
				will(returnValue(buildRoles()));
				
				atLeast(1).of(rolePrivilegesDaoMock).getPrivilegesByRoleId(with(equal(1020L)));
				will(returnValue(buildPrivileges()));
			}
		});
		User actualUser = (User) userDetailsService.loadUserByUsername(username);
		Assert.assertNotNull("User is not null", actualUser);
	}
	
	@Test
	public void getDistinctUsersForEmptyListOfUsers() {
		final User user = buildUser("testUser123", true);
		//final List<User> users = Collections.singletonList(user);
		final List<User> users = new ArrayList<User>();
		
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(userDetailsDaoMock).selectDistinctUsers();
				will(returnValue(users));
				
				atLeast(1).of(conversionServiceMock).convert(with(user), with(equal(UserDto.class)));
				will(returnValue(new UserDto()));
			}
		});
		
		UserApiResponse response = userDetailsService.getDistinctUsers(new UserApiRequest());
		Assert.assertNotNull("User Api Response is not null", response);
		Assert.assertNotNull("User List is not null", response.getUsers());
	}
	
	@Test
	public void getDistinctUsersForNullListOfUsers() {
		final User user = buildUser("testUser123", true);
		
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(userDetailsDaoMock).selectDistinctUsers();
				will(returnValue(null));
				
				atLeast(1).of(conversionServiceMock).convert(with(user), with(equal(UserDto.class)));
				will(returnValue(new UserDto()));
			}
		});
		
		UserApiResponse response = userDetailsService.getDistinctUsers(new UserApiRequest());
		Assert.assertNotNull("User Api Response is not null", response);
		Assert.assertNotNull("User List is not null", response.getUsers());
	}
	
	@Test
	public void getDistinctUsersForUsersFound() {
		final User user = buildUser("testUser123", true);
		final List<User> users = Collections.singletonList(user);
		
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(userDetailsDaoMock).selectDistinctUsers();
				will(returnValue(users));
				
				atLeast(1).of(conversionServiceMock).convert(with(user), with(equal(UserDto.class)));
				will(returnValue(new UserDto()));
			}
		});
		
		UserApiResponse response = userDetailsService.getDistinctUsers(new UserApiRequest());
		Assert.assertNotNull("User Api Response is not null", response);
		Assert.assertNotNull("User List is not null", response.getUsers());
	}
	
	private User buildUser(final String username, boolean withRoles) {
		User user = new User();
		user.setUsername(username);
		if (withRoles) {
			user.setAuthorities(buildRoles());
		}
		return user;
	}
	
	private List<Role> buildRoles() {
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role();
		role.setRoleId(1020L);
		role.setName("Security Administrator");
		role.setPrivileges(buildPrivileges());
		roles.add(role);
		return roles;
	}
	
	private List<Privilege> buildPrivileges() {
		List<Privilege> privileges = new ArrayList<Privilege>();
		Privilege priv = new Privilege();
		priv.setId(819L);
		priv.setName("Add User Privilege");
		privileges.add(priv);
		return privileges;
	}
}
