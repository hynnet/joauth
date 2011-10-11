/**
 * 
 */
package com.neurologic.oauth.service.response;

import net.oauth.parameters.OAuthParameters;

/**
 * @author Buhake Sindi
 * @since 05 October 2011
 *
 */
public interface OAuthResult extends Result {

	public void addHeader(String key, String value);
	public void setOAuthParameters(OAuthParameters parameters);
}
