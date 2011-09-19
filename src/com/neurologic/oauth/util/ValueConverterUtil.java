/**
 * 
 */
package com.neurologic.oauth.util;

/**
 * @author Buhake Sindi
 * @since 07 September 2011
 *
 */
public class ValueConverterUtil {

	private ValueConverterUtil() {}
	
	public static byte convert(String s, byte defaultValue) {
		if (s == null || s.isEmpty()) {
			return defaultValue;
		}
		
		return Byte.parseByte(s);
	}
	
	public static boolean convert(String s, boolean defaultValue) {
		if (s == null || s.isEmpty()) {
			return defaultValue;
		}
		
		return Boolean.parseBoolean(s);
	}
	
	public static short convert(String s, short defaultValue) {
		if (s == null || s.isEmpty()) {
			return defaultValue;
		}
		
		return Short.parseShort(s);
	}
	
	public static int convert(String s, int defaultValue) {
		if (s == null || s.isEmpty()) {
			return defaultValue;
		}
		
		return Integer.parseInt(s);
	}
	
	public static long convert(String s, long defaultValue) {
		if (s == null || s.isEmpty()) {
			return defaultValue;
		}
		
		return Long.parseLong(s);
	}
	
	public static double convert(String s, double defaultValue) {
		if (s == null || s.isEmpty()) {
			return defaultValue;
		}
		
		return Double.parseDouble(s);
	}
	
	public static float convert(String s, float defaultValue) {
		if (s == null || s.isEmpty()) {
			return defaultValue;
		}
		
		return Float.parseFloat(s);
	}
}
