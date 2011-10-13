/**
 * 
 */
package com.neurologic.oauth.service.response.formatter;

import net.oauth.parameters.OAuthParameters;

/**
 * @author Buhake Sindi
 * @since 06 October 2011
 *
 */
public interface ParameterFormatter {

	public String getContentType();
	public String format(OAuthParameters parameters, String charset);
}
