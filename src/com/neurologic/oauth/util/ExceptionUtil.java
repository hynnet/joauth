/**
 * 
 */
package com.neurologic.oauth.util;

/**
 * @author Buhake Sindi
 * @since 31 August 2011
 *
 */
public class ExceptionUtil {

	private ExceptionUtil() {}
	
	/**
	 * Throw a {@link NullPointerException} if <code>object == null</code>.
	 * @param object
	 * @param attribute
	 * @throws NullPointerException
	 */
	public static void throwIfNull(Object object, String message) throws NullPointerException {
		if (object == null) {
			throw new NullPointerException(message);
		}
	}
}
