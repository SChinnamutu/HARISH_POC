package com.goomo.inbound;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Service;

import com.goomo.DataTransformationApplication;

@Service
public class InService {

	private Logger log = Logger.getLogger(DataTransformationApplication.class.getName());
	
	public LOGGER inputConversion(String payload) throws JAXBException {
		log.log(Level.INFO, "InService :: inputConversion() :: Init");
		JAXBContext jc = JAXBContext.newInstance(LOGGER.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StreamSource streamSource = new StreamSource(new StringReader(payload));
		JAXBElement<LOGGER> je = unmarshaller.unmarshal(streamSource, LOGGER.class);
		LOGGER logger = (LOGGER) je.getValue();
		log.log(Level.INFO, "InService :: inputConversion() :: Ends");
		return logger;
	}
	
}
