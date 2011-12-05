/**
 * 
 */
package com.neurologic.oauth.service.response;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author Buhake Sindi
 * @since 08 October 2011
 *
 */
public abstract class AbstractOAuthResult implements OAuthResult {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private Map<String, String> headers = new LinkedHashMap<String, String>();
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.OAuthResult#addHeader(java.lang.String, java.lang.String)
	 */
	@Override
	public void addHeader(String key, String value) {
		// TODO Auto-generated method stub
		headers.put(key, value);
	}

	/**
	 * Returns a {@link Set} of header map entries.
	 * @return
	 */
	protected Set<Entry<String, String>> headersEntrySet() {
		return headers.entrySet();
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.Result#execute(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	public void execute(ServletRequest request, ServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		execute((HttpServletRequest)request, (HttpServletResponse)response);
	}
	
	protected abstract void execute(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
