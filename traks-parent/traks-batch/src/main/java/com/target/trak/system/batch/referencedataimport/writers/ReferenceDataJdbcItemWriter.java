package com.target.trak.system.batch.referencedataimport.writers;

import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.target.trak.system.entity.ReferenceData;

public class ReferenceDataJdbcItemWriter implements ItemWriter<ReferenceData> {

	private NamedParameterJdbcTemplate paramTemplate;

	private Properties queryProperties;

	public ReferenceDataJdbcItemWriter(DataSource dataSource) {
		this.paramTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public void write(List<? extends ReferenceData> referenceDataList) throws Exception {
		MapSqlParameterSource params = null;
		String sql = queryProperties.getProperty("insertSql");

		for (ReferenceData referenceData : referenceDataList) {
			params = new MapSqlParameterSource();
			params.addValue("referenceDataType", referenceData.getReferenceDataType());
			params.addValue("referenceDataLabel", referenceData.getReferenceDataLabel());
			params.addValue("referenceDataValue", referenceData.getReferenceDataValue());
			params.addValue("status", referenceData.getStatus());
			params.addValue("createdBy", referenceData.getCreatedBy());
			params.addValue("createdTimestamp", referenceData.getCreatedTimestamp());
			params.addValue("lastUpdatedBy", referenceData.getLastUpdatedBy());
			params.addValue("lastUpdatedTimestamp", referenceData.getLastUpdatedTimestamp());
			paramTemplate.update(sql, params);
		}
	}

	public void setQueryProperties(Properties queryProperties) {
		this.queryProperties = queryProperties;
	}
}