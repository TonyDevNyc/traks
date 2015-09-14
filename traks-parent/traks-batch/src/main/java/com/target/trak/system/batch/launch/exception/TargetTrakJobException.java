package com.target.trak.system.batch.launch.exception;

import com.target.trak.system.batch.launch.dto.TargetTrakJobResponse;

public class TargetTrakJobException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private TargetTrakJobResponse response; 

	public TargetTrakJobException() {
		super();
	}

	public TargetTrakJobException(String message) {
		super(message);
	}
	
	public TargetTrakJobException(String message, TargetTrakJobResponse response) {
		super(message);
		this.response = response;
	}

	public TargetTrakJobResponse getResponse() {
		return response;
	}

	public void setResponse(TargetTrakJobResponse response) {
		this.response = response;
	}
}