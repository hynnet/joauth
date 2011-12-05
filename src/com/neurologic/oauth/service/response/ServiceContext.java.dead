/**
 * 
 */
package com.neurologic.oauth.service.response;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @author Buhake Sindi
 * @since 08 October 2011
 *
 */
public class ServiceContext {

	private final ServletRequest request;
	private final ServletResponse response;
	
	/**
	 * @param request
	 * @param response
	 */
	public ServiceContext(final HttpServletRequest request, final HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

	/**
	 * @param request
	 * @param response
	 */
	public ServiceContext(ServletRequest request, ServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

	/**
	 * @return the request
	 */
	public ServletRequest getRequest() {
		return request;
	}

	/**
	 * @return the response
	 */
	public ServletResponse getResponse() {
		return response;
	}
	
	/**
	 * Create an {@link HttpSession}, if possible.
	 * @param create
	 * @return
	 */
	public HttpSession getSession(boolean create) {
		if (request instanceof HttpServletRequest) {
			return ((HttpServletRequest)request).getSession(create);
		}
		
		return null;
	}
	
	public void flushBuffer() throws IOException {
		if (response != null) {
			response.flushBuffer();
		}
	}
}
