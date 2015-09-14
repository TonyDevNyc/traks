package com.target.trak.system.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.target.trak.system.entity.User;
import com.target.trak.system.persistence.UserDetailsDao;

public class UserDetailsDaoImpl implements UserDetailsDao {

	private static final Logger logger = Logger.getLogger(UserDetailsDaoImpl.class);

	private NamedParameterJdbcTemplate userDetailTemplate;

	private Properties userDetailQueries;

	public UserDetailsDaoImpl(DataSource dataSource) {
		userDetailTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public User getUserByUsername(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = userDetailQueries.getProperty("selectUserByUsernameSql");
		params.addValue("username", username.toLowerCase());
		User user = null;

		try {
			user = userDetailTemplate.queryForObject(sql, params, new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			logger.error("No results found for username: " + username, e);
		}
		return user;
	}

	@Override
	public void insertUser(final User user) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", user.getUsername());
		params.addValue("password", user.getPassword());
		params.addValue("enabled", user.isEnabled());
		params.addValue("firstName", user.getFirstName());
		params.addValue("lastName", user.getLastName());
		params.addValue("email", user.getEmail());
		params.addValue("mobileNumber", user.getTelephoneNumber());
		params.addValue("registrationDate", user.getRegistrationDate());

		String sql = userDetailQueries.getProperty("insertUserSql");
		userDetailTemplate.update(sql, params);
	}

	@Override
	public User getUserByEmail(final String email) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", email);
		User user = null;
		String sql = userDetailQueries.getProperty("selectUserByEmailSql");

		try {
			user = userDetailTemplate.queryForObject(sql, params, new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			logger.error("No results found for email: " + email, e);
		}
		return user;
	}

	@Override
	public List<User> selectDistinctUsers() {
		String sql = userDetailQueries.getProperty("selectDistinctUsersSql");
		return userDetailTemplate.query(sql, new MapSqlParameterSource(), new UserRowMapper());
	}

	public void setUserDetailQueries(Properties userDetailQueries) {
		this.userDetailQueries = userDetailQueries;
	}

	private final class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setEnabled(rs.getBoolean("user_enabled"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			user.setTelephoneNumber(rs.getString("mobile_number"));
			user.setRegistrationDate(rs.getTimestamp("registration_date"));
			return user;
		}
	}
}