/**
 * 
 */
package net.oauth.signature.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import net.oauth.exception.OAuthException;
import net.oauth.signature.OAuthSignature;
import net.oauth.signature.OAuthSignatureMethod;
import net.oauth.util.OAuthSignatureUtil;
import sun.misc.BASE64Decoder;

/**
 * @author Bienfait Sindi
 * @since 30 March 2010
 *
 */
public class OAuthRsaSha1Signature implements OAuthSignature {
	
	private PrivateKey privateKey;

	/**
	 * @param privateKey
	 */
	public OAuthRsaSha1Signature(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	
	public OAuthRsaSha1Signature(String privateKeyString) throws OAuthException {
		if (privateKeyString == null || privateKeyString.isEmpty()) {
			throw new OAuthException("Private key string cannot be null nor can it be empty.");
		}
		
//		if (privateKeyString.isEmpty()) {
//			throw new OAuthException("Private Key String cannot be empty.");
//		}
		
		try {
			KeyFactory factory = KeyFactory.getInstance("RSA");
			byte[] privateKeyBytes = new BASE64Decoder().decodeBuffer(privateKeyString);
			EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			privateKey = factory.generatePrivate(privateKeySpec);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e);
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e);
		}
	}

	/* (non-Javadoc)
	 * @see net.oauth.signature.OAuthSignature#getOAuthSignatureMethod()
	 */
	@Override
	public String getOAuthSignatureMethod() {
		// TODO Auto-generated method stub
		return OAuthSignatureMethod.SIGNATURE_METHOD_RSA_SHA1;
	}

	/* (non-Javadoc)
	 * @see net.oauth.signature.OAuthSignature#sign(java.lang.String)
	 */
	@Override
	public String sign(String data) throws GeneralSecurityException {
		// TODO Auto-generated method stub
		return OAuthSignatureUtil.generateRSASHA1Signature(privateKey, data);
	}
}
