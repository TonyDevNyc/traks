package com.target.trak.system.batch.common;

public class BatchError {

	private Long id;
	private Long jobExecutionId;
	private String lineData;
	private String errorReason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobExecutionId() {
		return jobExecutionId;
	}

	public void setJobExecutionId(Long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public String getLineData() {
		return lineData;
	}

	public void setLineData(String lineData) {
		this.lineData = lineData;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

}
