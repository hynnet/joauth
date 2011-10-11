/**
 * 
 */
package com.neurologic.oauth.service.response.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.neurologic.oauth.service.response.AbstractOAuthResult;
import com.neurologic.oauth.service.response.ServiceContext;

/**
 * @author Buhake Sindi
 * @since 19 September 2011
 *
 */
public class OAuthRedirectResult extends AbstractOAuthResult {

	private static final Logger logger = Logger.getLogger(OAuthRedirectResult.class);
	private static final String HTTP_LOCATION_HEADER = "Location";
	private String location;
	private boolean contextRelative;
	private boolean http10Compatible;
	
	/**
	 * Default constructor (in case of <code>OAuthRedirectResult.newInstance()</code>).
	 */
	public OAuthRedirectResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param location
	 */
	public OAuthRedirectResult(String location) {
		this(location, false);
	}

	/**
	 * @param location
	 * @param contextRelative
	 */
	public OAuthRedirectResult(String location, boolean contextRelative) {
		this(location, contextRelative, false);
	}

	/**
	 * @param location
	 * @param contextRelative
	 * @param http10Compatible
	 */
	public OAuthRedirectResult(String location, boolean contextRelative, boolean http10Compatible) {
		super();
		setLocation(location);
		setContextRelative(contextRelative);
		setHttp10Compatible(http10Compatible);
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param contextRelative the contextRelative to set
	 */
	public void setContextRelative(boolean contextRelative) {
		this.contextRelative = contextRelative;
	}

	/**
	 * @param http10Compatible the http10Compatible to set
	 */
	public void setHttp10Compatible(boolean http10Compatible) {
		this.http10Compatible = http10Compatible;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.OAuthResult#execute(com.neurologic.oauth.service.response.ServiceContext)
	 */
	@Override
	public void execute(ServiceContext context) throws IOException {
		// TODO Auto-generated method stub
		if (context == null) {
			throw new IOException("No Service context provided.");
		}
		
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		
		if (location.startsWith("/") && contextRelative) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(location);
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				logger.error("ServletException: ", e);
				throw new IOException("ServletException", e);
			}
		} else {
			if (http10Compatible) {
				//We must do a HTTP 302 Found.
				response.sendRedirect(response.encodeRedirectURL(location));
			} else {
				response.setStatus(HttpServletResponse.SC_SEE_OTHER); //HTTP 303
				response.setHeader(HTTP_LOCATION_HEADER, response.encodeRedirectURL(location));
			}
		}
	}
}
