/**
 * 
 */
package net.oauth.exception;

/**
 * @author Bienfait Sindi
 *
 */
public class OAuthException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3440473617135361308L;

//	/**
//	 * 
//	 */
//	public OAuthException() {
//		// TODO Auto-generated constructor stub
//	}

	/**
	 * @param message
	 */
	public OAuthException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public OAuthException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public OAuthException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
