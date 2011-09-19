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
public class UsedNonceException extends OAuthException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8268474676778616218L;

	/**
	 * Default constructor with message "Nonce already used."
	 */
	public UsedNonceException() {
		this("Nonce already used.");
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public UsedNonceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public UsedNonceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UsedNonceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
