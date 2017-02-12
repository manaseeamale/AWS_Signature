package com.example.aws.s3.signature.util;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;


/**
 * @author Manasee Amale
 * @Since 1.0
 */
public class AWSConstants {


    public static final String S3_POLICY_SIGNATURE = "/constructPolicy";
    public static final String SEPARATOR = "/";
    public static final String SERVICE = "s3";
    public static final String ALGORITHM = "AWS4-HMAC-SHA256";
    public static final String REGION_NAME = Region.getRegion(Regions.US_WEST_2).toString();
    public static final String AWS_REQUEST = "aws4_request";
    public static final String DATE_STAMP = "YYYYMMDD'T'HHMMSS'Z'";
    public static final String YYYY_MM_DD = "yyyyMMdd";
    public static final String HTTPS = "https://";
    public static final String AWS_DOMAIN = ".s3.amazonaws.com/";
    public static final String HMAC_SHA_256 = "HmacSHA256";;
    public static final int DAY = 1;
    public static final String HMAC_SHA_1 = "HmacSHA1";

    /**
     * s3 policy for AWS version4
     */
    public static final String S3_POLICY_FOR_V4 = "'{'\"expiration\":\"{0}\",\"conditions\":['{'\"bucket\":\"{1}\"'}',"
            + "[\"starts-with\",\"$key\",\"{2}\"],[\"eq\",\"$success_action_status\",\"201\"],"
            + "'{'\"x-amz-credential\":\"{3}\"'}' ,'{'\"x-amz-algorithm\":\"{4}\"'}','{'\"x-amz-date\":\"{5}\"'}','{'\"acl\":\"private\"'}','{'\"x-amz-server-side-encryption\": \"AES256\"'}','{'\"x-amz-expires\":\"{6}\"'}]'}'";

    /**
     *  s3 policy for AWS version2
     */
	public static final String S3_POLICY_FOR_V2 = "'{'\"expiration\":\"{0}\",\"conditions\":['{'\"bucket\":\"{1}\"'}',"
			+ "[\"starts-with\",\"$key\",\"{2}\"],[\"eq\",\"$success_action_status\",\"201\"]]'}'";
}
