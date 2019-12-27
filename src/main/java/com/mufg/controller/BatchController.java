package com.mufg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mufg.io.BatchRequest;
import com.mufg.io.BatchResponse;
import com.mufg.service.BatchService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Slf4j
@Api(value = "Batch Controller", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value= "/batch")
public class BatchController {

	@Autowired
	private BatchService regService;
	
	
	/*
	 * This method is used to list all buckets.
	*/
	@GetMapping(value= "/buckets")
	public ResponseEntity<BatchResponse> processListBuckets() throws Exception {
		log.info("RegistrationController :: processListBuckets() :: Init ");
		BatchResponse response = regService.processListBuckets();
		log.info("RegistrationController :: processListBuckets() :: Ends "+ response.toString());
		return ResponseEntity.ok(response);
	}
	
	/*
	 * This method is used to list all directories in a bucket with argument of bucketName.
	*/
	@GetMapping(value= "/directory")
	public ResponseEntity<BatchResponse> processListDirectoryInBucket() throws Exception {
		log.info("BatchController :: processListDirectoryInBucket() :: Init ");
		BatchResponse response = regService.processListDirectoryInBucket();
		log.info("BatchController :: processListDirectoryInBucket() :: Ends "+ response.toString());
		return ResponseEntity.ok(response);
	}
	
	/*
	 * This method is used to create a new directory in a bucket with argument of bucketName and directory name.
	*/
	@PostMapping(value= "/directory")
	public ResponseEntity<BatchResponse> processCreateDirectoryInBucket(@RequestBody BatchRequest request) throws Exception {
		log.info("BatchController :: processDeleteDirectoryInBucket() :: Init " + request);
		BatchResponse response = regService.processCreateDirectoryInBucket(request);
		log.info("BatchController :: processDeleteDirectoryInBucket() :: Ends "+ response.toString());
		return ResponseEntity.ok(response);
	}
	
	/*
	 * This method is used to delete a directory in a bucket with argument of bucketName and directory name.
	*/
	@DeleteMapping(value = "/directory")
	public ResponseEntity<BatchResponse> processDeleteDirectoryInBucket(@RequestBody BatchRequest request)
			throws Exception {
		log.info("BatchController :: processDeleteDirectoryInBucket() :: Init ");
		BatchResponse response = regService.processDeleteDirectoryInBucket(request);
		log.info("BatchController :: processDeleteDirectoryInBucket() :: Ends " + response.toString());
		return ResponseEntity.ok(response);
	} 
	
	/*
	 * This method is used to delete a directory in a bucket with argument of bucketName and directory name.
	*/
	@ApiIgnore
	@DeleteMapping(value = "/directories")
	public ResponseEntity<BatchResponse> processAllDeleteDirectoryInBucket()
			throws Exception {
		log.info("BatchController :: processAllDeleteDirectoryInBucket() :: Init ");
		BatchResponse response = regService.processAllDeleteDirectoryInBucket();
		log.info("BatchController :: processAllDeleteDirectoryInBucket() :: Ends " + response.toString());
		return ResponseEntity.ok(response);
	} 
	
	
	/*
	 * This method is used to create a new directory in a bucket with argument of bucketName and directory name.
	*/
	@PostMapping(value= "uploadFile")
	public ResponseEntity<BatchResponse> processUploadFileTos3bucket(@RequestBody BatchRequest request) throws Exception {
		log.info("BatchController :: processUploadFileTos3bucket() :: Init ");
		BatchResponse response = regService.processUploadFileTos3bucket(request);
		log.info("BatchController :: processUploadFileTos3bucket() :: Ends "+ response.toString());
		return ResponseEntity.ok(response);
	}
	
	/*
	 * This method is used to delete a directory in a bucket with argument of bucketName and directory name.
	*/
	@DeleteMapping(value = "/file")
	public ResponseEntity<BatchResponse> processDeleteFileInDirectory(@RequestBody BatchRequest request)
			throws Exception {
		log.info("BatchController :: processDeleteFileInDirectory() :: Init ");
		BatchResponse response = regService.processDeleteFileInDirectory(request);
		log.info("BatchController :: processDeleteFileInDirectory() :: Ends " + response.toString());
		return ResponseEntity.ok(response);
	}
}
