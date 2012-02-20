/**
 * 
 */
package com.neurologic.oauth.service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neurologic.oauth.service.response.Result;

/**
 * @author Buhake Sindi
 * 
 *
 */
public abstract class HttpService extends AbstractService {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.Service#execute(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	public void execute(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Result result = execute((HttpServletRequest)request, (HttpServletResponse)response);
		if (result != null) {
			result.execute(request, response);
		}
	}
	
	protected Result execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result result = null;
		if ("GET".equals(request.getMethod())) {
			result = executeGet(request);
		} else if ("POST".equals(request.getMethod())) {
			result = executePost(request);
		} else {
			throw new Exception("Unregistered understand the HTTP Protocol method '" + request.getMethod() + ".");
		}
		
		return result;
	}
	
	protected abstract Result executeGet(HttpServletRequest request);
	protected abstract Result executePost(HttpServletRequest request);
}
