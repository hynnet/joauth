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
public class MissingParameterException extends OAuthException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4050113146217010632L;

	/**
	 * @param message
	 */
	public MissingParameterException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public MissingParameterException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MissingParameterException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
}
