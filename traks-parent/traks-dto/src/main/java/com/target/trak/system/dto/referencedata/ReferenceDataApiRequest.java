package com.target.trak.system.dto.referencedata;

import com.target.trak.system.dto.common.BaseTargetTrakApiRequest;
import com.target.trak.system.dto.common.TextSearchCriteriaDto;

public class ReferenceDataApiRequest extends BaseTargetTrakApiRequest {

	private ReferenceDataDto referenceDataDto;

	private TextSearchCriteriaDto textSearchCriteria;

	public ReferenceDataDto getReferenceDataDto() {
		return referenceDataDto;
	}

	public void setReferenceDataDto(ReferenceDataDto referenceDataDto) {
		this.referenceDataDto = referenceDataDto;
	}

	public TextSearchCriteriaDto getTextSearchCriteria() {
		return textSearchCriteria;
	}

	public void setTextSearchCriteria(TextSearchCriteriaDto textSearchCriteria) {
		this.textSearchCriteria = textSearchCriteria;
	}

}