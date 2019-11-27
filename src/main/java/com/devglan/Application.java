package com.devglan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
@EntityScan(basePackages = { "com.devglan.model" })
@EnableJpaRepositories(basePackages = { "com.devglan.repository" })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}