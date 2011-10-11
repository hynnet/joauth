/**
 * 
 */
package com.neurologic.oauth.service;

import javax.security.cert.X509Certificate;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Buhake Sindi
 * @since 24 September 2011
 *
 */
public abstract class AbstractOAuthService implements OAuthService {

	private String oauthName;
	private boolean nameSet;
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.OAuthService#setOAuthName(java.lang.String)
	 */
	@Override
	public void setOAuthName(String oauthName) {
		// TODO Auto-generated method stub
		if (nameSet) {
			throw new IllegalArgumentException("OAuth name has been set.");
		}
		
		this.oauthName = oauthName;
		nameSet = true;
	}

	/**
	 * @return the oauthName
	 */
	protected String getOauthName() {
		return oauthName;
	}
	
	/**
	 * Checks if the Servlet Request was done through a secure channel.
	 * @param request
	 * @param forceCheck if <code>true</code>, then checks if https scheme and X509 certificates exists in the 
	 *        {@link HttpServletRequest}.
	 * @return
	 */
	protected boolean isSecure(HttpServletRequest request, boolean forceCheck) {
		if (!forceCheck) {
			return request.isSecure();
		}
		
		X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
		return ("https".equals(request.getScheme()) && certs != null);
	}
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.Service#execute(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	public final void execute(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		execute((HttpServletRequest)request, (HttpServletResponse)response);
	}

	protected abstract void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
