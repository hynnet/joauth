/**
 * 
 */
package com.neurologic.oauth.service.response.impl;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.neurologic.oauth.service.response.AbstractMessage;

/**
 * @author Buhake Sindi
 * @since 15 October 2011
 *
 */
public class JsonEncodedMessage extends AbstractMessage {

	/**
	 * @throws JSONException 
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public JsonEncodedMessage(final Map<String, String> parameters, String charset) throws JSONException, UnsupportedEncodingException {
		// TODO Auto-generated constructor stub
		super();
		
		if (parameters == null) {
			throw new IllegalArgumentException("Parameters must not be null.");
		}
		
		JSONObject json = new JSONObject();
		for (Entry<String, String> entry : parameters.entrySet()) {
			json.put(entry.getKey(), entry.getValue());
		}
		
		if (charset == null) {
			charset = DEFAULT_HTTP_CHARSET;
		}
		
		setContent(json.toString().getBytes(charset));
		setContentType("application/json; charset=" +  charset);
	}
}
