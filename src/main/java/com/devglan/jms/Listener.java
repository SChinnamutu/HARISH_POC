package com.devglan.jms;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.devglan.io.BaseResponse;
import com.devglan.service.AuditService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Listener {

	@Autowired
	private AuditService service;
	
	@JmsListener(destination = "inbound.queue")
	@SendTo("outbound.queue")
	public String receiveMessage(final Message jsonMessage) throws JMSException, JsonParseException, JsonMappingException, IOException {
		BaseResponse transformedData = null;
		String transformedStringData = null;
		log.info("InBoundQueue :: Received message " + jsonMessage);
		if(jsonMessage instanceof TextMessage) {
			TextMessage textMessage = (TextMessage)jsonMessage;
			transformedData = service.processEnrich(textMessage.getText());
			transformedStringData = new Gson().toJson(transformedData);
			log.info("OutBound :: Sending message " + transformedStringData);
		}
		return transformedStringData;
	}

}
