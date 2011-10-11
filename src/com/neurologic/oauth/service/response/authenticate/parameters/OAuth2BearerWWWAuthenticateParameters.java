/**
 * 
 */
package com.neurologic.oauth.service.response.authenticate.parameters;

/**
 * @author Buhake Sindi
 * @since 26 September 2011
 *
 */
public class OAuth2BearerWWWAuthenticateParameters extends OAuth2WWWAuthenticateParameters {

	private static final String PARAM_SCOPE = "scope";
	private static final String PARAM_ERROR_DESCRIPTION = "error_description";
	private static final String PARAM_ERROR_URI = "error_uri";
		
	/**
	 * @param realm
	 * @param error
	 */
	public OAuth2BearerWWWAuthenticateParameters(String realm, BearerErrorCode error) {
		super(realm, error.toString());
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return get(PARAM_SCOPE);
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		if (scope != null && !scope.isEmpty()) {
			put(PARAM_SCOPE, scope);
		}
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return get(PARAM_ERROR_DESCRIPTION);
	}

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		if (errorDescription != null && !errorDescription.isEmpty()) {
			put(PARAM_ERROR_DESCRIPTION, errorDescription);
		}
	}

	/**
	 * @return the errorUri
	 */
	public String getErrorUri() {
		return get(PARAM_ERROR_URI);
	}

	/**
	 * @param errorUri the errorUri to set
	 */
	public void setErrorUri(String errorUri) {
		if (errorUri != null && !errorUri.isEmpty()) {
			put(PARAM_ERROR_URI, errorUri);
		}
	}
}
