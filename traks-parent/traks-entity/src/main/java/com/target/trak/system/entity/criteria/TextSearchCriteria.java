package com.target.trak.system.entity.criteria;

public class TextSearchCriteria extends BaseSearchCriteria {

	private String text;

	public TextSearchCriteria() {
	}

	public TextSearchCriteria(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
