/**
 * 
 */
package com.neurologic.oauth.service.provider.response;

/**
 * @author Buhake Sindi
 * @since 08 September 2011
 *
 */
public class DefaultOAuthResponseMessage extends AbstractOAuthResponseMessage {

	private String message;
	
	public DefaultOAuthResponseMessage() {
		super();
		setStatusCode(200);
		setContentType(CONTENT_TYPE_TEXT_PLAIN);
	}

	/**
	 * @param message
	 */
	public DefaultOAuthResponseMessage(String message) {
		this();
		this.message = message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.response.OAuthResponseMessage#getMessage()
	 */
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}
}
