package com.mufg.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.mufg.connector.AmazonS3Connector;
import com.mufg.io.BatchDataSourceModel;
import com.mufg.io.BatchRequest;
import com.mufg.io.BatchResponse;
import com.mufg.io.StatusMessage;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class BatchService {
	
	@Autowired
	private BatchDataSourceModel model;
	
	@Autowired
	private AmazonS3Connector s3Connector;
	
	public BatchResponse processListDirectoryInBucket() {
		log.info("BatchService :: processListDirectoryInBucket() :: Init ");
		List<String> directories = s3Connector.listDirectory(model.getS3BucketName());
		log.info("BatchService :: processListDirectoryInBucket() :: Ends " + directories);
		return BatchResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","LIST_ALL_DIRECTORIES_SUCCESS")).
				data(directories).
				build();
	}

	public BatchResponse processCreateDirectoryInBucket(BatchRequest request) {
		log.info("BatchService :: processCreateDirectoryInBucket() :: Init ");
		s3Connector.createDirectory(model.getS3BucketName(),request.getFolderName());
		log.info("BatchService :: processListDirectoryInBucket() :: Ends ");
		return BatchResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","DIRECTORY_CREATED_SUCCESS")).
				build();
	}
	
	public BatchResponse processDeleteDirectoryInBucket(BatchRequest request) {
		s3Connector.deleteDirectory(model.getS3BucketName(),request.getFolderName());
		return BatchResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","DIRECTORIES_DELETED_SUCCESS")).
				build();
	}
	
	public BatchResponse processAllDeleteDirectoryInBucket() {
		s3Connector.deleteAllDirectory(model.getS3BucketName());
		return BatchResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","DIRECTORIES_DELETED_SUCCESS")).
				build();
	}
	
	public BatchResponse processUploadFileTos3bucket(BatchRequest request) throws FileNotFoundException, IOException {
		log.info("BatchService :: processUploadFileTos3bucket() :: Init ");
		String referenceId = UUID.randomUUID().toString().substring(0, 8);
		//Store file in AWS
		byte[] imageByte = Base64Utils.decodeFromString(request.getPassportNumber());
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		String key =  "MUFG_"+referenceId+"_PASSPORT.txt";
		File outputfile = new File(key);
		IOUtils.copy(bis, new FileOutputStream(outputfile));
		bis.close();
		s3Connector.processUploadFileTos3bucket(model.getS3BucketName(),request.getFolderName(),outputfile.getName(),outputfile);
		if(outputfile.exists()) {
			outputfile.delete();
		}
		String url = s3Connector.getUrl(model.getS3BucketName(),key);
		log.info("BatchService :: processSaveData() :: Ends ");
		return BatchResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","REGISTRATION_DEATILS_SAVED_SUCCESS")).
				data(url).
				build();
	   
	}
	
	
}


