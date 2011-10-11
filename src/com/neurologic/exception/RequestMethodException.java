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
public class RequestMethodException extends OAuthException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5187923325915480721L;

	/**
	 * @param message
	 */
	public RequestMethodException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public RequestMethodException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RequestMethodException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
