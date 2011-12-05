/**
 * 
 */
package com.neurologic.oauth.service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neurologic.oauth.service.response.OAuthResult;

/**
 * @author Buhake Sindi
 * @since 18 November 2011
 *
 */
public abstract class AbstractOAuthHttpService extends AbstractOAuthService {

//	/**
//	 * Checks if the Servlet Request was done through a secure channel.
//	 * @param request
//	 * @param forceCheck if <code>true</code>, then checks if https scheme and X509 certificates exists in the 
//	 *        {@link ServletRequest}.
//	 * @return
//	 */
//	protected boolean isSecure(ServletRequest request, boolean forceCheck) {
//		if (!forceCheck) {
//			return request.isSecure();
//		}
//		
//		X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
////		X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.net.ssl.peer_certificates");
//		return ("https".equals(request.getScheme()) && certs != null);
//	}
//	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.Service#execute(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	public void execute(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		OAuthResult result = execute((HttpServletRequest)request, (HttpServletResponse)response);
		if (result != null) {
			result.execute(request, response);
		}
	}
	
	protected OAuthResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OAuthResult result = null;
		if ("GET".equals(request.getMethod())) {
			result = executeGet(request);
		} else if ("POST".equals(request.getMethod())) {
			result = executePost(request);
		} else {
			throw new Exception("OAuth doesn't understand the HTTP Protocol method '" + request.getMethod() + ".");
		}
		
		return result;
	}
	
	protected abstract OAuthResult executeGet(HttpServletRequest request);
	protected abstract OAuthResult executePost(HttpServletRequest request);
}
