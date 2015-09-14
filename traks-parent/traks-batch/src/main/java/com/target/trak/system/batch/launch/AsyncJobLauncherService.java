package com.target.trak.system.batch.launch;

import com.target.trak.system.batch.launch.dto.TargetTrakJobRequest;
import com.target.trak.system.batch.launch.dto.TargetTrakJobResponse;
import com.target.trak.system.batch.launch.exception.TargetTrakJobException;

public interface AsyncJobLauncherService {

	public TargetTrakJobResponse launchJob(TargetTrakJobRequest request) throws TargetTrakJobException;
}
