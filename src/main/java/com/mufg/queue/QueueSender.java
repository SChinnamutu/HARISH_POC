package com.mufg.queue;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QueueSender {

	private final JmsTemplate jmsTemplate;

	public QueueSender(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendMessage(String queue,Object transaction) {
		log.info("QueueSender :: sendMessage() ::  Started " + transaction);
		jmsTemplate.convertAndSend(queue, transaction);
		log.info("QueueSender :: sendMessage() ::  Ends");
	}
}
