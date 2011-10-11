/**
 * 
 */
package com.neurologic.oauth.service.response.formatter;


/**
 * @author Buhake Sindi
 * @since 10 October 2011
 *
 */
public abstract class AbstractParameterFormatter implements ParameterFormatter {

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
