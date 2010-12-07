/**
 * 
 */
package net.oauth.token;

import java.io.Serializable;


/**
 * @author Bienfait Sindi
 * @since 07 December 2009
 *
 */
public abstract class OAuthToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2805831035072950221L;
	private String token;
	
	/**
	 * @param token
	 */
	protected OAuthToken(String token) {
		this.token = token;
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return ("oauth_token=" + OAuthUtil.encode(getToken()) + "&oauth_token_secret=" + OAuthUtil.encode(getTokenSecret()));
//	}
}
