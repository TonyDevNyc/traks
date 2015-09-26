package com.target.trak.system.batch.common;

import java.util.List;

public interface BatchErrorDao {

	public int getRecordsWithErrorCount(Long jobExecutionId);
	
	public List<BatchError> getRecordsWithErrors(Long jobExecutionId);
	
}
