package com.devglan.controller;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devglan.io.MBRequest;
import com.devglan.io.MBResponse;
import com.devglan.service.MBConversionService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping(value = "/api",consumes = {"application/json","application/xml"},produces  = {"application/json","application/xml"})
public class MBConversionController {

	private Logger log = Logger.getLogger(MBConversionController.class.getSimpleName());
	
	@Autowired
	private MBConversionService service; 
	
	@PostMapping(value = "/convert")
	public ResponseEntity<MBResponse> processConvert(@RequestBody MBRequest mbRequest) throws JsonParseException, JsonMappingException, IOException{
		log.info("MBConversionController :: processConvert() :: Init :: " + mbRequest);
		MBResponse mbResponse = service.processConvert(mbRequest);
		log.info("MBConversionController :: processConvert() :: Ends :: " + mbResponse);
		return ResponseEntity.ok(mbResponse);
	}
	
}
