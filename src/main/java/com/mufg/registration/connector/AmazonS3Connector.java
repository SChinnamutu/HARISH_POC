package com.mufg.registration.connector;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AmazonS3Connector {

	@Autowired
	private AmazonS3 s3Client;
	
	public void deleteObject(String bucketName,String key) {
		log.info("Delete object from Amazon S3");
		s3Client.deleteObject(bucketName, key);
	}

	public void putObject(String bucketName,String key, File destFile) {
		log.info("Put object in Amazon S3");
		s3Client.putObject(bucketName, key, destFile);
	}
	
	public void createBucket(String bucketName) {
		log.info("Create bucket in Amazon S3");
		if(!s3Client.doesBucketExistV2(bucketName)) {
			s3Client.createBucket(bucketName);
		}
	}

	public List<String> listObject(String bucketName) {
		log.info("listObject from Amazon S3");
		ObjectListing objects=s3Client.listObjects(bucketName);
        List<String> objectNames=new ArrayList<String>(objects.getObjectSummaries().size());
        Iterator<S3ObjectSummary> oIter=objects.getObjectSummaries().iterator();
        while (oIter.hasNext()) {
            objectNames.add(oIter.next().getKey());
        }
        while (objects.isTruncated()) {
            objects=s3Client.listNextBatchOfObjects(objects);
            oIter=objects.getObjectSummaries().iterator();
            while (oIter.hasNext()) {
                objectNames.add(oIter.next().getKey());
            }
        }
        return objectNames;
	}
	
	public String getUrl(String bucketName,String key) {
		URL url = s3Client.getUrl(bucketName,key);
		return url.toString();
	}
	
	public List<String> listBuckets() {
		List<String> buckets = s3Client.listBuckets().stream().map( bucket -> bucket.getName()).collect(Collectors.toList());
		return buckets;
	}

	public void deleteFiles(String bucketName) {
		 for (S3ObjectSummary file : s3Client.listObjects(bucketName).getObjectSummaries()){
			 s3Client.deleteObject(bucketName, file.getKey());
		 }
	}
	
	public void createDirectory(String bucketName) {
		String folderName = "MUFG/";
		// create meta-data for your folder and set content-length to 0
	    ObjectMetadata metadata = new ObjectMetadata();
	    metadata.setContentLength(0);
	    // create empty content
	    InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
	    // create a PutObjectRequest passing the folder name suffixed by /
	    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,folderName , emptyContent, metadata);
	    // send request to S3 to create folder
	    s3Client.putObject(putObjectRequest);
		
	}

	
	public void createFileInDirectory(String bucketName,File file) {
		String folderName = "MUFG/";
		// create meta-data for your folder and set content-length to 0
	    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,folderName ,file);
	    // send request to S3 to create folder
	    s3Client.putObject(putObjectRequest);
		
	}


	public void deleteDirectory(String bucketName,String prefix) {
	    ObjectListing objectList = s3Client.listObjects(bucketName, prefix );
	    List<S3ObjectSummary> objectSummeryList =  objectList.getObjectSummaries();
	    String[] keysList = new String[ objectSummeryList.size() ];
	    int count = 0;
	    for( S3ObjectSummary summery : objectSummeryList ) {
	        keysList[count++] = summery.getKey();
	    }
	    DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest( bucketName ).withKeys( keysList );
	    this.s3Client.deleteObjects(deleteObjectsRequest);
	}

	public List<String> listDirectory(String bucketName) {
		String folderKey = "MUFG";
	    ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName).withPrefix(folderKey + "/");
	    List<String> keys = new ArrayList<String>();
	    ObjectListing objects = s3Client.listObjects(listObjectsRequest);
	    for (;;) {
	        List<S3ObjectSummary> summaries = objects.getObjectSummaries();
	        if (summaries.size() < 1) {
	            break;
	        }
	        summaries.forEach(s -> keys.add(s.getKey()));
	        objects = s3Client.listNextBatchOfObjects(objects);
	    }
	    return keys;
	}


}
