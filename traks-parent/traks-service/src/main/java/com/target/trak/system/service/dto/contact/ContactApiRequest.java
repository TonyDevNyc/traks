package com.target.trak.system.service.dto.contact;

import com.target.trak.system.service.dto.common.BaseTargetTrakApiRequest;

public class ContactApiRequest extends BaseTargetTrakApiRequest {

	private ContactDto contact;
	private ContactSearchCriteriaDto searchCriteria;

	public ContactDto getContact() {
		return contact;
	}

	public void setContact(ContactDto contact) {
		this.contact = contact;
	}

	public ContactSearchCriteriaDto getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(ContactSearchCriteriaDto searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

}
