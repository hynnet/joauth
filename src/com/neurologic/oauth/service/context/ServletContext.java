/**
 * 
 */
package com.neurologic.oauth.service.context;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Buhake Sindi
 * @since 30 November 2011
 *
 */
public class ServletContext implements Context {

	private javax.servlet.ServletContext context;
	private ServletRequest request;
	private ServletResponse response;
	
	/**
	 * @param context
	 * @param request
	 * @param response
	 */
	public ServletContext(javax.servlet.ServletContext context, ServletRequest request, ServletResponse response) {
		super();
		this.context = context;
		this.request = request;
		this.response = response;
	}

	/**
	 * @return the context {@link javax.servlet.ServletContext}.
	 */
	public javax.servlet.ServletContext getContext() {
		return context;
	}

	/**
	 * @return the request {@link ServletRequest}.
	 */
	public ServletRequest getRequest() {
		return request;
	}

	/**
	 * @return the response {@link ServletResponse}.
	 */
	public ServletResponse getResponse() {
		return response;
	}
}
