/**
 * 
 */
package com.neurologic.oauth.service.provider.response;

/**
 * @author Buhake Sindi
 * @since 08 September 2011
 *
 */
public interface OAuthResponseMessage {

	public void setCacheControl(String cacheControl);
	public String getCacheControl();
	public int getStatusCode();
	public void setStatusCode(int statusCode);
	public void setContentType(String contentType);
	public String getContentType();
	public int getContentLength();
//	public void setMessage(String message);
	public String getMessage();
}
