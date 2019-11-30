package com.devglan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfig {

	
	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("Conversion Engine Service").apiInfo(apiInfo()).select()
               .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
               .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.cloud")))
               .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.data.rest.webmvc")))
               .build();
    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Conversion Engine Micro Service")
				.description(
				"MB Conversion Engine REST API's micro service Developed in spring boot. "
				+ "This is the centralized micro service. Objective of this application is to "
				+ "consume all micro services to convert data.")
				.contact(new Contact("Harish Kumar", "", "harish.kumar@mb.com"))
				.license("Apache License Version 2.0").version("1.1.0").build();
	}

}
