/**
 * 
 */
package com.neurologic.oauth.service.provider.response;


/**
 * @author Buhake Sindi
 * @since 08 September 2011
 *
 */
public abstract class AbstractOAuthResponseMessage implements OAuthResponseMessage {

	protected static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
	private String cacheControl;
	private int statusCode;
	private String contentType;
	
	/**
	 * 
	 */
	public AbstractOAuthResponseMessage() {
		super();
		// TODO Auto-generated constructor stub
		setCacheControl("no-cache");
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.response.OAuthResponseMessage#setCacheControl(java.lang.String)
	 */
	@Override
	public void setCacheControl(String cacheControl) {
		// TODO Auto-generated method stub
		this.cacheControl = cacheControl;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.response.OAuthResponseMessage#getCacheControl()
	 */
	@Override
	public String getCacheControl() {
		// TODO Auto-generated method stub
		return cacheControl;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.servlet.OAuthResponseMessage#getStatusCode()
	 */
	@Override
	public int getStatusCode() {
		// TODO Auto-generated method stub
		return statusCode;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.servlet.OAuthResponseMessage#setStatusCode(int)
	 */
	@Override
	public void setStatusCode(int statusCode) {
		// TODO Auto-generated method stub
		this.statusCode = statusCode;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.servlet.OAuthResponseMessage#setContentType(java.lang.String)
	 */
	@Override
	public void setContentType(String contentType) {
		// TODO Auto-generated method stub
		this.contentType = contentType;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.servlet.OAuthResponseMessage#getContentType()
	 */
	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return contentType;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.servlet.OAuthResponseMessage#getContentLength()
	 */
	@Override
	public int getContentLength() {
		// TODO Auto-generated method stub
		return (getMessage() == null ? 0 : getMessage().length());
	}
}
