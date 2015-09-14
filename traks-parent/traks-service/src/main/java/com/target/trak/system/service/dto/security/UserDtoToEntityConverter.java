package com.target.trak.system.service.dto.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.target.trak.system.entity.User;
import com.target.trak.system.service.util.DateUtil;

public class UserDtoToEntityConverter implements Converter<UserDto, User> {

	private BCryptPasswordEncoder encoder;
	
	@Override
	public User convert(UserDto dto) {
		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setTelephoneNumber(dto.getTelephoneNumber());
		user.setUsername(dto.getUsername());
		user.setPassword(encoder.encode(dto.getPassword()));
		user.setEnabled(true);
		user.setRegistrationDate(DateUtil.convertCalendarToTimestamp(dto.getRegistrationDate()));
		return user;
	}

	public void setEncoder(BCryptPasswordEncoder encoder) {
		this.encoder = encoder;
	}
}
