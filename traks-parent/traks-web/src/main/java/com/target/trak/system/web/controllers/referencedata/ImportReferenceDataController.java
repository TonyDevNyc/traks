package com.target.trak.system.web.controllers.referencedata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.target.trak.system.batch.launch.AsyncJobLauncherService;
import com.target.trak.system.batch.launch.dto.TargetTrakJobRequest;
import com.target.trak.system.batch.launch.dto.TargetTrakJobResponse;

@Controller
public class ImportReferenceDataController {

	private static final Logger logger = Logger.getLogger(ImportReferenceDataController.class);
	private static final String REFERENCE_DATA_IMPORT_JOB = "importReferenceDataJob";
	private static final String SAN_STORAGE_LOCATION = "/Users/amarrajr/apps/traks/san/traks-batch/reference_data_import_job/";

	private AsyncJobLauncherService asyncJobLauncherService;

	@RequestMapping(value = "/referencedata/importReferenceData.json", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> handleFileUpload(MultipartFile file) {
		logger.info("Attempting to write file " + file.getOriginalFilename());
		writeFileToStorageAreaNetwork(file);
		Map<String, Object> params = createJobParametersMap(file.getOriginalFilename());
		TargetTrakJobRequest jobRequest = new TargetTrakJobRequest();
		jobRequest.setJobName(REFERENCE_DATA_IMPORT_JOB);
		jobRequest.setJobParameters(params);

		TargetTrakJobResponse jobResponse = asyncJobLauncherService.launchJob(jobRequest);
		Map<String, Object> response = new HashMap<String, Object>();

		response.put("message", jobResponse.getMessage());
		response.put("success", true);

		return response;
	}

	private Map<String, Object> createJobParametersMap(String fileName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("targetDirectory", SAN_STORAGE_LOCATION);
		params.put("targetFile", fileName);
		params.put("userid", "tina");
		
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		params.put("executionStartTime", fmt.print(System.currentTimeMillis()));
		return params;
	}

	private void writeFileToStorageAreaNetwork(MultipartFile file) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String fileName = file.getOriginalFilename();
		File newFile = null;
		String newFileLocation = SAN_STORAGE_LOCATION + fileName;

		try {
			inputStream = file.getInputStream();
			newFile = new File(newFileLocation);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.close();
			logger.info("Successfully wrote file " + newFileLocation);
		} catch (IOException e) {
			logger.error("Error uploading file", e);
		}
	}

	public void setAsyncJobLauncherService(AsyncJobLauncherService asyncJobLauncherService) {
		this.asyncJobLauncherService = asyncJobLauncherService;
	}
}