package com.target.trak.system.service.dto.contact;

import org.springframework.core.convert.converter.Converter;

import com.target.trak.system.entity.criteria.ContactSearchCriteria;

public class ContactSearchCriteriaDtoToEntityConverter implements Converter<ContactSearchCriteriaDto, ContactSearchCriteria> {

	@Override
	public ContactSearchCriteria convert(ContactSearchCriteriaDto dto) {
		ContactSearchCriteria criteria = new ContactSearchCriteria();
		criteria.setText(dto.getText());
		criteria.setPage(dto.getPage());
		criteria.setStart(dto.getStart());
		criteria.setEnd(dto.getEnd());
		criteria.setSortDirection(dto.getSortDirection());
		criteria.setSortField(dto.getSortField());
		return criteria;
	}

}
