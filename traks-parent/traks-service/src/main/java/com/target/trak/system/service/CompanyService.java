package com.target.trak.system.service;

import com.target.trak.system.service.dto.company.CompanyApiRequest;
import com.target.trak.system.service.dto.company.CompanyApiResponse;

public interface CompanyService {

	public CompanyApiResponse createCompany(final CompanyApiRequest request);
	
	public CompanyApiResponse getCompaniesByCriteria(final CompanyApiRequest request);
	
	public CompanyApiResponse updateCompany(final CompanyApiRequest request);
	
	public CompanyApiResponse getCompanyNames();
	
	public CompanyApiResponse getCompanyById(final CompanyApiRequest request);
}
