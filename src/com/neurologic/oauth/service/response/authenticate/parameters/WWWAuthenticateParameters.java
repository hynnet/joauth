/**
 * 
 */
package com.neurologic.oauth.service.response.authenticate.parameters;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Buhake Sindi
 * @since 26 September 2011
 *
 */
public abstract class WWWAuthenticateParameters {

	private Map<String, String> parameterMap;

	/**
	 * @param challenge
	 */
	protected WWWAuthenticateParameters() {
		super();
		parameterMap = new LinkedHashMap<String, String>();
	}

	protected String get(String key) {
		return parameterMap.get(key);
	}
	
	protected void put(String key, String value) {
		parameterMap.put(key, value);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> entry : parameterMap.entrySet()) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			
			sb.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
		}
		
		return sb.toString();
	}
}
