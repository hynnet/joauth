/**
 * 
 */
package com.neurologic.exception;

import net.oauth.exception.OAuthException;

/**
 * @author Buhake Sindi
 * @since 04 September 2011
 *
 */
public class UsedTokenException extends OAuthException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -883087449110912821L;
	
	/**
	 * Default constructor with message "Token already used.".
	 */
	public UsedTokenException() {
		this("Token already used.");
	}
	
	/**
	 * @param message
	 */
	public UsedTokenException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UsedTokenException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UsedTokenException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
