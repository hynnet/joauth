/**
 * 
 */
package com.neurologic.oauth.service.response.authenticate.parameters;

/**
 * @author Buhake Sindi
 * @since 05 October 2011
 *
 */
public abstract class RealmBasedWWWAuthenticateParameters extends WWWAuthenticateParameters {

	private static final String PARAM_REALM = "realm";
	private static final String PARAM_TOKEN = "token";
	
	/**
	 * 
	 * @param realm
	 */
	protected RealmBasedWWWAuthenticateParameters(String realm) {
		super();
		// TODO Auto-generated constructor stub
		if (realm != null && !realm.isEmpty()) {
			put(PARAM_REALM, realm);
		}
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return get(PARAM_TOKEN);
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		if (token != null && !token.isEmpty()) {
			put(PARAM_TOKEN, token);
		}
	}
}
