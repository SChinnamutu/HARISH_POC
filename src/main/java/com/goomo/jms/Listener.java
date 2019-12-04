package com.goomo.jms;

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

import com.goomo.DataTransformationApplication;
import com.goomo.inbound.InService;
import com.goomo.inbound.LOGGER;
import com.goomo.outbound.OutPutService;

@Component
public class Listener {

	private Logger log = Logger.getLogger(DataTransformationApplication.class.getName());
	
	@Autowired
	private InService inService;
	
	@Autowired
	private OutPutService outPutService;
	
	@JmsListener(destination = "inbound.queue")
	@SendTo("outbound.queue")
	public String receiveMessage(final Message jsonMessage) throws JMSException, JAXBException {
		log.log(Level.INFO, "Listener :: receiveMessage() :: Init");
		if(jsonMessage instanceof TextMessage) {
			TextMessage textMessage = (TextMessage)jsonMessage;
			log.log(Level.INFO, "Listener :: Input :: Data :: " + textMessage.getText());
			LOGGER inLogger = inService.inputConversion(textMessage.getText()); 
			String outLogger = outPutService.outputConversion(inLogger);
			log.log(Level.INFO, "Listener :: OutPut :: Data :: " + outLogger );
			log.log(Level.INFO, "Listener :: receiveMessage() :: Ends");
			return outLogger;
		}
		return null;
	}

}
