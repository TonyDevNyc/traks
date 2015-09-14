package com.target.trak.system.persistence;

import java.util.List;

import com.target.trak.system.entity.Company;
import com.target.trak.system.entity.criteria.CompanySearchCriteria;

public interface CompanyDao {

	public Company insertCompany(final Company company);

	public Company selectCompanyById(final Long id);

	public List<Company> selectPaginatedCompaniesByCriteria(final CompanySearchCriteria criteria);

	public int selectPaginatedCompaniesByCriteriaCount(final CompanySearchCriteria criteria);

	public Company updateCompany(final Company company);
	
	public List<Company> selectAllCompanyNames();

}
