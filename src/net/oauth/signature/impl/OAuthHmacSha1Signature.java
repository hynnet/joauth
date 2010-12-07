/**
 * 
 */
package net.oauth.signature.impl;

import java.security.GeneralSecurityException;

import net.oauth.signature.ConsumerSecretBasedOAuthSignature;
import net.oauth.signature.OAuthSignatureMethod;
import net.oauth.util.OAuthSignatureUtil;
import net.oauth.util.OAuthUtil;

/**
 * @author Bienfait Sindi
 *
 */
public class OAuthHmacSha1Signature extends ConsumerSecretBasedOAuthSignature {
	
	/**
	 * 
	 */
	public OAuthHmacSha1Signature() {
		super();
		// TODO Auto-generated constructor stub
	}

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
	public String sign(String data) throws GeneralSecurityException {
		// TODO Auto-generated method stub
		String key = OAuthUtil.encode(getConsumerSecret()) + "&" + OAuthUtil.encode((getTokenSecret() == null || getTokenSecret().isEmpty()) ? "" : getTokenSecret());
		return OAuthSignatureUtil.generateHmacSHA1Signature(data, key);
	}
}
