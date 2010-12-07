/**
 * 
 */
package net.oauth.token;

import net.oauth.util.OAuthUtil;

/**
 * @author Bienfait Sindi
 * @since 20 November 2010
 *
 */
public class RequestToken extends OAuthToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5435052772260977531L;
	private String tokenSecret;
	private boolean oauthCallbackConfirmed;
	
	/**
	 * 
	 * @param token
	 * @param tokenSecret
	 * @param oauthCallbackConfirmed
	 */
	public RequestToken(String token, String tokenSecret, boolean oauthCallbackConfirmed) {
		super(token);
		// TODO Auto-generated constructor stub
		this.tokenSecret = tokenSecret;
		this.oauthCallbackConfirmed = oauthCallbackConfirmed;
	}

	/**
	 * @return the tokenSecret
	 */
	public String getTokenSecret() {
		return tokenSecret;
	}

	/**
	 * @return the oauthCallbackConfirmed
	 */
	public boolean isOauthCallbackConfirmed() {
		return oauthCallbackConfirmed;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ("oauth_token=" + OAuthUtil.encode(getToken()) + "&oauth_token_secret=" + OAuthUtil.encode(getTokenSecret()));
	}
}
