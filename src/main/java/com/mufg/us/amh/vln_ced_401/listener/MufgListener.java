package com.mufg.us.amh.vln_ced_401.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.mufg.us.amh.vln_ced_401.service.MufgTransformService;
import com.mufg.us.amh.vln_ced_401.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MufgListener {

	@Autowired
	private MufgTransformService service;
	
	@JmsListener(destination = "${receiver.order}")
	@SendTo("${destination.order}")
	public String receiveMessage(final Message jsonMessage) throws JAXBException, JMSException{
		String transformedStringData = null;
		String processId = CommonUtil.generateUniqueId();
		log.info("MufgListener ::receiveMessage() " + processId);
		if(jsonMessage instanceof TextMessage) {
			TextMessage textMessage = (TextMessage)jsonMessage;
			log.info("MufgListener :: Received message " + jsonMessage);
			transformedStringData = service.processTransformation(textMessage.getText(),processId);
			log.info("MufgListener :: Sent message successfully  " + processId);
		}
		return transformedStringData;
	}

}
