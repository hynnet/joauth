/**
 * 
 */
package net.oauth.signature.impl;

import java.security.GeneralSecurityException;

import net.oauth.signature.OAuthSignature;
import net.oauth.signature.OAuthSignatureMethod;
import net.oauth.util.OAuthSignatureUtil;
import net.oauth.util.OAuthUtil;

/**
 * @author Bienfait Sindi
 *
 */
public class OAuthHmacSHA1Signature implements OAuthSignature {

	
	/* (non-Javadoc)
	 * @see net.oauth.signature.OAuthSignature#getOAuthSignatureMethod()
	 */
	@Override
	public String getOAuthSignatureMethod() {
		// TODO Auto-generated method stub
		return OAuthSignatureMethod.SIGNATURE_METHOD_HMAC_SHA1;
	}

	/* (non-Javadoc)
	 * @see net.oauth.signature.OAuthSignature#sign(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String sign(String data, String consumerSecret, String tokenSecret) throws GeneralSecurityException {
		// TODO Auto-generated method stub
		String key = OAuthUtil.encode(consumerSecret) + "&" + OAuthUtil.encode((tokenSecret == null || tokenSecret.isEmpty()) ? "" : tokenSecret);
		return OAuthSignatureUtil.generateHmacSHA1Signature(data, key);
	}
}
