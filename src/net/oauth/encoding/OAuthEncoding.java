/**
 * 
 */
package net.oauth.encoding;

/**
 * @author Bienfait Sindi
 * @since 29 December 2009
 */
public interface OAuthEncoding {
	
	public String encode(String data);
	public String decode(String encodedData);
}
