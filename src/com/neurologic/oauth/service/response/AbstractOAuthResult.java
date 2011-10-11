/**
 * 
 */
package com.neurologic.oauth.service.response;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import net.oauth.parameters.OAuthParameters;

/**
 * @author Buhake Sindi
 * @since 08 October 2011
 *
 */
public abstract class AbstractOAuthResult implements OAuthResult {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private Map<String, String> headers = new LinkedHashMap<String, String>();
	private OAuthParameters parameters;
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.OAuthResult#addHeader(java.lang.String, java.lang.String)
	 */
	@Override
	public void addHeader(String key, String value) {
		// TODO Auto-generated method stub
		headers.remove(key);
		headers.put(key, value);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.OAuthResult#setOAuthParameters(net.oauth.parameters.OAuthParameters)
	 */
	@Override
	public void setOAuthParameters(OAuthParameters parameters) {
		// TODO Auto-generated method stub
		this.parameters = parameters;
	}

	/**
	 * @return the parameters
	 */
	protected OAuthParameters getAuthParameters() {
		return parameters;
	}

	/**
	 * Returns a {@link Set} of header map entries.
	 * @return
	 */
	protected Set<Entry<String, String>> headersEntrySet() {
		return headers.entrySet();
	}
}
