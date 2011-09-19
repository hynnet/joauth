/**
 * 
 */
package com.neurologic.exception;

/**
 * @author Buhake Sindi
 * @since 18 August 2011
 *
 */
public class ManagerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8817624348902995567L;

	/**
	 * 
	 */
	public ManagerException() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * @param message
	 */
	public ManagerException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ManagerException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ManagerException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
}
