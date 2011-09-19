/**
 * 
 */
package com.neurologic.oauth.builder;

import java.security.PrivateKey;

import net.oauth.signature.ConsumerSecretBasedOAuthSignature;
import net.oauth.signature.OAuthSignature;
import net.oauth.signature.OAuthSignatureMethod;
import net.oauth.signature.impl.OAuthHmacSha1Signature;
import net.oauth.signature.impl.OAuthPlainTextSignature;
import net.oauth.signature.impl.OAuthRsaSha1Signature;

/**
 * @author Buhake Sindi
 * @since 01 September 2011
 *
 */
public class OAuthSignatureBuilder {

	private String consumerSecret;
	private String tokenSecret;
	private PrivateKey privateKey;
	/**
	 * @param consumerSecret the consumerSecret to set
	 */
	public OAuthSignatureBuilder setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
		return this;
	}
	/**
	 * @param tokenSecret the tokenSecret to set
	 */
	public OAuthSignatureBuilder setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
		return this;
	}
	/**
	 * @param privateKey the privateKey to set
	 */
	public OAuthSignatureBuilder setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
		return this;
	}
	
	public OAuthSignature build(String signatureMethod) {
		OAuthSignature signature = null;
		
		if (OAuthSignatureMethod.SIGNATURE_METHOD_RSA_SHA1.equals(signatureMethod)) {
			if (privateKey == null) {
				throw new IllegalArgumentException("No Private Key provided for RSA-SHA1 Signature Method.");
			}
			
			signature = new OAuthRsaSha1Signature(privateKey);
		} else {
			if (OAuthSignatureMethod.SIGNATURE_METHOD_HMAC_SHA1.equals(signatureMethod)) {
				signature = new OAuthHmacSha1Signature();
			} else if (OAuthSignatureMethod.SIGNATURE_METHOD_PLAINTEXT.equals(signatureMethod)) {
				signature = new OAuthPlainTextSignature();
			}
			
			if (signature != null) {
				((ConsumerSecretBasedOAuthSignature)signature).setConsumerSecret(consumerSecret);
				((ConsumerSecretBasedOAuthSignature)signature).setTokenSecret(tokenSecret);	
			}
		}
		
		return signature;
	}
}
