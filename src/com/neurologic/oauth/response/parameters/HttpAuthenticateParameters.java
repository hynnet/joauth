/**
 * 
 */
package com.neurologic.oauth.response.parameters;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Buhake Sindi
 * @since 26 September 2011
 *
 */
public abstract class HttpAuthenticateParameters {

	public static final String PARAM_REALM = "realm";
	
	private String challenge;
	private Map<String, String> parameterMap;

	/**
	 * @param challenge
	 */
	protected HttpAuthenticateParameters(String challenge) {
		super();
		this.challenge = challenge;
		parameterMap = new LinkedHashMap<String, String>();
	}

	/**
	 * @return the challenge
	 */
	public String getChallenge() {
		return challenge;
	}
	
	/**
	 * @param realm the realm to set
	 */
	public void setRealm(String realm) {
		put(PARAM_REALM, realm);
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
