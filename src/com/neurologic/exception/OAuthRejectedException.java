/**
 * 
 */
package com.neurologic.exception;


/**
 * @author Buhake Sindi
 * @since 04 September 2011
 *
 */
public class OAuthRejectedException extends OAuthAuthorizationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7159774809493366691L;

	/**
	 * @param message
	 */
	public OAuthRejectedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public OAuthRejectedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public OAuthRejectedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
