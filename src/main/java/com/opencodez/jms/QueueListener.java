package com.opencodez.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.gson.JsonSyntaxException;
import com.opencodez.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QueueListener {

	@Autowired
	private UserService service;
	
	@JmsListener(destination = "pdd2.queue", containerFactory = "myFactory")
	public void receiveMessage(final Message jsonMessage) throws JsonSyntaxException, JMSException {
		log.info("QueueSender :: receiveMessage() ::  Started ");
		if(jsonMessage instanceof TextMessage) {
			TextMessage textMessage = (TextMessage)jsonMessage;
			service.processInsert(textMessage.getText());
		}
		log.info("QueueSender :: receiveMessage() ::  Ends");
	}
	
}
