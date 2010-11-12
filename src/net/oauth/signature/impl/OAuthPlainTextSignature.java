/**
 * 
 */
package net.oauth.signature.impl;

import java.security.GeneralSecurityException;

import net.oauth.signature.OAuthSignature;
import net.oauth.signature.OAuthSignatureMethod;
import net.oauth.util.OAuthUtil;

/**
 * @author Bienfait Sindi
 *
 */
public class OAuthPlainTextSignature implements OAuthSignature {

	/* (non-Javadoc)
	 * @see net.oauth.signature.OAuthSignature#getOAuthSignatureMethod()
	 */
	@Override
	public String getOAuthSignatureMethod() {
		// TODO Auto-generated method stub
		return OAuthSignatureMethod.SIGNATURE_METHOD_PLAINTEXT;
	}

	/* (non-Javadoc)
	 * @see net.oauth.signature.OAuthSignature#sign(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String sign(String data, String consumerSecret, String tokenSecret) throws GeneralSecurityException {
		// TODO Auto-generated method stub
		String secret = consumerSecret + "&" + ((tokenSecret == null || tokenSecret.isEmpty()) ? "" : tokenSecret);
		return OAuthUtil.encode(secret);
	}
}
