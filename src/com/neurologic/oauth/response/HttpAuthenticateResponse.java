/**
 * 
 */
package com.neurologic.oauth.response;

import javax.servlet.http.HttpServletResponse;

import com.neurologic.oauth.response.parameters.HttpAuthenticateParameters;

/**
 * @author Buhake Sindi
 * @since 25 September 2011
 *
 */
public interface HttpAuthenticateResponse<T extends HttpAuthenticateParameters> {

	public static final String HTTP_AUTHENTICATE_HEADER = "WWW-Authenticate";
	public void apply(HttpServletResponse response, T parameters);
}
