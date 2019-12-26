package com.mufg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("File Batch Processing Service").apiInfo(apiInfo()).select()
               .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
               .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.cloud")))
               .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.data.rest.webmvc")))
               .build();
    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("File Batch Processing Micro Service")
				.description("File Batch Processing  REST API's micro service Developed in spring boot.")
				.contact(new Contact("Harih T","","harisht@mufg.com"))
				.license("Apache License Version 2.0")
				.version("1.1.0")
				.build();
	}
}
