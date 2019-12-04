package com.goomo.outbound;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.goomo.DataTransformationApplication;

@Service
public class OutPutService {

	private Logger log = Logger.getLogger(DataTransformationApplication.class.getName());
	
	public String outputConversion(com.goomo.inbound.LOGGER inLogger) throws JAXBException {
		log.log(Level.INFO, "OutPutService :: outputConversion() :: Init");
		LOGGER outLogger = new LOGGER();
		List<FIELD> outFieldList  = new ArrayList<FIELD>();
		FIELD outField = null;
		for(com.goomo.inbound.FIELD inField : inLogger.getFIELD() ) {
			if(!OutPutService.removedTags().contains(inField.getName())) {
				outField = new FIELD();
				BeanUtils.copyProperties(inField, outField);
				outFieldList.add(outField);
			}
		}
		outLogger.setFIELD(outFieldList);
		StringWriter writer = new StringWriter();
		JAXBContext jaxbContext = JAXBContext.newInstance(LOGGER.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    jaxbMarshaller.marshal(outLogger, writer);
	    log.log(Level.INFO, "OutPutService :: outputConversion() :: Ends");
		return writer.toString();
	}
	
	
	public static List<String> removedTags(){
		List<String> removedTags = new ArrayList<String>();
		removedTags.add("cd_file");
		removedTags.add("id_err_typ");
		removedTags.add("cd_svrty_lvl");
		return removedTags;
	}
}
