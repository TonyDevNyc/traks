package com.target.trak.system.batch.referencedataimport.processors;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.target.trak.system.entity.ReferenceData;

public class ExistingReferenceDataFilterItemProcessor implements ItemProcessor<ReferenceData, ReferenceData> {

	private static final Logger logger = Logger.getLogger(ExistingReferenceDataFilterItemProcessor.class);
	
	private NamedParameterJdbcTemplate paramTemplate;
	private Properties queryProperties;

	public ExistingReferenceDataFilterItemProcessor(DataSource dataSource) {
		this.paramTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public ReferenceData process(ReferenceData referenceDataItem) throws Exception {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("referenceDataType", referenceDataItem.getReferenceDataType());
		params.addValue("referenceDataLabel", referenceDataItem.getReferenceDataLabel());
		params.addValue("referenceDataValue", referenceDataItem.getReferenceDataValue());
		String sql = queryProperties.getProperty("referenceDataExistsSql");
		
		int count = paramTemplate.queryForObject(sql, params, Integer.class);
		if (count == 0) {
			logger.info("Reference not found, count = " + count+". Returning reference data item");
			return referenceDataItem;
		}
		logger.info("Reference Data found, count = " + count+". Returning null to filter reference data item");
		return null;
	}
	
	public void setParamTemplate(NamedParameterJdbcTemplate paramTemplate) {
		this.paramTemplate = paramTemplate;
	}

	public void setQueryProperties(Properties queryProperties) {
		this.queryProperties = queryProperties;
	}
}