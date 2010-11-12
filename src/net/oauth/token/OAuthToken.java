/**
 * 
 */
package net.oauth.token;

import net.oauth.util.OAuthUtil;

/**
 * @author Bienfait Sindi
 * @since 07 December 2009
 *
 */
public class OAuthToken {

	private String token;
	private String tokenSecret;
	
	/**
	 * @param token
	 * @param tokenSecret
	 */
	public OAuthToken(String token, String tokenSecret) {
		this.token = token;
		this.tokenSecret = tokenSecret;
	}
	/**
	 * @return the token
	 */
	public final String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
//	protected void setToken(String token) {
//		this.token = token;
//	}
	/**
	 * @return the tokenSecret
	 */
	public final String getTokenSecret() {
		return tokenSecret;
	}
	/**
	 * @param tokenSecret the tokenSecret to set
	 */
//	protected void setTokenSecret(String tokenSecret) {
//		this.tokenSecret = tokenSecret;
//	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ("oauth_token=" + OAuthUtil.encode(getToken()) + "&oauth_token_secret=" + OAuthUtil.encode(getTokenSecret()));
	}
}
