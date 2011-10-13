/**
 * 
 */
package com.neurologic.oauth.service.response.formatter;

import java.util.Map.Entry;

import net.oauth.parameters.OAuthParameters;

/**
 * @author Buhake Sindi
 * @since 13 October 2011
 *
 */
public class NoFormatParameterFormatter extends AbstractParameterFormatter {

	/**
	 * @param contentType
	 */
	public NoFormatParameterFormatter(String contentType) {
		super(contentType);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.formatter.ParameterFormatter#format(net.oauth.parameters.OAuthParameters, java.lang.String)
	 */
	@Override
	public String format(OAuthParameters parameters, String charset) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> entry : parameters.getOAuthParameters().entrySet()) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			
			sb.append(entry.getKey()).append("=")
			  .append(entry.getValue());
		}

		return sb.toString();
	}
}
