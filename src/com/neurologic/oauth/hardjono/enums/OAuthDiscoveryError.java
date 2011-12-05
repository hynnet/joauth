/**
 * 
 */
package com.neurologic.oauth.hardjono.enums;

/**
 * @author Buhake Sindi
 * @since 21 November 2011
 *
 */
public enum OAuthDiscoveryError {
	UNAUTHORIZED_CLIENT("unauthorized_client")
	,HOSTMETA_ERROR("hostmeta_error")
	;
	private final String error;

	/**
	 * @param error
	 */
	private OAuthDiscoveryError(final String error) {
		this.error = error;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return error;
	}
}
