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
public class OAuth2BearerHttpAuthenticateParameters extends	OAuth2HttpAuthenticateParameters {

	public static final String PARAM_SCOPE = "scope";
	public static final String PARAM_ERROR_DESC = "error-desc";
	public static final String PARAM_ERROR_URI = "error-uri";
		
	/**
	 * This is the HTTP Authenticate header with <code>Bearer</code> challenge.
	 */
	public OAuth2BearerHttpAuthenticateParameters() {
		super(TokenType.BEARER);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.response.parameters.OAuth2HttpAuthenticateParameters#setError(java.lang.String)
	 */
	public void setError(ErrorCode error) {
		// TODO Auto-generated method stub
		if (error != null) {
			super.setError(error.toString());
		}
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		put(PARAM_SCOPE, scope);
	}

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		put(PARAM_ERROR_DESC, errorDescription);
	}

	/**
	 * @param errorUri the errorUri to set
	 */
	public void setErrorUri(String errorUri) {
		put(PARAM_ERROR_URI, errorUri);
	}
}
