/**
 * 
 */
package com.neurologic.oauth.service.response.impl;

import java.io.UnsupportedEncodingException;

import com.neurologic.oauth.service.response.AbstractMessage;

/**
 * @author Buhake Sindi
 * @since 14 October 2011
 *
 */
public class StringMessage extends AbstractMessage {

	/**
	 * @param contentType
	 * @param content
	 * @throws UnsupportedEncodingException 
	 */
	public StringMessage(final String content, String charset) throws UnsupportedEncodingException {
		super();
		if (content == null) {
			throw new IllegalArgumentException("Content string must not be null.");
		}
		
		if (charset == null) {
			charset = DEFAULT_HTTP_CHARSET;
		}
		
		setContent(content.getBytes(charset));
		setContentType("text/plain; charset=" + charset);
	}
}
