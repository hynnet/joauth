/**
 * 
 */
package com.neurologic.oauth.service.response.impl;

import java.io.IOException;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.neurologic.oauth.service.response.AbstractOAuthResult;
import com.neurologic.oauth.service.response.Message;

/**
 * @author Buhake Sindi
 * @since 08 October 2011
 *
 */
public class OAuthMessageResult extends AbstractOAuthResult {

	private int statusCode;
	private Message message;
	
	/**
	 * 
	 */
	public OAuthMessageResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param statusCode
	 */
	public OAuthMessageResult(int statusCode) {
		this(statusCode, null);
	}

	/**
	 * @param statusCode
	 * @param message
	 */
	public OAuthMessageResult(int statusCode, Message message) {
		super();
		setStatusCode(statusCode);
		setMessage(message);
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Message message) {
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.Result#execute(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	public void execute(ServletRequest request, ServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		if (statusCode < 100) {
			throw new IOException("Invalid HTTP Status Code '" + statusCode + "'.");
		}
		
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		response.reset();
		for (Entry<String, String> entry : headersEntrySet()) {
			httpServletResponse.addHeader(entry.getKey(), entry.getValue());
		}
		
		//Set status
		httpServletResponse.setStatus(statusCode);
		
		if (message != null) {
			//Set Content-Type
			httpServletResponse.addHeader("Content-Type", message.getContentType());
			//Set Content-Length
			httpServletResponse.addHeader("Content-Length", String.valueOf(message.getContentLength()));
			
			//Write...
			message.writeTo(response.getOutputStream());
		}
	}
}
