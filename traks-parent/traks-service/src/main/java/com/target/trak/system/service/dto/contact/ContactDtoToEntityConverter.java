package com.target.trak.system.service.dto.contact;

import org.springframework.core.convert.converter.Converter;

import com.target.trak.system.entity.Company;
import com.target.trak.system.entity.Contact;
import com.target.trak.system.service.util.DateUtil;

public class ContactDtoToEntityConverter implements Converter<ContactDto, Contact> {

	@Override
	public Contact convert(ContactDto dto) {
		Contact entity = new Contact();
		entity.setId(dto.getId());
		entity.setTitle(dto.getTitle());
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setSuffix(dto.getSuffix());
		entity.setMiddleInitial(dto.getMiddleInitial());
		entity.setContactType(dto.getContactType());
		entity.setTelephoneNumber(dto.getTelephoneNumber());
		entity.setEmailAddress(dto.getEmailAddress());
		if (dto.getCompany() != null) {
			Company company = new Company();
			company.setId(dto.getCompany().getId());
			entity.setCompany(company);
		}
		entity.setActiveAtCompany(dto.isActiveAtCompany());
		entity.setCreatedTimestamp(DateUtil.convertCalendarToTimestamp(dto.getCreatedDate()));
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setLastUpdatedBy(dto.getLastUpdatedBy());
		entity.setLastUpdatedTimestamp(DateUtil.convertCalendarToTimestamp(dto.getLastUpdatedDate()));
		entity.setVersion(dto.getVersion());
		return entity;
	}
}
