package com.target.trak.system.batch.referencedataimport.listeners;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.target.trak.system.entity.ReferenceData;

public class ReferenceDataSkipJdbcListener implements SkipListener<ReferenceData, ReferenceData>, StepExecutionListener {

	private NamedParameterJdbcTemplate paramTemplate;
	private JobExecution jobExecution;
	private Properties queryProps;
	
	public ReferenceDataSkipJdbcListener(DataSource dataSource) {
		this.paramTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public void onSkipInProcess(ReferenceData referenceDataItem, Throwable error) {
		Long jobExecId = jobExecution.getId();
		String sql = queryProps.getProperty("insertRecordsWithErrorsSql");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("jobExecutionId", jobExecId);
		params.addValue("lineData", buildLineData(referenceDataItem));
		params.addValue("errorReason", buildErrorReason(error));
	
		paramTemplate.update(sql, params);
	}
	
	private String buildErrorReason(Throwable error) {
		StringBuilder builder = new StringBuilder();
		
		if (error instanceof ValidationException) {
			builder.append(error.getMessage());
		} else {
			builder.append("Error processing the record.");
		}
		
		return builder.toString();
	}

	private String buildLineData(ReferenceData referenceDataItem) {
		StringBuilder builder = new StringBuilder();
		builder.append("Type: [").append(referenceDataItem.getReferenceDataType()).append("] ");
		builder.append("Label: [").append(referenceDataItem.getReferenceDataLabel()).append("] ");
		builder.append("Value: [").append(referenceDataItem.getReferenceDataValue()).append("] ");
		builder.append("Status: [").append(referenceDataItem.getStatus()).append("]");
		return builder.toString();
	}
	
	@Override
	public void onSkipInRead(Throwable t) {
		System.out.println("Skipping in reader");
		
	}

	@Override
	public void onSkipInWrite(ReferenceData referenceDataItem, Throwable t) {
		System.out.println("Skipping in writer");
		
	}
	
	@Override
	public ExitStatus afterStep(StepExecution arg0) {
		
		return null;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		jobExecution = stepExecution.getJobExecution();
	}	
	
	public void setJobExecution(JobExecution jobExecution) {
		this.jobExecution = jobExecution;
	}

	public void setParamTemplate(NamedParameterJdbcTemplate paramTemplate) {
		this.paramTemplate = paramTemplate;
	}

	public void setQueryProps(Properties queryProps) {
		this.queryProps = queryProps;
	}
}