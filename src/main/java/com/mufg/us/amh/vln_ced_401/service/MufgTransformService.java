package com.mufg.us.amh.vln_ced_401.service;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mufg.us.amh.vln_ced_401.handler.InputTransformHandler;
import com.mufg.us.amh.vln_ced_401.handler.OutputTransformHandler;
import com.mufg.us.amh.vln_ced_401.model.RequestData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MufgTransformService {

	@Autowired
	private InputTransformHandler inputHandler;
	
	@Autowired
	private OutputTransformHandler outputHandler;
	
	public String processTransformation(String payload,String processId) throws JAXBException  {
		log.info("MufgTransformService :: unmarshallingDocument() :: Init " + processId);
		RequestData unMarshalDocument = inputHandler.unmarshallingDocument(payload,processId);
		log.info("MufgTransformService :: unmarshallingDocument() :: Ends " + processId );
		log.info("MufgTransformService :: marshallingDocument() :: Init " + processId);
		String marshalDocument = outputHandler.marshallingDocument(unMarshalDocument,processId);
		log.info("MufgTransformService :: marshallingDocument() :: Ends " + processId);
		return marshalDocument;
	}
	
		
	
}
