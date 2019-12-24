package com.mufg.registration.config;

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
import com.mufg.registration.io.RegistrationDataSourceModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Configuration(value="RegistrationConfig")
@Primary
public class RegistrationConfig  implements Serializable  {/**
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
		RegistrationDataSourceModel model = new RegistrationDataSourceModel();
		model.setS3AccessKey(S3AccessKey);
		model.setS3SecretKey(S3SecretKey);
		model.setS3BucketName(S3BucketName);
		model.setS3Region(S3Region);
		log.info("RegistrationConfig :: createS3Client() :: ConfigurationDetails follows :: " + model.toString());
		AWSCredentials credentials = new BasicAWSCredentials(model.getS3AccessKey(),model.getS3SecretKey());
		AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
		return AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider)
				.withRegion(model.getS3Region()).build();
	}

}
