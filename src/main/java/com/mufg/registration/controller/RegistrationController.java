package com.mufg.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mufg.registration.io.RegistrationRequest;
import com.mufg.registration.io.RegistrationResponse;
import com.mufg.registration.service.RegistrationService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(value = "RegistrationPayment Controller", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value= "/registration")
public class RegistrationController {

	@Autowired
	private RegistrationService regService;
	
	
	@GetMapping(value= "/buckets")
	public ResponseEntity<RegistrationResponse> processListBuckets() throws Exception {
		log.info("RegistrationController :: processListBuckets() :: Init ");
		RegistrationResponse response = regService.processListBuckets();
		log.info("RegistrationController :: processListBuckets() :: Ends "+ response.toString());
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value= "/bucket")
	public ResponseEntity<RegistrationResponse> processCreateNewBucket(@RequestBody RegistrationRequest request) throws Exception {
		log.info("RegistrationController :: processCreateNewBucket() :: Init ");
		RegistrationResponse response = regService.processCreateNewBucket(request);
		log.info("RegistrationController :: processCreateNewBucket() :: Ends "+ response.toString());
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value= "/file")
	public ResponseEntity<RegistrationResponse> processPutFileInBucket(@RequestBody RegistrationRequest request) throws Exception {
		log.info("RegistrationController :: processPutObjectInBucket() :: Init ");
		RegistrationResponse response = regService.processSaveData(request);
		log.info("RegistrationController :: processPutObjectInBucket() :: Ends "+ response.toString());
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value= "/file")
	public ResponseEntity<RegistrationResponse> processDeleteFileInBucket(@RequestBody RegistrationRequest request) throws Exception {
		log.info("RegistrationController :: processNewRegisteration() :: Init ");
		RegistrationResponse response = regService.processDeleteFileInBucket(request);
		log.info("RegistrationController :: processNewRegisteration() :: Ends "+ response.toString());
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value= "/files")
	public ResponseEntity<RegistrationResponse> processListFilesInBucket() throws Exception {
		log.info("RegistrationController :: processListFilesInBucket() :: Init ");
		RegistrationResponse response = regService.processListFilesInBucket();
		log.info("RegistrationController :: processListFilesInBucket() :: Ends "+ response.toString());
		return ResponseEntity.ok(response);
	}
	
	/*
	 * @DeleteMapping(value= "/files") public ResponseEntity<RegistrationResponse>
	 * processDeleteAllFilesInBucket(@RequestBody RegistrationRequest request)
	 * throws Exception {
	 * log.info("RegistrationController :: processDeleteAllFilesInBucket() :: Init "
	 * ); RegistrationResponse response =
	 * regService.processDeleteAllFilesInBucket(request);
	 * log.info("RegistrationController :: processDeleteAllFilesInBucket() :: Ends "
	 * + response.toString()); return ResponseEntity.ok(response); }
	 */
	
	
	@GetMapping(value= "/directory")
	public ResponseEntity<RegistrationResponse> processListDirectoryInBucket() throws Exception {
		log.info("RegistrationController :: processListDirectoryInBucket() :: Init ");
		RegistrationResponse response = regService.processListDirectoryInBucket();
		log.info("RegistrationController :: processListDirectoryInBucket() :: Ends "+ response.toString());
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value= "/directory")
	public ResponseEntity<RegistrationResponse> processCreateDirectoryInBucket() throws Exception {
		log.info("RegistrationController :: processDeleteDirectoryInBucket() :: Init ");
		RegistrationResponse response = regService.processCreateDirectoryInBucket();
		log.info("RegistrationController :: processDeleteDirectoryInBucket() :: Ends "+ response.toString());
		return ResponseEntity.ok(response);
	}
	
	
	
	/*
	 * @PostMapping(value = "/file-directory") public
	 * ResponseEntity<RegistrationResponse> processCreateFileDirectoryInBucket(
	 * 
	 * @RequestBody RegistrationRequest request) throws Exception { log.
	 * info("RegistrationController :: processCreateFileDirectoryInBucket() :: Init "
	 * ); RegistrationResponse response = regService.createFileInDirectory(request);
	 * log.
	 * info("RegistrationController :: processCreateFileDirectoryInBucket() :: Ends "
	 * + response.toString()); return ResponseEntity.ok(response); }
	 */
	/*
	 * @DeleteMapping(value= "/directory") public
	 * ResponseEntity<RegistrationResponse>
	 * processDeleteDirectoryInBucket(@RequestBody RegistrationRequest request)
	 * throws Exception { log.
	 * info("RegistrationController :: processDeleteDirectoryInBucket() :: Init ");
	 * RegistrationResponse response =
	 * regService.processDeleteDirectoryInBucket(request); log.
	 * info("RegistrationController :: processDeleteDirectoryInBucket() :: Ends "+
	 * response.toString()); return ResponseEntity.ok(response); }
	 */
	
}
