/**
 * 
 */
package com.neurologic.oauth.hardjono.parameters;

import net.oauth.parameters.OAuthErrorParameters;

import com.neurologic.oauth.hardjono.enums.OAuthDiscoveryError;

/**
 * @author Buhake Sindi
 * @since 21 November 2011
 *
 */
public class OAuthDiscoveryErrorParameters extends OAuthErrorParameters {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5832320786912041862L;
	public static final String ERROR_DESCRIPTION = "error_description";
	public static final String ERROR_URI = "error_uri";

	/**
	 * @param error
	 */
	public OAuthDiscoveryErrorParameters(OAuthDiscoveryError error) {
		// TODO Auto-generated constructor stub
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
		if (errorDescription != null) {
			put(ERROR_DESCRIPTION, errorDescription);
		}
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
		if (errorUri != null) {
			put(ERROR_URI, errorUri);
		}
	}
}
