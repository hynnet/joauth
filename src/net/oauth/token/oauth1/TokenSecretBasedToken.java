/**
 * 
 */
package net.oauth.token.oauth1;

/**
 * @author Buhake Sindi
 * @since 12 August 2011
 *
 */
public abstract class TokenSecretBasedToken extends OAuthToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3142578651128838681L;
	private String tokenSecret;

	/**
	 * @param token
	 * @param tokenSecret
	 */
	protected TokenSecretBasedToken(String token, String tokenSecret) {
		super(token);
		this.tokenSecret = tokenSecret;
	}

	/**
	 * @return the tokenSecret
	 */
	public String getTokenSecret() {
		return tokenSecret;
	}
}
