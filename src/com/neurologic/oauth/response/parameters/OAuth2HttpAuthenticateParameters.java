/**
 * 
 */
package com.neurologic.oauth.response.parameters;

import net.oauth.enums.TokenType;

/**
 * @author Buhake Sindi
 * @since 26 September 2011
 *
 */
public abstract class OAuth2HttpAuthenticateParameters extends HttpAuthenticateParameters {

	public static final String PARAM_AUTH_PARAM = "auth-param";
	public static final String PARAM_ERROR = "error";
	
	/**
	 * @param challenge
	 */
	protected OAuth2HttpAuthenticateParameters(TokenType type) {
		super(type.toString());
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the authParam
	 */
	public String getAuthParam() {
		return get(PARAM_AUTH_PARAM);
	}

	/**
	 * @param authParam the authParam to set
	 */
	public void setAuthParam(String authParam) {
		put(PARAM_AUTH_PARAM, authParam);
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return get(PARAM_ERROR);
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		put(PARAM_ERROR, error);
	}
}
