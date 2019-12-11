package com.mufg.jms;

import java.util.logging.Logger;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {
   
	private Logger log = Logger.getLogger(Sender.class.getName());
	
	private final JmsTemplate jmsTemplate;

	public Sender(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendMessage(String queue,Object transaction) {
		log.info("OrderTransactionSender :: sendMessage() ::  Started " + transaction);
		jmsTemplate.convertAndSend(queue, transaction);
		log.info("OrderTransactionSender :: sendMessage() ::  Ends");
	}
}
