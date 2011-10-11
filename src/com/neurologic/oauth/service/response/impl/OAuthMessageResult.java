/**
 * 
 */
package com.neurologic.oauth.service.response.impl;

import java.io.IOException;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import com.neurologic.oauth.service.response.AbstractOAuthResult;
import com.neurologic.oauth.service.response.ServiceContext;
import com.neurologic.oauth.service.response.formatter.ParameterFormatter;

/**
 * @author Buhake Sindi
 * @since 08 October 2011
 *
 */
public class OAuthMessageResult extends AbstractOAuthResult {

	private int statusCode;
	private ParameterFormatter formatter;

	/**
	 * @param formatter
	 */
	public OAuthMessageResult(ParameterFormatter formatter) {
		super();
		this.formatter = formatter;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.Result#execute(com.neurologic.oauth.service.response.ServiceContext)
	 */
	@Override
	public void execute(ServiceContext context) throws IOException {
		// TODO Auto-generated method stub
		if (formatter == null) {
			throw new IOException("A formatter is required.");
		}
		
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
		
		//Write content-type
		response.addHeader("Content-Type", formatter.getContentType());
		
		//Set status
		response.setStatus(statusCode);
		//Write...
		if (getAuthParameters() != null) {
			response.getWriter().write(formatter.format(getAuthParameters()));
		}
	}
}
