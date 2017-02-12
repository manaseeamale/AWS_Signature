package com.example.aws.s3.signature.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Manasee Amale
 * @Since 1.0 This is for take value from configuration file.In which we can
 *        store access,secret key of AWS account.
 */
@Component
@PropertySource("classpath:aws.properties")
@ConfigurationProperties(prefix = "com.example.aws.s3.signature")
public class AWSConfiguration {

	public static final String S3_ACCESS_KEY = "${com.aws.s3.access.key}";
	public static final String S3_SECRET_KEY = "${com.aws.s3.secret.key}";
	public static final String S3_BUCKET_NAME = "${com.aws.s3.bucket}";
	public static final String BUCKET_FOLDER_NAME = "${com.aws.folder.name}";

	@Value(S3_ACCESS_KEY)
	private String s3AccessKey;

	@Value(S3_SECRET_KEY)
	private String s3SecretKey;

	@Value(S3_BUCKET_NAME)
	private String s3BucketName;

	@Value(BUCKET_FOLDER_NAME)
	private String bucketFolderName;

	/**
	 * Returns S3 Access Key
	 * 
	 * @return S3 Access Key
	 */
	public String getAmazonS3AccessKey() {
		return s3AccessKey;
	}

	/**
	 * Returns S3 Secret Key
	 * 
	 * @return S3 Secret Key
	 */
	public String getAmazonS3SecretKey() {
		return s3SecretKey;
	}

	/**
	 * Returns S3  bucket name
	 * 
	 * @return S3 bucket name
	 */
	public String getAmazonS3Bucket() {
		return s3BucketName;

	}

	/**
	 * Returns current environment host name.
	 * 
	 * @return host name
	 */
	public String getCurrentFolderName() {
		return bucketFolderName;
	}

}
