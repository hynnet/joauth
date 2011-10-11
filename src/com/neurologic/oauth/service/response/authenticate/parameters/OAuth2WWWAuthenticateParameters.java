/**
 * 
 */
package com.neurologic.oauth.service.response.authenticate.parameters;

/**
 * @author Buhake Sindi
 * @since 26 September 2011
 *
 */
public class OAuth2WWWAuthenticateParameters extends RealmBasedWWWAuthenticateParameters {

	private static final String PARAM_ERROR = "error";

	/**
	 * @param realm
	 * @param error
	 */
	public OAuth2WWWAuthenticateParameters(String realm, String error) {
		super(realm);
		if (error != null && !error.isEmpty()) {
			put(PARAM_ERROR, error);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getError() {
		return get(PARAM_ERROR);
	}
}
