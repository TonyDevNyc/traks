package com.target.trak.system.batch.launch.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import com.target.trak.system.batch.launch.AsyncJobLauncherService;
import com.target.trak.system.batch.launch.dto.TargetTrakJobRequest;
import com.target.trak.system.batch.launch.dto.TargetTrakJobResponse;
import com.target.trak.system.batch.launch.exception.TargetTrakJobException;

public class AsyncJobLauncherServiceImpl implements AsyncJobLauncherService {

	private static final Logger logger = Logger.getLogger(AsyncJobLauncherServiceImpl.class);
	
	private JobLauncher asyncJobLauncher;
	
	private JobRegistry mapJobRegistry;
	
	@Override
	public TargetTrakJobResponse launchJob(TargetTrakJobRequest request) throws TargetTrakJobException {
		// TODO execute validations and then throw exception if failed
		
		TargetTrakJobResponse response = new TargetTrakJobResponse();
		JobParameters jobParameters = buildJobParameters(request.getJobParameters());
		Job job = null;
		JobExecution jobExecution = null;
		
		try {
			job = mapJobRegistry.getJob(request.getJobName());
			jobExecution = asyncJobLauncher.run(job, jobParameters);
			response.setSuccess(Boolean.TRUE);
			response.setMessage("Job named: named: [" + request.getJobName() +"(Execution ID: "+jobExecution.getJobId()+")" +"] has been started successfully.<br/>Please check the batch admin screen for status.");
		} catch (NoSuchJobException e) {
			String errorMessage = "Error finding job named: [" + request.getJobName() +"]";
			logger.error(errorMessage, e);
			response.setSuccess(Boolean.FALSE);
			response.setMessage(errorMessage);
			throw new TargetTrakJobException(errorMessage, response);
		}
		catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
			String errorMessage = "Error starting job named: [" + request.getJobName() +"]";
			logger.error(errorMessage, e);
			response.setSuccess(Boolean.FALSE);
			response.setMessage(errorMessage);
		}
		return response;
	}
	
	private JobParameters buildJobParameters(Map<String, Object> jobParameters) {
		if (jobParameters == null || jobParameters.isEmpty()) {
			return new JobParameters();
		}
		
		JobParametersBuilder builder = new JobParametersBuilder();
		for (Map.Entry<String, Object> entry : jobParameters.entrySet()) {
			builder.addString(entry.getKey(), entry.getValue().toString());
		}
		return builder.toJobParameters();
	}

	public void setAsyncJobLauncher(JobLauncher asyncJobLauncher) {
		this.asyncJobLauncher = asyncJobLauncher;
	}

	public void setMapJobRegistry(JobRegistry mapJobRegistry) {
		this.mapJobRegistry = mapJobRegistry;
	}
}