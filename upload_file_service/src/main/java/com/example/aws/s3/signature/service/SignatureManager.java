package com.example.aws.s3.signature.service;

import com.example.aws.s3.signature.dto.PolicyRequestDto;
import com.example.aws.s3.signature.dto.PolicyResponseV2Dto;
import com.example.aws.s3.signature.dto.PolicyResponseV4Dto;


/**
 * @author Manasee Amale
 * @Since 1.0
 */
public interface SignatureManager {

	public PolicyResponseV2Dto createAWSSignatureVersion2(PolicyRequestDto fileDto) throws Exception;

	public PolicyResponseV4Dto createAWSSignatureVersion4(PolicyRequestDto fileDto) throws Exception;

}
