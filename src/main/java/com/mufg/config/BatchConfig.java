package com.mufg.config;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.mufg.io.BatchDataSourceModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration(value="BatchConfig")
@Primary
public class BatchConfig  implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Value("${S3_ACCESS_KEY}")
	private String S3AccessKey;
	
	@Value("${S3_SECRET_KEY}")
	private String S3SecretKey;
	
	@Value("${S3_REGION}")
	private String S3Region;
	
	@Value("${S3_BUCKET_NAME}")
	private String S3BucketName;
	
	/**
	 * This method used to create object for AmazonS3.
	 * @author Sasikumar Chinnamuthu
	 * @return ModelMapper
	 */
	@Bean
	public AmazonS3 createS3Client() {
		BatchDataSourceModel model = new BatchDataSourceModel();
		model.setS3AccessKey(S3AccessKey);
		model.setS3SecretKey(S3SecretKey);
		model.setS3BucketName(S3BucketName);
		model.setS3Region(S3Region);
		AWSCredentials credentials = new BasicAWSCredentials(model.getS3AccessKey(),model.getS3SecretKey());
		AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
		return AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider)
				.withRegion(model.getS3Region()).build();
	}

	
	/**
	 * This method used to create object for AmazonS3.
	 * @author Sasikumar Chinnamuthu
	 * @return ModelMapper
	 */
	@Bean
	public BatchDataSourceModel model() {
		BatchDataSourceModel model = new BatchDataSourceModel();
		model.setS3AccessKey(S3AccessKey);
		model.setS3SecretKey(S3SecretKey);
		model.setS3BucketName(S3BucketName);
		model.setS3Region(S3Region);
		return model;
	}

}
