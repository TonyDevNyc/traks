package com.target.trak.system.service.dto.company;

import org.springframework.core.convert.converter.Converter;

import com.target.trak.system.entity.Company;
import com.target.trak.system.service.util.DateUtil;

public class CompanyDtoToEntityConverter implements Converter<CompanyDto, Company> {

	@Override
	public Company convert(CompanyDto dto) {
		Company entity = new Company();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setAddressLine1(dto.getAddressLine1());
		entity.setAddressLine2(dto.getAddressLine2());
		entity.setCity(dto.getCity());
		entity.setState(dto.getState());
		entity.setCountry(dto.getCountry());
		entity.setZipcode(dto.getZipcode());
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setCreatedTimestamp(DateUtil.convertCalendarToTimestamp(dto.getCreatedDate()));
		entity.setLastUpdatedBy(dto.getLastUpdatedBy());
		entity.setLastUpdatedTimestamp(DateUtil.convertCalendarToTimestamp(dto.getLastUpdatedDate()));
		entity.setVersion(dto.getVersion());
		return entity;
	}

}
