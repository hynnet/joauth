/**
 * 
 */
package com.neurologic.oauth.service.response.impl;

import java.io.IOException;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import com.neurologic.oauth.service.response.AbstractOAuthResult;
import com.neurologic.oauth.service.response.Message;
import com.neurologic.oauth.service.response.ServiceContext;

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
	 * @see com.neurologic.oauth.service.response.Result#execute(com.neurologic.oauth.service.response.ServiceContext)
	 */
	@Override
	public void execute(ServiceContext context) throws IOException {
		// TODO Auto-generated method stub
		if (context == null) {
			throw new IOException("A Service context is required.");
		}
		
		if (statusCode < 100) {
			throw new IOException("Invalid HTTP Status Code '" + statusCode + "'.");
		}
		
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		response.reset();
		for (Entry<String, String> entry : headersEntrySet()) {
			response.addHeader(entry.getKey(), entry.getValue());
		}
		
		//Set status
		response.setStatus(statusCode);
		
		if (message != null) {
			//Set Content-Type
			response.addHeader("Content-Type", message.getContentType());
			//Set Content-Length
			response.addHeader("Content-Length", String.valueOf(message.getContentLength()));
			
			//Write...
			message.writeTo(response.getOutputStream());
		}
	}
}
