/**
 * 
 */
package com.neurologic.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author Buhake Sindi
 * @since 15 August 2011
 *
 */
public abstract class OAuthProtectedResourceFilter implements Filter {
	
	protected static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
	protected final Logger logger = Logger.getLogger(OAuthProtectedResourceFilter.class);
	private String realm;
	private String oauthName;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		if (logger.isInfoEnabled()) {
			logger.info("Initializing...");
		}
		
		realm = filterConfig.getInitParameter("realm");
		oauthName = filterConfig.getInitParameter("refOAuth");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		if (logger.isInfoEnabled()) {
			logger.info("Destroying...");
		}
	}

	/**
	 * @return the realm
	 */
	public String getRealm() {
		return realm;
	}

	/**
	 * @return the oauthName
	 */
	public String getOauthName() {
		return oauthName;
	}
	

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		doFilter((HttpServletRequest)request, (HttpServletResponse)response, filterChain);
	}

	protected abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException;
}
