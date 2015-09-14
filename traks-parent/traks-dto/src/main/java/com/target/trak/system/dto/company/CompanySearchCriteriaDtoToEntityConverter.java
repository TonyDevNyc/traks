package com.target.trak.system.dto.company;

import org.springframework.core.convert.converter.Converter;

import com.target.trak.system.entity.criteria.CompanySearchCriteria;

public class CompanySearchCriteriaDtoToEntityConverter implements Converter<CompanySearchCriteriaDto, CompanySearchCriteria> {

	@Override
	public CompanySearchCriteria convert(CompanySearchCriteriaDto dto) {
		CompanySearchCriteria criteria = new CompanySearchCriteria();
		criteria.setText(dto.getText());
		criteria.setPage(dto.getPage());
		criteria.setStart(dto.getStart());
		criteria.setEnd(dto.getEnd());
		criteria.setSortDirection(dto.getSortDirection());
		criteria.setSortField(dto.getSortField());
		return criteria;
	}

}
