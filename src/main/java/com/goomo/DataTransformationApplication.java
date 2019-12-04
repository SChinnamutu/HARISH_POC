package com.goomo;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class DataTransformationApplication {
	
	private static Logger log = Logger.getLogger(DataTransformationApplication.class.getName());

    public static void main(String[] args) {
    	log.log(Level.INFO, "DataTransformationApplication service starting");
        SpringApplication.run(DataTransformationApplication.class, args);
    }

}