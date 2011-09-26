/**
 * 
 */
package net.oauth.hash;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Buhake Sindi
 * @since 21 September 2011
 *
 */
public interface Hasher {

	public byte[] hash(String text, String charset) throws UnsupportedEncodingException, NoSuchAlgorithmException;
	public byte[] hash(byte[] data) throws NoSuchAlgorithmException;
}
