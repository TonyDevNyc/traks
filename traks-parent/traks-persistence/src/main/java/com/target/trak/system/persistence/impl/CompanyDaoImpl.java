package com.target.trak.system.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.target.trak.system.entity.Company;
import com.target.trak.system.entity.criteria.CompanySearchCriteria;
import com.target.trak.system.persistence.CompanyDao;
import com.target.trak.system.persistence.builder.CompanyQueryBuilder;

public class CompanyDaoImpl implements CompanyDao {

	private final Logger logger = Logger.getLogger(getClass());

	private Properties companyQueries;

	private CompanyQueryBuilder companyQueryBuilder;

	private NamedParameterJdbcTemplate companyTemplate;

	public CompanyDaoImpl(DataSource dataSource) {
		companyTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Company insertCompany(final Company company) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", company.getName());
		params.addValue("addressLine1", company.getAddressLine1());
		params.addValue("addressLine2", company.getAddressLine2());
		params.addValue("city", company.getCity());
		params.addValue("state", company.getState());
		params.addValue("zipcode", company.getZipcode());
		params.addValue("country", company.getCountry());
		params.addValue("createdBy", company.getCreatedBy());
		params.addValue("createdTs", company.getCreatedTimestamp());
		params.addValue("lastUpdatedBy", company.getLastUpdatedBy());
		params.addValue("lastUpdatedTs", company.getLastUpdatedTimestamp());

		String sql = companyQueries.getProperty("insertCompanySql");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int count = companyTemplate.update(sql, params, keyHolder);

		if (count > 0) {
			company.setId(keyHolder.getKey().longValue());
		} else {
			logger.error("Company [" + company.getName() + "] was not inserted");
			throw new RuntimeException("Company [" + company.getName() + "] was not inserted");
		}
		return company;
	}

	@Override
	public Company selectCompanyById(final Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		String sql = companyQueries.getProperty("selectCompanyByIdSql");
		return companyTemplate.queryForObject(sql, params, new CompanyRowMapper());
	}

	@Override
	public List<Company> selectPaginatedCompaniesByCriteria(final CompanySearchCriteria criteria) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = companyQueryBuilder.buildPaginatedQueryByCriteria(criteria, params);
		return companyTemplate.query(sql, params, new CompanyRowMapper());
	}

	@Override
	public int selectPaginatedCompaniesByCriteriaCount(final CompanySearchCriteria criteria) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = companyQueryBuilder.buildPagingQueryCountByCriteria(criteria, params);
		return companyTemplate.queryForObject(sql, params, Integer.class);
	}

	@Override
	public Company updateCompany(final Company company) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", company.getName());
		params.addValue("addressLine1", company.getAddressLine1());
		params.addValue("addressLine2", company.getAddressLine2());
		params.addValue("city", company.getCity());
		params.addValue("state", company.getState());
		params.addValue("zipcode", company.getZipcode());
		params.addValue("country", company.getCountry());
		params.addValue("lastUpdatedBy", company.getLastUpdatedBy());
		params.addValue("lastUpdatedTs", company.getLastUpdatedTimestamp());
		params.addValue("id", company.getId());
		params.addValue("updatedVersion", company.getVersion()+1);
		params.addValue("version", company.getVersion());

		String sql = companyQueries.getProperty("updateCompanySql");
		int count = companyTemplate.update(sql, params);
		if (count != 1) {
			logger.error("Company was not updated");
			throw new RuntimeException("Company was not updated!");
		} else {
			logger.info("Company was updated successfully");
			return company;
		}
	}

	@Override
	public List<Company> selectAllCompanyNames() {
		String sql = companyQueries.getProperty("selectDistinctCompaniesSql");
		return companyTemplate.query(sql, new ResultSetExtractor<List<Company>>() {

			@Override
			public List<Company> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Company> companies = new ArrayList<Company>();
				Company company = null;
				while (rs.next()) {
					company = new Company();
					company.setId(rs.getLong("id"));
					company.setName(rs.getString("name"));
					companies.add(company);
				}
				return companies;
			}
		});
	}

	public void setCompanyQueries(Properties companyQueries) {
		this.companyQueries = companyQueries;
	}

	public void setCompanyQueryBuilder(CompanyQueryBuilder companyQueryBuilder) {
		this.companyQueryBuilder = companyQueryBuilder;
	}

	private final class CompanyRowMapper implements RowMapper<Company> {

		@Override
		public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
			Company company = new Company();
			company.setId(rs.getLong("id"));
			company.setName(rs.getString("name"));
			company.setAddressLine1(rs.getString("address_line1"));
			company.setAddressLine2(rs.getString("address_line2"));
			company.setCity(rs.getString("city"));
			company.setState(rs.getString("state"));
			company.setCountry(rs.getString("country"));
			company.setZipcode(rs.getString("zipcode"));
			company.setCreatedBy(rs.getString("created_by"));
			company.setCreatedTimestamp(rs.getTimestamp("created_ts"));
			company.setLastUpdatedBy(rs.getString("last_updated_by"));
			company.setLastUpdatedTimestamp(rs.getTimestamp("last_updated_ts"));
			company.setVersion(rs.getInt("version"));
			return company;
		}
	}
}