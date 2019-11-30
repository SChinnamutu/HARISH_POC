package com.devglan.service;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.devglan.io.MBRequest;
import com.devglan.io.MBResponse;
import com.devglan.utils.ConversionUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class MBConversionService {

	
	private Logger log = Logger.getLogger(MBConversionService.class.getSimpleName()); 
	
	public MBResponse processConvert(MBRequest mbRequest) throws JsonParseException, JsonMappingException, IOException {
		log.info("MBConversionService :: processConvert() :: Init");
		String referenceId = UUID.randomUUID().toString();
		String conversionResponse = null;
		if(mbRequest.getClientId().equalsIgnoreCase("CITI")) {
			conversionResponse = ConversionUtils.toString(mbRequest);
		}else if(mbRequest.getClientId().equalsIgnoreCase("HDFC")) {
			String payload = ConversionUtils.toString(mbRequest);
			conversionResponse = ConversionUtils.jaxbObjectToXML(payload);
		}
		log.info("MBConversionService :: processConvert() :: Ends");
		MBResponse response = new MBResponse();
		response.setData(conversionResponse);
		response.setReferenceId(referenceId);
		return response;
				
	}
	
	
	
}
