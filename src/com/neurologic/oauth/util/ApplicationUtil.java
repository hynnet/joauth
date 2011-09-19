/**
 * 
 */
package com.neurologic.oauth.util;

/**
 * @author Buhake Sindi
 * @since 07 September 2011
 *
 */
public class ApplicationUtil {

	private ApplicationUtil(){}
	
	public static <T> Class<T> applicationClass(String className) throws ClassNotFoundException {
		return applicationClass(className, null);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> applicationClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
		if (classLoader == null) {
			classLoader = Thread.currentThread().getContextClassLoader();
			
			if (classLoader == null) {
				classLoader = ApplicationUtil.class.getClassLoader();
			}
		}
		
		return (Class<T>) classLoader.loadClass(className);
	}
	
	public static Object applicationInstance(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return applicationInstance(className, null);
	}
	
	public static Object applicationInstance(String className, ClassLoader classLoader) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return applicationClass(className, classLoader).newInstance();
	}
}
