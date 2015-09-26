package com.target.trak.system.batch.common.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.target.trak.system.batch.common.BatchError;
import com.target.trak.system.batch.common.BatchErrorDao;

public class BatchErrorDaoImpl implements BatchErrorDao {

	private NamedParameterJdbcTemplate paramTemplate;
	private Properties queryProperties;

	public BatchErrorDaoImpl(DataSource dataSource) {
		paramTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public int getRecordsWithErrorCount(Long jobExecutionId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("jobExecutionId", jobExecutionId);
		String sql = queryProperties.getProperty("getRecordsWithErrorsCountSql");
		return paramTemplate.queryForObject(sql, params, Integer.class);
	}

	@Override
	public List<BatchError> getRecordsWithErrors(Long jobExecutionId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("jobExecutionId", jobExecutionId);
		String sql = queryProperties.getProperty("getRecordsWithErrorsSql");
		return paramTemplate.query(sql, params, new BatchErrorRowMapper());
	}
	
	public void setParamTemplate(NamedParameterJdbcTemplate paramTemplate) {
		this.paramTemplate = paramTemplate;
	}

	public void setQueryProperties(Properties queryProperties) {
		this.queryProperties = queryProperties;
	}

	private final class BatchErrorRowMapper implements RowMapper<BatchError> {

		@Override
		public BatchError mapRow(ResultSet rs, int rowNum) throws SQLException {
			BatchError batchError = new BatchError();
			batchError.setId(rs.getLong("ID"));
			batchError.setJobExecutionId(rs.getLong("JOB_EXECUTION_ID"));
			batchError.setLineData(rs.getString("LINE_DATA"));
			batchError.setErrorReason(rs.getString("ERROR_REASON"));
			return batchError;
		}	
	}
}