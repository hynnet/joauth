/**
 * 
 */
package net.oauth.parameters;

import net.oauth.enums.OAuth2Error;

/**
 * @author Buhake Sindi
 * @since 06 October 2011
 *
 */
public class OAuth2ErrorParameters extends OAuthErrorParameter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6992625965688088724L;
	
	public static final String ERROR_DESCRIPTION = "error_description";
	public static final String ERROR_URI = "error_uri";
	public static final String STATE = "state";

	/**
	 * @param statusCode
	 * @param oauthMessage
	 * @param error
	 */
	public OAuth2ErrorParameters(OAuth2Error error) {
		if (error == null) {
			throw new IllegalArgumentException("'" + ERROR + "' is required.");
		}
		
		setError(error.toString());
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return get(ERROR_DESCRIPTION);
	}

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		if (errorDescription != null)
		put(ERROR_DESCRIPTION, errorDescription);
	}

	/**
	 * @return the errorUri
	 */
	public String getErrorUri() {
		return get(ERROR_URI);
	}

	/**
	 * @param errorUri the errorUri to set
	 */
	public void setErrorUri(String errorUri) {
		if (errorUri != null)
			put(ERROR_URI, errorUri);
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return get(STATE);
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		if (state != null)
			put(STATE, state);
	}
}
