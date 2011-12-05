/**
 * 
 */
package com.neurologic.oauth.service.response.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.neurologic.oauth.service.response.AbstractOAuthResult;

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
	private Map<String, String[]> parameterMap;
	
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
	
	public OAuthRedirectResult addParameter(String key, String value) {
		String[] values = null;
		if (parameterMap == null) {
			parameterMap = new LinkedHashMap<String, String[]>();
		}
		
		if (parameterMap.containsKey(key)) {
			values = parameterMap.get(key);
			
			List<String> valueList = new ArrayList<String>(Arrays.asList(values));
			valueList.add(value);
			values = valueList.toArray(new String[values.length]);
		} else {
			values = new String[] {value};
		}
		
		if (values != null) {
			parameterMap.put(key, values);
		}
		
		return this;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.Result#execute(javax.servlet.HttpServletRequest, javax.servlet.HttpServletResponse)
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int parameterStartPos = location.indexOf('?', 1);
		StringBuilder sb = new StringBuilder(location);
		if (parameterStartPos == -1) {
			sb.append("?");
		} else {
			sb.append("&");
		}
		sb.append(getParameterString());
		
		location = sb.toString();
		if (location.startsWith("/") && contextRelative) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(location);
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				logger.error("ServletException:", e);
				throw new IOException("ServletException", e);
			}
		} else {
//			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			
			if (http10Compatible) {
				//We must do a HTTP 302 Found.
				response.sendRedirect(response.encodeRedirectURL(location));
			} else {
				response.setStatus(HttpServletResponse.SC_SEE_OTHER); //HTTP 303
				response.setHeader(HTTP_LOCATION_HEADER, response.encodeRedirectURL(location));
			}
		}
	}
	
	private String getParameterString() {
		StringBuffer sb = new StringBuffer(location);
		
		if (parameterMap != null) {
			for (Entry<String, String[]> entry : parameterMap.entrySet()) {
				for (String value:  entry.getValue()) {
					if (sb.length() > 0) {
						sb.append("&");
					}
					
					sb.append(entry.getKey()).append("=").append(value);
					
				}
			}
		}
		
		return sb.toString();
	}
}
