/**
 * 
 */
package com.example.aws.s3.signature.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Manasee Amale
 * @Since 1.0
 */
@JsonInclude(Include.NON_NULL)
public class PolicyRequestDto {

	@JsonProperty("file_name")
	public String fileName;

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
