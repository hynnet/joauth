/**
 * 
 */
package net.oauth.hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Buhake Sindi
 * @since 21 September 2011
 *
 */
public abstract class AbstractHasher implements Hasher {

	private String algorithm;
	
	/**
	 * @param algorithm
	 */
	protected AbstractHasher(String algorithm) {
		super();
		this.algorithm = algorithm;
	}

	/* (non-Javadoc)
	 * @see net.oauth.hash.Hasher#hash(java.lang.String, java.lang.String)
	 */
	@Override
	public byte[] hash(String text, String charset) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		return hash(text.getBytes(charset));
	}

	/* (non-Javadoc)
	 * @see net.oauth.hash.Hasher#hash(byte[])
	 */
	@Override
	public byte[] hash(byte[] data) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		return digest.digest(data);
	}
}
