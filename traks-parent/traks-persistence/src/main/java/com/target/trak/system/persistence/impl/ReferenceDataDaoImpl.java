package com.target.trak.system.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.target.trak.system.entity.ReferenceData;
import com.target.trak.system.entity.criteria.TextSearchCriteria;
import com.target.trak.system.persistence.ReferenceDataDao;
import com.target.trak.system.persistence.builder.ReferenceDataQueryBuilder;

public class ReferenceDataDaoImpl implements ReferenceDataDao {

	private final Logger logger = Logger.getLogger(getClass());

	private Properties referenceDataQueries;

	private ReferenceDataQueryBuilder refDataQueryBuilder;

	private NamedParameterJdbcTemplate refDataTemplate;

	public ReferenceDataDaoImpl(DataSource dataSource) {
		refDataTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public ReferenceData insertReferenceData(final ReferenceData referenceData) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("referenceDataType", referenceData.getReferenceDataType());
		params.addValue("referenceDataLabel", referenceData.getReferenceDataLabel());
		params.addValue("referenceDataValue", referenceData.getReferenceDataValue());
		params.addValue("createdBy", referenceData.getCreatedBy());
		params.addValue("createdTimestamp", referenceData.getCreatedTimestamp());
		params.addValue("lastUpdatedBy", referenceData.getLastUpdatedBy());
		params.addValue("lastUpdatedTimestamp", referenceData.getLastUpdatedTimestamp());
		params.addValue("status", referenceData.getStatus());

		String sql = referenceDataQueries.getProperty("insertReferenceDataSql");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int count = refDataTemplate.update(sql, params, keyHolder);

		if (count > 0) {
			referenceData.setId(keyHolder.getKey().longValue());
		} else {
			logger.error("Reference Data was not created");
			throw new RuntimeException("Reference Data was not created");
		}
		return referenceData;
	}

	@Override
	public int selectDefaultReferenceDataCount(final TextSearchCriteria criteria) {
		String sql = referenceDataQueries.getProperty("baseReferenceDataPagingSql");
		return refDataTemplate.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
	}

	@Override
	public List<ReferenceData> selectPaginatedReferenceDataBySearchCriteria(final TextSearchCriteria criteria) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = refDataQueryBuilder.buildPaginatedQueryByCriteria(criteria, params);
		return refDataTemplate.query(sql, params, new ReferenceDataRowMapper());
	}

	@Override
	public int selectReferenceDataBySearchCriteriaCount(final TextSearchCriteria criteria) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = refDataQueryBuilder.buildCountQueryByCriteria(criteria, params);
		return refDataTemplate.queryForObject(sql, params, Integer.class);
	}

	@Override
	public boolean referenceDataAlreadyExists(final String type, final String label, final String value) {
		String sql = referenceDataQueries.getProperty("selectCountReferenceDataSql");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("type", type);
		params.addValue("label", label);
		params.addValue("value", value);

		int count = refDataTemplate.queryForObject(sql, params, Integer.class);
		return (count > 0) ? true : false;
	}

	@Override
	public List<ReferenceData> getReferenceDataTypes() {
		String sql = referenceDataQueries.getProperty("selectReferenceTypesSql");
		return refDataTemplate.query(sql, new ResultSetExtractor<List<ReferenceData>>() {

			@Override
			public List<ReferenceData> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<ReferenceData> typesList = new ArrayList<ReferenceData>();
				ReferenceData domain = null;
				while (rs.next()) {
					domain = new ReferenceData();
					domain.setReferenceDataType(rs.getString("reference_data_type"));
					typesList.add(domain);
				}
				return typesList;
			}
		});
	}

	@Override
	public ReferenceData updateReferenceData(final ReferenceData referenceData) {
		String sql = referenceDataQueries.getProperty("updateReferenceTypesSql");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("label", referenceData.getReferenceDataLabel());
		params.addValue("value", referenceData.getReferenceDataValue());
		params.addValue("id", referenceData.getId());
		params.addValue("lastUpdatedBy", referenceData.getLastUpdatedBy());
		params.addValue("lastUpdatedTimestamp", referenceData.getLastUpdatedTimestamp());
		params.addValue("status", referenceData.getStatus());
		params.addValue("newVersion", referenceData.getVersion()+1);
		params.addValue("version", referenceData.getVersion());
		
		int count = refDataTemplate.update(sql, params);
		if (count != 1) {
			logger.error("Reference Data was not updated since it has been updated by another user.");
			throw new RuntimeException("Reference Data was not updated since it has been updated by another user.");
		} else {
			logger.info("Reference Data was updated successfully");
			return referenceData;
		}
	}

	@Override
	public void deleteReferenceData(final ReferenceData referenceData) {
		String sql = referenceDataQueries.getProperty("deleteReferenceTypesSql");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", referenceData.getId());

		int count = refDataTemplate.update(sql, params);
		if (count != 1) {
			logger.error("Reference Data was not deleted");
			throw new RuntimeException("Reference Data was not deleted!");
		} else {
			logger.info("Reference Data was deleted successfully");
		}
	}

	@Override
	public List<ReferenceData> selectReferenceDataByType(final String referenceDataType) {
		String sql = referenceDataQueries.getProperty("selectReferenceDataByTypeSql");
		logger.info("Executing: " + sql);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("referenceDataType", referenceDataType);

		return refDataTemplate.query(sql, params, new ReferenceDataRowMapper());
	}

	@Override
	public ReferenceData selectReferenceDataByFields(final String type, final String label, final String value) {
		String sql = referenceDataQueries.getProperty("selectReferenceDataByFieldsSql");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("type", type);
		params.addValue("label", label);
		params.addValue("value", value);
		ReferenceData domain = null;

		try {
			domain = refDataTemplate.queryForObject(sql, params, new ReferenceDataRowMapper());
		} catch (EmptyResultDataAccessException e) {
			logger.error("No refererence data found for [" + type + ", " + label + ", " + value + "]", e);
		}
		return domain;
	}

	@Override
	public ReferenceData selectReferenceDataById(final Long id) {
		String sql = referenceDataQueries.getProperty("selectReferenceDataByIdSql");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		ReferenceData entity = null;
		
		try {
			entity = refDataTemplate.queryForObject(sql, params, new ReferenceDataRowMapper());
		} catch (EmptyResultDataAccessException e) {
			logger.error("No refererence data found for id [" + id + "]", e);
		}
		return entity;
	}

	public void setReferenceDataQueries(Properties referenceDataQueries) {
		this.referenceDataQueries = referenceDataQueries;
	}

	public void setRefDataQueryBuilder(ReferenceDataQueryBuilder refDataQueryBuilder) {
		this.refDataQueryBuilder = refDataQueryBuilder;
	}

	private final class ReferenceDataRowMapper implements RowMapper<ReferenceData> {

		@Override
		public ReferenceData mapRow(ResultSet rs, int rowNum) throws SQLException {
			ReferenceData domain = new ReferenceData();
			domain.setId(rs.getLong("id"));
			domain.setReferenceDataType(rs.getString("reference_data_type"));
			domain.setReferenceDataLabel(rs.getString("reference_data_label"));
			domain.setReferenceDataValue(rs.getString("reference_data_value"));
			domain.setCreatedBy(rs.getString("created_by"));
			domain.setCreatedTimestamp(rs.getTimestamp("created_timestamp"));
			domain.setLastUpdatedBy(rs.getString("last_updated_by"));
			domain.setLastUpdatedTimestamp(rs.getTimestamp("last_updated_timestamp"));
			domain.setStatus(rs.getString("status"));
			domain.setVersion(rs.getInt("version"));
			return domain;
		}
	}

}