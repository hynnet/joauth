/**
 * 
 */
package com.neurologic.oauth.service.response.formatter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map.Entry;

import net.oauth.parameters.OAuthParameters;

import org.apache.log4j.Logger;

/**
 * @author Buhake Sindi
 * @since 06 October 2011
 *
 */
public class UrlEncodedParameterFormatter extends AbstractParameterFormatter {
	
	private static final Logger logger = Logger.getLogger(UrlEncodedParameterFormatter.class);
	private String charset;
	
	/**
	 * 
	 */
	public UrlEncodedParameterFormatter() {
		this("UTF-8");
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param charset
	 */
	public UrlEncodedParameterFormatter(String charset) {
		super("application/x-www-form-urlencoded");
		this.charset = charset;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.formatter.ParameterFormatter#format(net.oauth.parameters.OAuthParameters)
	 */
	@Override
	public String format(OAuthParameters parameters) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		try {
			for (Entry<String, String> entry : parameters.getOAuthParameters().entrySet()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				
				sb.append(URLEncoder.encode(entry.getKey(), charset)).append("=")
				  .append(URLEncoder.encode(entry.getValue(), charset));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("UnsupportedEncodingException: " + e.getLocalizedMessage(), e);
		}

		return sb.toString();
	}
}
