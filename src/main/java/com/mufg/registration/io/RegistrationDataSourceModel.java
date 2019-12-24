package com.mufg.registration.io;

import lombok.Data;

@Data
public class RegistrationDataSourceModel {

	private String S3AccessKey;
	private String S3SecretKey;
	private String S3Region;
	private String S3BucketName;
	
}
