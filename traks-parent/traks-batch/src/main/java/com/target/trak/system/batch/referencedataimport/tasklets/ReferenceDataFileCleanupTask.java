package com.target.trak.system.batch.referencedataimport.tasklets;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.util.StringUtils;

public class ReferenceDataFileCleanupTask implements Tasklet {

	private static final String TARGET_DIRECTORY = "targetDirectory";
	private static final String TARGET_FILE = "targetFile";
	private static final Logger logger = Logger.getLogger(ReferenceDataFileCleanupTask.class);
	
	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		JobExecution jobExecution = chunkContext.getStepContext().getStepExecution().getJobExecution();
		JobParameters jobParameters = jobExecution.getJobParameters();
		String targetDirectory = jobParameters.getString(TARGET_DIRECTORY);
		String targetFile = jobParameters.getString(TARGET_FILE);
		
		if (StringUtils.isEmpty(targetDirectory) || StringUtils.isEmpty(targetFile)) {
			logger.error("Error cleaning up files due to blank Job Params: targetDirectory: ["+targetDirectory+"] targetFile: [" + targetFile+"]");
			throw new Exception("Error cleaning up files due to blank Job Params: targetDirectory: ["+targetDirectory+"] targetFile: [" + targetFile+"]");
		}
		
		String fileName = targetDirectory + targetFile;
		logger.error("Attempting to delete file: " + fileName);
		File file = new File(fileName);
		file.delete();
		logger.error("Successfully deleted file: " + fileName);
		return RepeatStatus.FINISHED;
	}

}
