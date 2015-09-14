package com.target.trak.system.dto.company;

import com.target.trak.system.dto.common.TargetTrakPagingCriteria;

public class CompanySearchCriteriaDto extends TargetTrakPagingCriteria {

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
