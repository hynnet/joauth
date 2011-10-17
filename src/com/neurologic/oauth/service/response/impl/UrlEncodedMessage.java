/**
 * 
 */
package com.neurologic.oauth.service.response.impl;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import com.neurologic.oauth.service.response.AbstractMessage;

/**
 * @author Buhake Sindi
 * @since 14 October 2011
 *
 */
public class UrlEncodedMessage extends AbstractMessage {

	/**
	 * 
	 * @param parameters
	 * @param charset
	 * @throws UnsupportedEncodingException
	 */
	public UrlEncodedMessage(final Map<String, String> parameters, String charset) throws UnsupportedEncodingException {
		// TODO Auto-generated constructor stub
		super();
		
		if (parameters == null) {
			throw new IllegalArgumentException("parameters must not be null.");
		}
		
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> entry : parameters.entrySet()) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			
			sb.append(entry.getKey()).append("=")
			  .append(entry.getValue());
		}

		if (charset == null) {
			charset = DEFAULT_HTTP_CHARSET;
		}
		
		setContent(sb.toString().getBytes(charset));
		setContentType("application/x-www-form-urlencoded; charset=" +  charset);
	}
}
