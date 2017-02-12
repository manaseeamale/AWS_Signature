package com.example.aws.s3.signature.dto;

import java.io.Serializable;
import java.net.URL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Manasee Amale
 * @Since 1.0
 */
@JsonInclude(Include.NON_NULL)
public class PolicyResponseV2Dto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5896650259407154715L;

	public String policy;
	public String signature;
	public String objectKey;
	public long expireAfter;
	public URL destinatonUrl;

	public PolicyResponseV2Dto() {

		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the policy
	 */
	public String getPolicy() {
		return policy;
	}

	/**
	 * @param policy
	 *            the policy to set
	 */
	public void setPolicy(String policy) {
		this.policy = policy;
	}

	/**
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * @param signature
	 *            the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * @return the objectKey
	 */
	public String getObjectKey() {
		return objectKey;
	}

	/**
	 * @param objectKey
	 *            the objectKey to set
	 */
	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	/**
	 * @return the expireAfter
	 */
	public long getExpireAfter() {
		return expireAfter;
	}

	/**
	 * @param expireAfter
	 *            the expireAfter to set
	 */
	public void setExpireAfter(long expireAfter) {
		this.expireAfter = expireAfter;
	}

	/**
	 * @return the destinatonUrl
	 */
	public URL getDestinatonUrl() {
		return destinatonUrl;
	}

	/**
	 * @param destinatonUrl
	 *            the destinatonUrl to set
	 */
	public void setDestinatonUrl(URL destinatonUrl) {
		this.destinatonUrl = destinatonUrl;
	}

}
