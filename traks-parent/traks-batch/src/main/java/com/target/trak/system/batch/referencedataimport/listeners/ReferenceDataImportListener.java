package com.target.trak.system.batch.referencedataimport.listeners;

import org.apache.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;

public class ReferenceDataImportListener implements JobExecutionListener {

	private static final Logger logger = Logger.getLogger(ReferenceDataImportListener.class);
	private static final String TARGET_DIR = "targetDirectory";
	private static final String TARGET_FILE = "targetFile";
	private static final String USERID = "userid";

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			// 
		} 
		else if (BatchStatus.FAILED == jobExecution.getStatus()) {
			// send email
		}

	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		StringBuilder builder = new StringBuilder();
		JobParameters params = jobExecution.getJobParameters();
		builder.append("User ").append(params.getString(USERID));
		builder.append(" started job: [").append(jobExecution.getJobInstance().getJobName()).append("]");
		builder.append(" with [ file: ").append(TARGET_DIR).append(TARGET_FILE).append("]");
		logger.error(builder.toString());
	}

}
