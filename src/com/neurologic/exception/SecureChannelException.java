/**
 * 
 */
package com.neurologic.exception;

import net.oauth.exception.OAuthException;


/**
 * @author Buhake Sindi
 * @since 10 October 2011
 *
 */
public class SecureChannelException extends OAuthException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6112608843044661281L;

	/**
	 * @param message
	 */
	public SecureChannelException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public SecureChannelException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SecureChannelException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
}
