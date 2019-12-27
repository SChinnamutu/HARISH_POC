package com.mufg.connector;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;


@Service
public class AmazonS3Connector {

	@Autowired
	private AmazonS3 s3Client;
	
	public List<String> listBuckets() {
		List<String> buckets = s3Client.listBuckets().stream().map( bucket -> bucket.getName()).collect(Collectors.toList());
		return buckets;
	}
	
	public List<String> listDirectory(String bucketName) {
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName);
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
	
	public void createDirectory(String bucketName,String folderName) {
		ObjectMetadata metadata = new ObjectMetadata();
	    metadata.setContentLength(0);
	    InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
	    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,folderName+ "/", emptyContent, metadata);
	    s3Client.putObject(putObjectRequest);
	}
	
	public void deleteAllDirectory(String bucketName) {
	    ObjectListing objectList = s3Client.listObjects(bucketName);
	    List<S3ObjectSummary> objectSummeryList =  objectList.getObjectSummaries();
	    String[] keysList = new String[ objectSummeryList.size() ];
	    int count = 0;
	    for( S3ObjectSummary summery : objectSummeryList ) {
	    	if(!StringUtils.isEmpty(summery.getKey())) {
	    		keysList[count++] = summery.getKey();	
	    	}
	    }
	    DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest( bucketName ).withKeys( keysList );
	    this.s3Client.deleteObjects(deleteObjectsRequest);
	}
	
	public void deleteDirectory(String bucketName,String folderName) {
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName).withPrefix(folderName + "/");
		ObjectListing objectListing = s3Client.listObjects(listObjectsRequest);
	    List<S3ObjectSummary> objectSummeryList =  objectListing.getObjectSummaries();
	    String[] keysList = new String[ objectSummeryList.size() ];
	    int count = 0;
	    for( S3ObjectSummary summery : objectSummeryList ) {
	    	if(!StringUtils.isEmpty(summery.getKey())) {
	    		keysList[count++] = summery.getKey();	
	    	}
	    }
	    DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest( bucketName ).withKeys( keysList );
	    this.s3Client.deleteObjects(deleteObjectsRequest);
	}
	
	public void processUploadFileTos3bucket(String bucketName,String folderName ,String fileName, File file) {
		s3Client.putObject(new PutObjectRequest(bucketName, folderName.concat("/"+fileName), file)
	            .withCannedAcl(CannedAccessControlList.PublicRead));
	}
	
	

	public void deleteFileInDirectory(String bucketName,String folderName, String fileName) {
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName).withPrefix(folderName + "/");
		ObjectListing objectListing = s3Client.listObjects(listObjectsRequest);
	    List<S3ObjectSummary> objectSummeryList =  objectListing.getObjectSummaries();
	    String[] keysList = new String[ objectSummeryList.size() ];
	    int count = 0;
	    for( S3ObjectSummary summery : objectSummeryList ) {
	    	if(!StringUtils.isEmpty(summery.getKey())) {
	    		keysList[count++] = summery.getKey();	
	    	}
	    }
	    DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest( bucketName ).withKeys( keysList );
	    this.s3Client.deleteObjects(deleteObjectsRequest);
	}
	
	public String getUrl(String bucketName,String key) {
		URL url = s3Client.getUrl(bucketName,key);
		return url.toString();
	}
	
}


