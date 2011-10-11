/**
 * 
 */
package com.neurologic.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author Buhake Sindi
 * @since 15 August 2011
 *
 */
public class OAuth2ProtectedResourceFilter extends OAuthProtectedResourceFilter {
	
	protected final Logger logger = Logger.getLogger(OAuth2ProtectedResourceFilter.class);
	private String authScheme;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		super.init(filterConfig);
		authScheme = filterConfig.getInitParameter("auth-scheme");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	/* (non-Javadoc)
	 * @see com.neurologic.filter.OAuthProtectedResourceFilter#doFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}
}
