package com.target.trak.system.persistence;

import java.util.List;

import com.target.trak.system.entity.User;

public interface UserDetailsDao {

	public User getUserByUsername(final String username);

	public void insertUser(final User user);

	public User getUserByEmail(final String email);
	
	public List<User> selectDistinctUsers();
}
