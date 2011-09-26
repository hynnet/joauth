/**
 * 
 */
package com.neurologic.oauth.service.provider.response;

import java.util.Map;
import java.util.Map.Entry;

import net.oauth.parameters.KeyValuePair;
import net.oauth.parameters.QueryKeyValuePair;

/**
 * @author Buhake Sindi
 * @since 19 September 2011
 *
 */
public class UrlEncodedFormResponseMessage extends AbstractOAuthResponseMessage {

	private static final String CONTENT_TYPE_URL_ENCODED = "application/x-www-form-urlencoded";
	private static final String ENCODING_CHARSET = "UTF-8";
	private KeyValuePair kvp = new QueryKeyValuePair();
	
	/**
	 * 
	 */
	public UrlEncodedFormResponseMessage() {
		// TODO Auto-generated constructor stub
		super();
		setContentType(CONTENT_TYPE_URL_ENCODED + ";charset=" + ENCODING_CHARSET);
	}
	
	/**
	 * @param map
	 */
	public UrlEncodedFormResponseMessage(Map<String, String> map) {
		this();
		if (map != null && !map.isEmpty()) {
			for (Entry<String, String> entry : map.entrySet()) {
				kvp.add(entry.getKey(), entry.getValue());
			}
		}
	}

	public void put(String key, String value) {
		kvp.add(key, value);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.response.OAuthResponseMessage#getMessage()
	 */
	@Override
	public String getMessage() {
		return kvp.toString();
	}
}
