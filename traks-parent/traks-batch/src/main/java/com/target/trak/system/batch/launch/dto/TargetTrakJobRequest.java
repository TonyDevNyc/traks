package com.target.trak.system.batch.launch.dto;

import java.util.Map;

public class TargetTrakJobRequest {

	private String jobName;
	private Map<String, Object> jobParameters;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Map<String, Object> getJobParameters() {
		return jobParameters;
	}

	public void setJobParameters(Map<String, Object> jobParameters) {
		this.jobParameters = jobParameters;
	}
}