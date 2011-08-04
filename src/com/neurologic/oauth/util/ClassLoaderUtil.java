/**
 * 
 */
package com.neurologic.oauth.util;

/**
 * 
 * @author Buhake Sindi
 * @since 20 July 2011
 *
 */
public class ClassLoaderUtil {

	private static ClassLoaderUtil instance = new ClassLoaderUtil();
	private ClassLoader classLoader = null;
	
	/**
	 * 
	 */
	private ClassLoaderUtil() {
		// TODO Auto-generated constructor stub
		// NOOP
	}

	/**
	 * @return the instance
	 */
	public static ClassLoaderUtil getInstance() {
		if (instance == null) {
			instance = new ClassLoaderUtil();
		}
		
		return instance;
	}

	/**
	 * @return the classLoader
	 * @see ClassLoader
	 */
	public ClassLoader getClassLoader() {
		if (classLoader == null) {
			classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null) {
				classLoader = this.getClass().getClassLoader();
			}
		}
		
		return classLoader;
	}
}
