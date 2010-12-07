/**
 * 
 */
package net.oauth.signature.impl;

import java.security.GeneralSecurityException;

import net.oauth.signature.ConsumerSecretBasedOAuthSignature;
import net.oauth.signature.OAuthSignatureMethod;
import net.oauth.util.OAuthUtil;

/**
 * @author Bienfait Sindi
 *
 */
public class OAuthPlainTextSignature extends ConsumerSecretBasedOAuthSignature {

	/**
	 * 
	 */
	public OAuthPlainTextSignature() {
		super();
		// TODO Auto-generated constructor stub
	}

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
	public String sign(String data) throws GeneralSecurityException {
		// TODO Auto-generated method stub
		String secret = getConsumerSecret() + "&" + ((getTokenSecret() == null || getTokenSecret().isEmpty()) ? "" : getTokenSecret());
		return OAuthUtil.encode(secret);
	}
}
