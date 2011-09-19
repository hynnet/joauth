/**
 * 
 */
package com.neurologic.oauth.service.provider.response;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Buhake Sindi
 * @since 08 September 2011
 *
 */
public class ExceptionResponseMessage extends AbstractOAuthResponseMessage {

	private Exception exception;
	
	public ExceptionResponseMessage(Exception exception) {
		this(HttpServletResponse.SC_NOT_FOUND, exception);
	}
	
	public ExceptionResponseMessage(int statusCode, Exception exception) {
		super();
		setStatusCode(statusCode);
		setContentType(CONTENT_TYPE_TEXT_PLAIN);
		this.exception = exception;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.response.OAuthResponseMessage#getMessage()
	 */
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		if (exception == null) {
			return null;
		}
		
		return exception.toString();
	}
}
