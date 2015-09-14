package com.target.trak.system.service.dto.company;

import org.springframework.core.convert.converter.Converter;

import com.target.trak.system.entity.Company;
import com.target.trak.system.service.util.DateUtil;

public class CompanyEntityToDtoConverter implements Converter<Company, CompanyDto> {

	@Override
	public CompanyDto convert(Company entity) {
		CompanyDto dto = new CompanyDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setAddressLine1(entity.getAddressLine1());
		dto.setAddressLine2(entity.getAddressLine2());
		dto.setCity(entity.getCity());
		dto.setState(entity.getState());
		dto.setCountry(entity.getCountry());
		dto.setZipcode(entity.getZipcode());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreatedDate(DateUtil.convertTimestampToCalendar(entity.getCreatedTimestamp()));
		dto.setLastUpdatedBy(entity.getLastUpdatedBy());
		dto.setLastUpdatedDate(DateUtil.convertTimestampToCalendar(entity.getLastUpdatedTimestamp()));
		dto.setVersion(entity.getVersion());
		return dto;
	}

}
