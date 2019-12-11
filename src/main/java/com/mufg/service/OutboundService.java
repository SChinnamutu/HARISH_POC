package com.mufg.service;

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

import com.mufg.documents.FIELD;
import com.mufg.documents.LOGGER;

@Service
public class OutboundService {

	private Logger log = Logger.getLogger(OutboundService.class.getName());
	
	public String outputConversion(LOGGER inLogger) throws JAXBException {
		log.log(Level.INFO, "OutPutService :: outputConversion() :: Init");
		List<FIELD> outFieldList  = new ArrayList<FIELD>();
		FIELD outField = null;
		for(FIELD inField : inLogger.getFIELD() ) {
			if(!OutboundService.removedTags().contains(inField.getName())) {
				outField = new FIELD();
				BeanUtils.copyProperties(inField, outField);
				outFieldList.add(outField);
			}
		}
		//Clear the existing details from LOGGER object
		inLogger.getFIELD().clear(); //It makes empty in the list object
		//Set the new set of data
		inLogger.setFIELD(outFieldList);
		StringWriter writer = new StringWriter();
		JAXBContext jaxbContext = JAXBContext.newInstance(LOGGER.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    jaxbMarshaller.marshal(inLogger, writer);
	    log.log(Level.INFO, "OutPutService :: outputConversion() :: Ends");
		return writer.toString();
	}
	
	
	public static List<String> removedTags(){
		List<String> removedTags = new ArrayList<String>();
		removedTags.add("cd_file");
		removedTags.add("id_err_typ");
		removedTags.add("cd_svrty_lvl");
		removedTags.add("log_narrative");
		removedTags.add("log_severity");
		removedTags.add("tx_error");
		removedTags.add("user_ref");
		removedTags.add("user_id");
		return removedTags;
	}
}
