package com.example.aws.s3.signature.controller;

import static com.example.aws.s3.signature.util.AWSConstants.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.aws.s3.signature.dto.PolicyRequestDto;
import com.example.aws.s3.signature.dto.PolicyResponseV2Dto;
import com.example.aws.s3.signature.dto.PolicyResponseV4Dto;
import com.example.aws.s3.signature.service.SignatureManagerImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Manasee Amale
 * @Since 1.0
 */
@RestController
@Api(tags = "AWS Policy Signature service for upload file on s3 Bucket. ")
@RequestMapping(value = S3_POLICY_SIGNATURE)
@CrossOrigin(origins = "*")
public class SignatureVersionController {

	@Autowired
	private SignatureManagerImpl manager;

	@RequestMapping(value = ("/signature/V4"), method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Signature version4 for upload file on s3", notes = "Create policy and signature using AWS signature version4. ")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 403, message = "Access Denied"),
			@ApiResponse(code = 400, message = "Bad Request:Invalid parameter.") })
	public @ResponseBody PolicyResponseV4Dto uploadFileOnS3UsingSigV4(
			@RequestBody PolicyRequestDto fileInfo) throws Exception {

		return manager.createAWSSignatureVersion4(fileInfo); // using signature
																// version 4

	}

	@RequestMapping(value = ("/signature/V2"), method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Signature version2 for upload file on s3", notes = "Create policy and signature using AWS signature version2. ")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 403, message = "Access Denied"),
			@ApiResponse(code = 400, message = "Bad Request:Invalid parameter.") })
	public @ResponseBody PolicyResponseV2Dto uploadFileOnS3UsingSigV2(
			@RequestBody PolicyRequestDto fileInfo) throws Exception {

		return manager.createAWSSignatureVersion2(fileInfo); // using signature
																// version 2

	}

}
