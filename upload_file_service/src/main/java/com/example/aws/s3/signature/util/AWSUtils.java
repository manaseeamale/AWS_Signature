package com.example.aws.s3.signature.util;

import static com.example.aws.s3.signature.util.AWSConstants.ALGORITHM;
import static com.example.aws.s3.signature.util.AWSConstants.AWS_REQUEST;
import static com.example.aws.s3.signature.util.AWSConstants.DAY;
import static com.example.aws.s3.signature.util.AWSConstants.HMAC_SHA_1;
import static com.example.aws.s3.signature.util.AWSConstants.HMAC_SHA_256;
import static com.example.aws.s3.signature.util.AWSConstants.REGION_NAME;
import static com.example.aws.s3.signature.util.AWSConstants.S3_POLICY_FOR_V2;
import static com.example.aws.s3.signature.util.AWSConstants.SEPARATOR;
import static com.example.aws.s3.signature.util.AWSConstants.SERVICE;
import static com.example.aws.s3.signature.util.AWSConstants.YYYY_MM_DD;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * @author Manasee Amale
 * @Since 1.0
 */
public class AWSUtils {

	/**
	 * Generates a key for file being uploaded.
	 * 
	 * @param fileName
	 * @return Object key
	 */
	public static String generateObjectKeyForFile(String fileName, String folder) {
		return new StringBuilder(folder).append(SEPARATOR).append(fileName)
				.toString();
	}

	/**
	 * Generates a Date after adding given number of days.
	 * 
	 * @param nDays
	 * @return
	 */
	public static Calendar getDateAfterDays(int nDays) {
		Calendar aDayNDaysLater = Calendar.getInstance();
		aDayNDaysLater.add(Calendar.DATE, nDays);
		return aDayNDaysLater;
	}

	/**
	 * Get date Format
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static String getDate(String dateFormat) {
		Date myDate = new Date();
		String dateStamp = new SimpleDateFormat(dateFormat).format(myDate);
		return dateStamp;
	}

	/**
	 * Generate policy for AWS signature version2.
	 * 
	 * @param policyExpiresAt
	 * @param bucketName
	 * @param objectKey
	 * @param fileSize
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String generatePolicyForVersion2(Long policyExpiresAt,
			String bucketName, String objectKey)
			throws UnsupportedEncodingException {
		String expiration = new DateTime(policyExpiresAt, DateTimeZone.UTC)
				.toString();
		String policy = null;

		policy = MessageFormat.format(S3_POLICY_FOR_V2, expiration, bucketName,
				objectKey);
		return Base64.encodeBase64String(policy
				.getBytes(StandardCharsets.UTF_8));

	}

	/**
	 * Calculate signatureVersion2
	 * 
	 * @param policy
	 * @param secretKey
	 * @return
	 * @throws IllegalStateException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public static String calculateSignatureV2(String policy, String secretKey)
			throws IllegalStateException, UnsupportedEncodingException,
			NoSuchAlgorithmException, InvalidKeyException {
		Mac hmac = Mac.getInstance(HMAC_SHA_1);
		hmac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
				HMAC_SHA_1));
		return Base64
				.encodeBase64String(
						hmac.doFinal(policy.getBytes(StandardCharsets.UTF_8)))
				.replaceAll("\n", "").replaceAll("\r", "");

	}

	/**
	 * Generate policy for AWS signature version4.
	 * 
	 * @param policyExpiresAt
	 * @param bucketName
	 * @param objectKey
	 * @param credential
	 * @param secretKey
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String generatePolicyForVersion4(long policyExpiresAt,
			String bucketName, String objectKey, String dateStamp,
			String credential) throws UnsupportedEncodingException {
		String expiration = new DateTime(policyExpiresAt, DateTimeZone.UTC)
				.toString();
		String expireSeconds = String.valueOf(TimeUnit.DAYS.toSeconds(DAY));

		String policy = MessageFormat.format(AWSConstants.S3_POLICY_FOR_V4,
				expiration, bucketName, objectKey, credential, ALGORITHM,
				dateStamp, expireSeconds);

		return Base64.encodeBase64String(policy
				.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Calculate signatureVersion4
	 * 
	 * @param policy
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalStateException
	 * @throws InvalidKeyException
	 * @throws Exception
	 */
	public static String calculateSignatureV4(String policy, String secretKey)
			throws InvalidKeyException, IllegalStateException,
			UnsupportedEncodingException, NoSuchAlgorithmException {
		String dateStamp = getDate(YYYY_MM_DD);
		String signature = getV4SignatureKey(secretKey, dateStamp, REGION_NAME,
				policy);
		return signature;

	}

	/**
	 * Calculate signature version4
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws IllegalStateException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */

	private static String getV4SignatureKey(String key, String date,
			String region, String policy) throws InvalidKeyException,
			IllegalStateException, UnsupportedEncodingException,
			NoSuchAlgorithmException {
		byte[] kSecret = ("AWS4" + key).getBytes("UTF8");
		byte[] kDate = sha256Encode(date, kSecret);
		byte[] kRegion = sha256Encode(region, kDate);
		byte[] kService = sha256Encode(SERVICE, kRegion);
		byte[] kSigning = sha256Encode(AWS_REQUEST, kService);
		byte[] kSignature = sha256Encode(policy, kSigning);

		return Hex.encodeHexString(kSignature);
	}

	/**
	 * HMACSHA256 algorithm.
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws IllegalStateException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
	private static byte[] sha256Encode(String data, byte[] key)
			throws IllegalStateException, UnsupportedEncodingException,
			InvalidKeyException, NoSuchAlgorithmException {
		Mac mac = Mac.getInstance(HMAC_SHA_256);
		mac.init(new SecretKeySpec(key, HMAC_SHA_256));
		return mac.doFinal(data.getBytes("UTF8"));
	}
}
