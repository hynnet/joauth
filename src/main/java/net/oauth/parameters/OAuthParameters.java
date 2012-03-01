/**
 * 
 */
package net.oauth.parameters;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Buhake Sindi
 * @since 06 October 2011
 *
 */
public abstract class OAuthParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3167327773619182962L;
	private Map<String, String> parameterMap;

	/**
	 * 
	 */
	protected OAuthParameters() {
		super();
		// TODO Auto-generated constructor stub
		parameterMap = new LinkedHashMap<String, String>();
	}
	
	/**
	 * Adds a key/value to the parameter map.
	 * @param key
	 * @param value
	 */
	protected void put(String key, String value) {
		if (key != null && !key.isEmpty()) {
			parameterMap.put(key, value);
		}
	}
	
	/**
	 * Get a value from the parameter map (based on the key provided).
	 * @param key
	 * @return
	 */
	protected String get(String key) {
		return parameterMap.get(key);
	}
	
	/**
	 * Returns true if the parameter map is empty, false otherwise.
	 * @return
	 */
	public boolean isEmpty() {
		return parameterMap.isEmpty();
	}
	
	/**
	 * Returns a unmodifiable copy of this parameter map.
	 * @return
	 */
	public final Map<String, String> getOAuthParameters() {
		return Collections.unmodifiableMap(parameterMap);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getName() + " [parameterMap=" + parameterMap + "]";
	}
}
