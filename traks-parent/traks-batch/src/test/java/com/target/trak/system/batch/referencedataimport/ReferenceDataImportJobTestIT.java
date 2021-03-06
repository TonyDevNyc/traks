package com.target.trak.system.batch.referencedataimport;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/traks-batch-test-config.xml" })
public class ReferenceDataImportJobTestIT {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() throws Exception {
		jdbcTemplate.update("delete from target_trak_dw.reference_data");
	}

	@Test
	public void importReferenceData_forSuccessfulExecution() throws Exception {
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addString("targetDirectory", "/Users/amarrajr/apps/");
		builder.addString("targetFile", "2015_09_reference_data.csv");
		builder.addString("userid", "tina");
		builder.addDate("timestamp", new Timestamp(new Date().getTime()));

		JobExecution jobExecution = jobLauncher.run(job, builder.toJobParameters());
		BatchStatus status = jobExecution.getStatus();
		Assert.assertEquals("Post import count is ", BatchStatus.COMPLETED, status);
	}
}