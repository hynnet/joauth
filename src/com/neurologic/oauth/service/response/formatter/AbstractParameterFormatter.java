/**
 * 
 */
package com.neurologic.oauth.service.response.formatter;

import org.apache.log4j.Logger;


/**
 * @author Buhake Sindi
 * @since 10 October 2011
 *
 */
public abstract class AbstractParameterFormatter implements ParameterFormatter {

	protected final Logger logger = Logger.getLogger(UrlEncodedParameterFormatter.class);
	private String contentType;
	
	/**
	 * @param contentType
	 */
	protected AbstractParameterFormatter(String contentType) {
		super();
		this.contentType = contentType;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.formatter.ParameterFormatter#getContentType()
	 */
	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return contentType;
	}
}
