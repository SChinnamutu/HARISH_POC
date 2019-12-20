package com.mufg.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
 
@RestController
@Slf4j
public class JobInvokerController {
 
    @Autowired
    JobLauncher jobLauncher;
 
    @Autowired
    @Qualifier("accountJob")
    Job accountKeeperJob;
    
    @RequestMapping("/run-batch-job")
    public String handle() throws Exception {
    	log.info("JobInvokerController :: handle() :: Init");
        JobParameters jobParameters = new JobParametersBuilder()
        								.addString("source", "Spring Boot")
        								.toJobParameters();
        jobLauncher.run(accountKeeperJob, jobParameters);
        log.info("JobInvokerController :: handle() :: Batch job has been invoked");
        log.info("JobInvokerController :: handle() :: End");
        return "Batch job has been invoked";
    }
    
}

