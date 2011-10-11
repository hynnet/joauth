/**
 * 
 */
package com.neurologic.oauth.service.response.authenticate;


/**
 * @author Buhake Sindi
 * @since 25 September 2011
 *
 */
public interface WWWAuthenticateResponse {

	public static final String HTTP_AUTHENTICATE_HEADER = "WWW-Authenticate";
	public String getChallenge();
	public String getValue();
}
