package com.mufg.io;

import lombok.Data;

@Data
public class BatchDataSourceModel {

	private String S3AccessKey;
	private String S3SecretKey;
	private String S3Region;
	private String S3BucketName;
	
}
