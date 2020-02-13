package com.mufg.us.amh.vln_ced_401.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class JMS_SOD_Config {

	@Value("${spring.activemq.broker-url}")
	private String BROKER_URL;

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(BROKER_URL);
		return connectionFactory;
	}

	@Bean
    public JmsComponent activemq() {
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setDeliveryMode(1);
        jmsComponent.setConnectionFactory(connectionFactory());
        return jmsComponent;
    }
}