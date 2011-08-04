/**
 * 
 */
package com.neurologic.exception;

import net.oauth.exception.OAuthException;

/**
 * @author Buhake Sindi
 * @since 11 July 2011
 *
 */
public class OAuthAuthorizationException extends OAuthException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -271563398611814375L;

	/**
	 * @param message
	 */
	public OAuthAuthorizationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public OAuthAuthorizationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public OAuthAuthorizationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
}
