package com.target.trak.system.service.dto.company;

import com.target.trak.system.service.dto.common.BaseTargetTrakApiRequest;

public class CompanyApiRequest extends BaseTargetTrakApiRequest {

	private CompanyDto company;
	private CompanySearchCriteriaDto companySearchCriteria;

	public CompanyDto getCompany() {
		return company;
	}

	public void setCompany(CompanyDto company) {
		this.company = company;
	}

	public CompanySearchCriteriaDto getCompanySearchCriteria() {
		return companySearchCriteria;
	}

	public void setCompanySearchCriteria(CompanySearchCriteriaDto companySearchCriteria) {
		this.companySearchCriteria = companySearchCriteria;
	}

}
