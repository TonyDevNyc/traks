package com.target.trak.system.batch.referencedataimport.tasklets;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.target.trak.system.batch.common.BatchError;
import com.target.trak.system.batch.common.BatchErrorDao;
import com.target.trak.system.service.EmailService;

public class EmailReferenceDataReportTasklet implements Tasklet {

	private EmailService emailService;
	private BatchErrorDao batchErrorDao;
	
	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		JobExecution jobExecution = chunkContext.getStepContext().getStepExecution().getJobExecution();
		Long jobExecutionId = jobExecution.getId();
		int count = batchErrorDao.getRecordsWithErrorCount(jobExecutionId);
		List<BatchError> batchErrors = null;
		
		if (count > 0) {
			batchErrors = batchErrorDao.getRecordsWithErrors(jobExecutionId);
		} 
		
		String message = buildEmailMessage(jobExecution, count, batchErrors);
		emailService.sendEmailMessage(getEmailRecipients(), "Reference Data Import Job Report", message);
		return RepeatStatus.FINISHED;
	}
	
	private List<String> getEmailRecipients() {
		List<String> list = new ArrayList<String>();
		list.add("csullivan1020@aol.com");
		list.add("antoine.marrajr@gmail.com");
		return list;
	}
	
	private String buildEmailMessage(JobExecution jobExecution, int count, List<BatchError> batchErrors) {
		StringBuilder builder = new StringBuilder();
		builder.append("The Reference Data Import Job (Execution ID: ").append(jobExecution.getId()).append(")");
		builder.append(" finished with a status: [").append(jobExecution.getStatus()).append("]");
		if (count > 0) {
			builder.append("\n");
			builder.append("The following records have errors: ").append("\n");
			for (BatchError batchError : batchErrors) {
				builder.append(batchError.getLineData()).append(" has error: [");
				builder.append(batchError.getErrorReason()).append("]").append("\n");
			}
			builder.append("Please check the import file and check batch admin");
		}
		return builder.toString();
	}
	
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public void setBatchErrorDao(BatchErrorDao batchErrorDao) {
		this.batchErrorDao = batchErrorDao;
	}
}
