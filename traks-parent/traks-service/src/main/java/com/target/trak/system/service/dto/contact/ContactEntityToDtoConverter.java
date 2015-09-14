package com.target.trak.system.service.dto.contact;

import org.springframework.core.convert.converter.Converter;

import com.target.trak.system.entity.Contact;
import com.target.trak.system.service.dto.company.CompanyDto;
import com.target.trak.system.service.util.DateUtil;

public class ContactEntityToDtoConverter implements Converter<Contact, ContactDto> {

	@Override
	public ContactDto convert(Contact entity) {
		ContactDto dto = new ContactDto();
		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setSuffix(entity.getSuffix());
		dto.setMiddleInitial(entity.getMiddleInitial());
		dto.setContactType(entity.getContactType());
		dto.setTelephoneNumber(entity.getTelephoneNumber());
		dto.setEmailAddress(entity.getEmailAddress());
		if (entity.getCompany() != null) {
			CompanyDto company = new CompanyDto();
			company.setId(entity.getCompany().getId());
			company.setName(entity.getCompany().getName());
			dto.setCompany(company);
		}
		dto.setActiveAtCompany(entity.isActiveAtCompany());
		dto.setCreatedDate(DateUtil.convertTimestampToCalendar(entity.getCreatedTimestamp()));
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setLastUpdatedBy(entity.getLastUpdatedBy());
		dto.setLastUpdatedDate(DateUtil.convertTimestampToCalendar(entity.getLastUpdatedTimestamp()));
		dto.setVersion(entity.getVersion());
		return dto;
	}

}
