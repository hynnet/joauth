/**
 * 
 */
package net.oauth.token.oauth2.type.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import net.oauth.hash.Hasher;
import net.oauth.hash.Sha1Hasher;
import net.oauth.hash.Sha256Hasher;
import net.oauth.token.oauth2.type.MacTokenType.Algorithm;
import sun.misc.BASE64Encoder;

/**
 * @author Buhake Sindi
 * @since 21 September 2011
 *
 */
public class MacTokenTypeUtil {

//	private static final String[] HTTP_VALID_METHODS = {"HEAD", "GET", "POST", "PUT", "DELETE", "TRACE", "OPTIONS", "CONNECT"};
	private static final String[] HTTP_REQUEST_METHODS = {"GET", "POST", "PUT"};
	private static final String NEWLINE = "\n";
	private static final Map<String, Integer> defaultPortMap = new HashMap<String, Integer>();
	
	static {
		defaultPortMap.put("http", 80);
		defaultPortMap.put("https", 443);
	}
	
	/**
	 * 
	 */
	private MacTokenTypeUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static String normalizeRequestString(long age, String randomString, String requestMethod, String requestUri, String hostName, int port, String payloadBodyHash, String ext) {
		return normalizeRequestString(age + ":" + randomString, requestMethod, requestUri, hostName, port, payloadBodyHash, ext);
	}
	
	public static String normalizeRequestString(String nonce, String requestMethod, String requestUri, String hostName, int port, String payloadBodyHash, String ext) {		
		StringBuilder sb = new StringBuilder();
		sb.append(emptyNullValue(nonce)).append(NEWLINE)
		  .append(emptyNullValue(requestMethod).toUpperCase()).append(NEWLINE)
		  .append(requestUri).append(NEWLINE)
		  .append(emptyNullValue(hostName)).append(NEWLINE)
		  .append(port).append(NEWLINE)
		  .append(emptyNullValue(payloadBodyHash)).append(NEWLINE)
		  .append(emptyNullValue(ext));
		
		return sb.toString();
	}
	
	public static String bodyHash(byte[] data, Algorithm algorithm) throws GeneralSecurityException {
		if (algorithm == null) {
			throw new GeneralSecurityException(new NullPointerException("No MAC algorithm provided."));
		}
		
		Hasher hasher = null;
		if (algorithm == Algorithm.HMAC_SHA1) {
			hasher = new Sha1Hasher();
		} else if (algorithm == Algorithm.HMAC_SHA256) {
			hasher = new Sha256Hasher();
		}
		
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(hasher.hash(data));
	}
	
	public static String bodyHash(String text, String charset, Algorithm algorithm) throws GeneralSecurityException {
		if (algorithm == null) {
			throw new GeneralSecurityException(new NullPointerException("No MAC algorithm provided."));
		}
		
		Hasher hasher = null;
		if (algorithm == Algorithm.HMAC_SHA1) {
			hasher = new Sha1Hasher();
		} else if (algorithm == Algorithm.HMAC_SHA256) {
			hasher = new Sha256Hasher();
		}
		
		try {
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(hasher.hash(text, charset));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new GeneralSecurityException(e);
		}
	}
	
	public static int defaultPort(String scheme) {
		return defaultPortMap.get(scheme);
	}
	
	public static boolean isValidHttpRequestMethod(String s) {
		for (String requestMethod : HTTP_REQUEST_METHODS) {
			if (requestMethod.equals(s)) {
				return true;
			}
		}
		
		return false;
	}
	
	private static String emptyNullValue(String s) {
		if (s == null) {
			s = "";
		}
		
		return s;
	}
}
