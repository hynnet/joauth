/**
 * 
 */
package com.neurologic.oauth.service.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Buhake Sindi
 * @since 30 November 2011
 *
 */
public class HttpServletContext extends ServletContext {

	/**
	 * @param context
	 * @param request
	 * @param response
	 */
	public HttpServletContext(javax.servlet.ServletContext context, HttpServletRequest request, HttpServletResponse response) {
		super(context, request, response);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.context.ServletContext#getRequest()
	 */
	@Override
	public HttpServletRequest getRequest() {
		// TODO Auto-generated method stub
		return (HttpServletRequest) super.getRequest();
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.context.ServletContext#getResponse()
	 */
	@Override
	public HttpServletResponse getResponse() {
		// TODO Auto-generated method stub
		return (HttpServletResponse) super.getResponse();
	}
	
	public HttpSession getSession() {
		return getRequest().getSession();
	}
	
	public HttpSession getSession(boolean create) {
		return getSession(create);
	}
}
