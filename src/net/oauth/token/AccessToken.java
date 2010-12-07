/**
 * 
 */
package net.oauth.token;

import java.util.Map;

/**
 * @author Bienfait Sindi
 * @since 20 November 2010
 *
 */
public class AccessToken extends OAuthToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1584406119580124615L;
	private String tokenSecret;
	private Map<String, String> additionalParameters;
	
	/**
	 * @param token
	 * @param tokenSecret
	 * @param additionalParameters
	 */
	public AccessToken(String token, String tokenSecret, Map<String, String> additionalParameters) {
		super(token);
		this.tokenSecret = tokenSecret;
		this.additionalParameters = additionalParameters;
	}

	/**
	 * @return the tokenSecret
	 */
	public String getTokenSecret() {
		return tokenSecret;
	}

	/**
	 * @return the additionalParameters
	 */
	public Map<String, String> getAdditionalParameters() {
		return additionalParameters;
	}
}
