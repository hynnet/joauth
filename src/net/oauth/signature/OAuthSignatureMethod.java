/**
 * 
 */
package net.oauth.signature;

/**
 * @author Bienfait Sindi
 * @since 08 December 2009
 */
public interface OAuthSignatureMethod {

	public static final String SIGNATURE_METHOD_HMAC_SHA1 = "HMAC-SHA1";
	public static final String SIGNATURE_METHOD_RSA_SHA1 = "RSA-SHA1";
	public static final String SIGNATURE_METHOD_PLAINTEXT = "PLAINTEXT";
}
