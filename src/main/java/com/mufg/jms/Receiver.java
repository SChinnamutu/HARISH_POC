package com.mufg.jms;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.mufg.documents.LOGGER;
import com.mufg.service.InboundService;
import com.mufg.service.OutboundService;

@Component
public class Receiver {
    
	private Logger log = Logger.getLogger(Receiver.class.getName());
	
	@Autowired
	private InboundService inService;
	
	@Autowired
	private OutboundService outPutService;
	
	
	@JmsListener(destination = "in.xml.queue", containerFactory = "myFactory")
	@SendTo("out.xml.queue")
	public String receiveXMLMessage(final Message jsonMessage) throws JMSException, JAXBException {
		log.log(Level.INFO, "Listener :: receiveMessage() :: Init");
		if(jsonMessage instanceof TextMessage) {
			TextMessage textMessage = (TextMessage)jsonMessage;
			log.log(Level.INFO, "Listener :: Input :: Data :: " + textMessage.getText());
			LOGGER inLogger = inService.inputConversion(textMessage.getText()); 
			String outLogger = outPutService.outputConversion(inLogger);
			log.log(Level.INFO, "Listener :: OutPut :: Data :: " + outLogger );
			//.sendMessage("out.xml.queue",outLogger);
			log.log(Level.INFO, "Listener :: receiveMessage() :: Ends");
			return outLogger;
		}
		return null;
	}

}
