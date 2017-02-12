package com.example.aws.s3.signature.service;

import static com.example.aws.s3.signature.util.AWSConstants.AWS_DOMAIN;
import static com.example.aws.s3.signature.util.AWSConstants.AWS_REQUEST;
import static com.example.aws.s3.signature.util.AWSConstants.DATE_STAMP;
import static com.example.aws.s3.signature.util.AWSConstants.DAY;
import static com.example.aws.s3.signature.util.AWSConstants.HTTPS;
import static com.example.aws.s3.signature.util.AWSConstants.REGION_NAME;
import static com.example.aws.s3.signature.util.AWSConstants.SEPARATOR;
import static com.example.aws.s3.signature.util.AWSConstants.SERVICE;
import static com.example.aws.s3.signature.util.AWSConstants.YYYY_MM_DD;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.BadRequestException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aws.s3.signature.config.AWSConfiguration;
import com.example.aws.s3.signature.dto.PolicyRequestDto;
import com.example.aws.s3.signature.dto.PolicyResponseV2Dto;
import com.example.aws.s3.signature.dto.PolicyResponseV4Dto;
import com.example.aws.s3.signature.util.AWSUtils;



/**
 * @author Manasee Amale
 * @Since 1.0
 */
@Service
public class SignatureManagerImpl implements SignatureManager {

	private static final Logger log = LoggerFactory.getLogger(SignatureManagerImpl.class);
	@Autowired
	AWSConfiguration as;

	/**
	 * Create signature version4 for  file
	 *
	 * @throws Exception
	 */
	@Override
	public PolicyResponseV4Dto createAWSSignatureVersion4(PolicyRequestDto requestDto)
			throws  Exception {
		validateScriptFileDto(requestDto);

		PolicyResponseV4Dto fileDto = new PolicyResponseV4Dto();

		String bucketFolder = as.getCurrentFolderName();

		String dateStamp = AWSUtils.getDate(DATE_STAMP);
		String credential = new StringBuilder(as.getAmazonS3AccessKey()).append(SEPARATOR)
				.append(AWSUtils.getDate(YYYY_MM_DD)).append(SEPARATOR).append(REGION_NAME).append(SEPARATOR)
				.append(SERVICE).append(SEPARATOR).append(AWS_REQUEST).toString();

		fileDto.setDateStamp(dateStamp);
		fileDto.setAmzCredentials(credential);

		fileDto.setObjectKey(AWSUtils.generateObjectKeyForFile(requestDto.getFileName(), bucketFolder));
		fileDto.setExpireAfter(TimeUnit.DAYS.toSeconds(DAY));
		URL url = new URL(HTTPS + as.getAmazonS3Bucket() + AWS_DOMAIN);
		fileDto.setDestinatonUrl(url);
		this.generatePolicySignatureVer4(fileDto);
		return fileDto;
	}

	/**
	 * Validates {@link PolicyRequestDto}
	 * 
	 * @throws BadRequestException
	 */
	private void validateScriptFileDto(PolicyRequestDto fileDto) throws BadRequestException {

		if (StringUtils.isEmpty(generateObjectKey(fileDto))) {
			log.error("Bad fileName or fileSize");
			throw new BadRequestException();
		}

	}

	/**
	 * @param fileDto
	 * @return
	 */
	private String generateObjectKey(PolicyRequestDto fileDto) {
		return fileDto.getFileName();
	}

	/**
	 * Generate policy and signature version4
	 * 
	 * @param fileDto
	 * @throws Exception
	 */
	public void generatePolicySignatureVer4(PolicyResponseV4Dto fileDto) throws Exception {

		try {
			Calendar aDayLater = AWSUtils.getDateAfterDays(DAY);
			long expiresAt = aDayLater.getTimeInMillis();
			String policy = AWSUtils.generatePolicyForVersion4(expiresAt, as.getAmazonS3Bucket(),
					fileDto.getObjectKey(), fileDto.getDateStamp(), fileDto.getAmzCredentials());
			String signature = AWSUtils.calculateSignatureV4(policy, as.getAmazonS3SecretKey());
			fileDto.setPolicy(policy);
			fileDto.setSignature(signature);

		} catch (UnsupportedEncodingException | InvalidKeyException | IllegalStateException
				| NoSuchAlgorithmException e) {
			log.error("Failed to generate policy for object", e);
			throw new Exception();
		}

	}

	
	
	/**
	 * Calculate Signature version2 for file
	 * @throws Exception
	 */
	@Override
	public PolicyResponseV2Dto createAWSSignatureVersion2(PolicyRequestDto requestDto)
			throws Exception{
		validateScriptFileDto(requestDto);

		PolicyResponseV2Dto fileDto = new PolicyResponseV2Dto();

		String bucketFolder = as.getCurrentFolderName();
		fileDto.setObjectKey(AWSUtils.generateObjectKeyForFile(requestDto.getFileName(), bucketFolder));
		fileDto.setExpireAfter(TimeUnit.DAYS.toSeconds(DAY));
		URL url = new URL(HTTPS + as.getAmazonS3Bucket() + AWS_DOMAIN);
		fileDto.setDestinatonUrl(url);
		this.generatePolicySignatureVer2(fileDto);
		return fileDto;
	}

	
	/**
	 * Signature version2
	 */
	public void generatePolicySignatureVer2(PolicyResponseV2Dto fileDto) throws Exception {

		try {
			Calendar aDayLater = AWSUtils.getDateAfterDays(DAY);
			long expiresAt = aDayLater.getTimeInMillis();
			String policy = AWSUtils.generatePolicyForVersion2(expiresAt,
					as.getAmazonS3Bucket(), fileDto.getObjectKey());
			String signature = AWSUtils.calculateSignatureV2(policy,as.getAmazonS3SecretKey());
			fileDto.setPolicy(policy);
			fileDto.setSignature(signature);
		} catch (UnsupportedEncodingException | InvalidKeyException | IllegalStateException
				| NoSuchAlgorithmException e) {
			log.error("Failed to generate policy for object", e);
			throw new Exception();
		}

	}
}
