package com.devglan.service;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devglan.io.BaseResponse;
import com.devglan.io.MBRequest;
import com.devglan.model.AuditLog;
import com.devglan.repository.AuditLogRepo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;

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
	
		
		String conversionResponse = null;
		if(payload.contains("CITI")) {
			XmlMapper xmlMapper = new XmlMapper();
			MBRequest mbRequest = xmlMapper.readValue(payload, MBRequest.class);
			auditLog = new AuditLog();
			auditLog.setTxnCreatedDate(new Date());
			auditLog.setTxnReferenceId(referenceId);
			auditLog.setTxnPayload(payload);
			auditLog.setTxnPayloadType("INBOUND");
			auditLog.setTxnOwner(mbRequest.getClientId());
			auditLogRepo.save(auditLog);
			conversionResponse = jaxbXMLToObject(payload);
			auditLog = new AuditLog();
			auditLog.setTxnPayload(conversionResponse);
			auditLog.setTxnPayloadType("OUTBOUND");
			auditLogRepo.save(auditLog);
		}
		if(payload.contains("HDFC")) {
			MBRequest mbRequest = new Gson().fromJson(payload, MBRequest.class);
			auditLog = new AuditLog();
			auditLog.setTxnCreatedDate(new Date());
			auditLog.setTxnReferenceId(referenceId);
			auditLog.setTxnPayload(payload);
			auditLog.setTxnPayloadType("INBOUND");
			auditLog.setTxnOwner(mbRequest.getClientId());
			auditLogRepo.save(auditLog);
			conversionResponse = jaxbObjectToXML(payload);
			auditLog = new AuditLog();
			auditLog.setTxnPayload(conversionResponse);
			auditLog.setTxnPayloadType("OUTBOUND");
			auditLogRepo.save(auditLog);
		}
		log.info("AuditService :: processEnrich() :: Ends");
		BaseResponse response = new BaseResponse();
		response.setData(conversionResponse);
		response.setReferenceId(referenceId);
		return response;
				
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
