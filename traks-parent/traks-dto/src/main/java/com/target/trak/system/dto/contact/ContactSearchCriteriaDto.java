package com.target.trak.system.dto.contact;

import com.target.trak.system.dto.common.TargetTrakPagingCriteria;

public class ContactSearchCriteriaDto extends TargetTrakPagingCriteria {

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
