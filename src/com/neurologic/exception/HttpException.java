/**
 * 
 */
package com.neurologic.exception;

/**
 * @author Bienfait Sindi
 * @since 28 November 2010
 *
 */
public class HttpException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8817293816310682090L;

	/**
	 * 
	 */
	public HttpException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public HttpException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public HttpException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public HttpException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
}
