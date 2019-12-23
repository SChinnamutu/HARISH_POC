package com.mufg;

import javax.jms.ConnectionFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableJms
@Slf4j
public class SpringBatchApplication implements CommandLineRunner {
	
	 public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
		log.info("SpringBatchApplication started");
	 }
	
	 @Override
	 public void run(String... args) throws Exception {
		
	 }
	 
	 @Bean
	 public RestTemplate template() {
		 return new RestTemplate();
	 }
	
	 // Only required due to defining myFactory in the receiver
	 @Bean
	 public JmsListenerContainerFactory<?> myFactory(
	      ConnectionFactory connectionFactory,
	      DefaultJmsListenerContainerFactoryConfigurer configurer) {
	    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	    factory.setErrorHandler(t -> System.out.println("An error has occurred in the transaction"));
	    configurer.configure(factory, connectionFactory);
	    return factory;
	  }
	
	  // Serialize message content to json using TextMessage
	  @Bean
	  public MessageConverter jacksonJmsMessageConverter() {
	    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	    converter.setTargetType(MessageType.TEXT);
	    converter.setTypeIdPropertyName("_type");
	    return converter;
	  }
}
