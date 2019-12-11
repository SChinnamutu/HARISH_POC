package com.mufg.service;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Service;

import com.mufg.documents.LOGGER;

@Service
public class InboundService {

	private Logger log = Logger.getLogger(InboundService.class.getName());
	
	public LOGGER inputConversion(String payload) throws JAXBException {
		log.log(Level.INFO, "InService :: inputConversion() :: Init");
		Unmarshaller unmarshaller = null ;
		try {
			JAXBContext jc = JAXBContext.newInstance(LOGGER.class);
			unmarshaller = jc.createUnmarshaller();
		} catch (Exception e) {
			e.printStackTrace();
		}
		StreamSource streamSource = new StreamSource(new StringReader(payload));
		JAXBElement<LOGGER> je = unmarshaller.unmarshal(streamSource, LOGGER.class);
		LOGGER logger = (LOGGER) je.getValue();
		log.log(Level.INFO, "InService :: inputConversion() :: Ends");
		return logger;
	}
	
}
