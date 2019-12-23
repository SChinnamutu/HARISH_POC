package com.mufg.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobInvokerScheduler {

	
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	@Qualifier("accountJob")
	Job accountKeeperJob;

	
	@Scheduled(cron = "	0 0 12 1/1 * ?")
	public String handle() throws Exception {
		log.info("JobInvokerScheduler :: handle() :: Init");
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis()).toJobParameters();
		jobLauncher.run(accountKeeperJob, jobParameters);
		log.info("JobInvokerScheduler :: handle() :: Batch job has been invoked");
		log.info("JobInvokerScheduler :: handle() :: End");
		return "Batch job has been invoked";
	}
	    
}
