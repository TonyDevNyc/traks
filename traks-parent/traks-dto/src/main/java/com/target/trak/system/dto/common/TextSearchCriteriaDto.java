package com.target.trak.system.dto.common;

public class TextSearchCriteriaDto extends TargetTrakPagingCriteria {

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}