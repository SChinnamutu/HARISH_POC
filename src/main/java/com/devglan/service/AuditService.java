package com.devglan.service;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devglan.io.BaseResponse;
import com.devglan.model.AuditLog;
import com.devglan.repository.AuditLogRepo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuditService {

	@Autowired
	AuditLogRepo auditLogRepo;
	
	public BaseResponse processEnrich(String payload) throws JsonParseException, JsonMappingException, IOException {
		log.info("AuditService :: processEnrich() :: Init");
		AuditLog auditLog ;
		String referenceId = UUID.randomUUID().toString();
		auditLog =  AuditLog.builder()
				.txnCreatedDate(new Date())
				.txnReferenceId(referenceId)
				.txnPayloadType("INBOUND")
				.txnOwner("CITI")
				.txnPayload(payload)
				.build();
		auditLogRepo.save(auditLog);
		String conversionResponse = null;
		if(payload.contains("CITI")) {
			conversionResponse = jaxbXMLToObject(payload);
		}
		if(payload.contains("HDFC")) {
			conversionResponse = jaxbObjectToXML(payload);
		}
		auditLog =  AuditLog.builder()
				.txnCreatedDate(new Date())
				.txnReferenceId(referenceId)
				.txnPayloadType("OUTBOUND")
				.txnOwner("CITI")
				.txnPayload(conversionResponse)
				.build();
		auditLogRepo.save(auditLog);
		log.info("AuditService :: processEnrich() :: Ends");
		return BaseResponse.builder()
				//.status("SUCCESS")
				//.statusMessage(new StatusMessage("200", "Encrichment success"))
				.data(conversionResponse)
				.referenceId(referenceId)
				.build();
				
	}
	
	//Method for CITI
	private static String jaxbXMLToObject(String payload) {
		JSONObject obj = XML.toJSONObject(payload);
		return obj.toString();
	}

	
	//Method for HDFC
	private static String jaxbObjectToXML(String payload) {
		JSONObject json = new JSONObject(payload);
		return XML.toString(json);
	}
	
}
