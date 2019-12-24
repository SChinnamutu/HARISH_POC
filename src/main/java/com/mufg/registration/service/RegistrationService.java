package com.mufg.registration.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.mufg.registration.connector.AmazonS3Connector;
import com.mufg.registration.entity.RegistrationEntity;
import com.mufg.registration.io.RegistrationRequest;
import com.mufg.registration.io.RegistrationResponse;
import com.mufg.registration.io.StatusMessage;
import com.mufg.registration.repository.RegistrationRepository;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class RegistrationService {
	
	
	private final String S3_BUCKET_NAME = "mufg-registration-service";
	
	@Autowired
	private RegistrationRepository repository;

	@Autowired
	private AmazonS3Connector s3Connector;
	
	
	public RegistrationResponse processSaveData(RegistrationRequest request) throws IOException {
		log.info("RegistrationService :: processSaveData() :: Init ");
		String referenceId = "MUFG".concat(UUID.randomUUID().toString().substring(0, 8));
		//Store file in AWS
		byte[] imageByte = Base64Utils.decodeFromString(request.getPassportNumber());
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		String key =  referenceId+"_"+"MUFG_PASSPORT_BACK.jpg";
		File outputfile = new File(key);
		IOUtils.copy(bis, new FileOutputStream(outputfile));
		bis.close();
		s3Connector.putObject(S3_BUCKET_NAME,key,outputfile);
		if(outputfile.exists()) {
			outputfile.delete();
		}
		String url = s3Connector.getUrl(S3_BUCKET_NAME,key);
		//Store file in DB
		RegistrationEntity entity = new RegistrationEntity();
		entity.setReferenceId(referenceId);
		BeanUtils.copyProperties(request, entity);
		entity.setPassportFront(url);
		entity.setCreateAt(new Date());
		repository.save(entity);
		log.info("RegistrationService :: processSaveData() :: Ends ");
		return RegistrationResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","REGISTRATION_DEATILS_SAVED_SUCCESS")).
				referenceId(entity.getReferenceId()).
				build();
	}

	
	public RegistrationResponse processListBuckets() {
		 List<String>  buckets =  s3Connector.listBuckets();
		return RegistrationResponse.builder()
				.data(buckets)
				.build();
	}

	public RegistrationResponse processDeleteFileInBucket(RegistrationRequest request) {
		s3Connector.deleteObject(S3_BUCKET_NAME,request.getFileName());
		return RegistrationResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","REGISTRATION_DELETED_SUCCESS")).
				referenceId(request.getReferenceId()).
				build();
	}

	public RegistrationResponse processListFilesInBucket() {
		List<String> files = s3Connector.listObject(S3_BUCKET_NAME);
		return RegistrationResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","REGISTRATION_DELETED_SUCCESS")).
				data(files).
				build();
	}
	

	public RegistrationResponse processDeleteDirectoryInBucket(RegistrationRequest request) {
		s3Connector.deleteDirectory(request.getBucketName(), request.getPrefix());
		return RegistrationResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","BUCKET_CREATED_SUCCESS")).
				referenceId(request.getReferenceId()).
				build();
	}

	public RegistrationResponse processCreateNewBucket(RegistrationRequest request) {
		s3Connector.createBucket(request.getBucketName());
		return RegistrationResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","BUCKET_CREATED_SUCCESS")).
				referenceId(request.getReferenceId()).
				build();
	}

	public RegistrationResponse processDeleteAllFilesInBucket(RegistrationRequest request) {
		s3Connector.deleteFiles(S3_BUCKET_NAME);
		return RegistrationResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","BUCKET_ALL_FILES_DELETED_SUCCESS")).
				referenceId(request.getReferenceId()).
				build();
	}

	public RegistrationResponse processListDirectoryInBucket() {
		List<String> directories = s3Connector.listDirectory(S3_BUCKET_NAME);
		return RegistrationResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","LIST_ALL_DIRECTORIES_SUCCESS")).
				data(directories).
				build();
	}

	public RegistrationResponse processCreateDirectoryInBucket() {
		s3Connector.createDirectory(S3_BUCKET_NAME);
		return RegistrationResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","DIRECTORY_CREATED_SUCCESS")).
				build();
	}
	

	public RegistrationResponse createFileInDirectory(RegistrationRequest request) throws FileNotFoundException, IOException {
		String referenceId = UUID.randomUUID().toString().substring(0,8);
		byte[] imageByte = Base64Utils.decodeFromString(request.getPassportNumber());
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		String key =  referenceId+"_"+"MUFG_PASSPORT_BACK.jpg";
		File outputfile = new File(key);
		IOUtils.copy(bis, new FileOutputStream(outputfile));
		bis.close();
		s3Connector.createFileInDirectory(S3_BUCKET_NAME,outputfile);
		return RegistrationResponse.builder().
				status("SUCCESS").
				statusMessage(new StatusMessage("SUCCESS_MSG","FILE_DIRECTORY_CREATED_SUCCESS")).
				build();
	}
	
}


