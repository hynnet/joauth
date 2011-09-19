/**
 * 
 */
package com.neurologic.exception;

/**
 * @author Buhake Sindi
 * @since 18 August 2011
 *
 */
public class StoreException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2283969140929095536L;

	/**
	 * 
	 */
	public StoreException() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * @param message
	 */
	public StoreException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public StoreException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public StoreException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
}
