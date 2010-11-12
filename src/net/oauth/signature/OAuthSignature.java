/**
 * 
 */
package net.oauth.signature;

import java.security.GeneralSecurityException;

/**
 * @author Bienfait Sindi
 * @since 29 December 2009
 */
public interface OAuthSignature {

	public String getOAuthSignatureMethod();
	public String sign(String data, String consumerSecret, String tokenSecret) throws GeneralSecurityException;
}
