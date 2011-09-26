/**
 * 
 */
package com.neurologic.oauth.response;

import javax.servlet.http.HttpServletResponse;

import com.neurologic.oauth.response.parameters.OAuth1HttpAuthenticateParameters;

/**
 * @author Buhake Sindi
 *
 */
public class OAuth1HttpAuthenticateResponse implements HttpAuthenticateResponse<OAuth1HttpAuthenticateParameters> {
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.response.HttpAuthenticateResponse#apply(javax.servlet.http.HttpServletResponse, com.neurologic.oauth.response.OAuth1HttpAuthenticateParameters)
	 */
	@Override
	public void apply(HttpServletResponse response, OAuth1HttpAuthenticateParameters parameters) {
		// TODO Auto-generated method stub
		if (parameters == null) {
			throw new IllegalArgumentException("No parameters provided.");
		}
		
		response.setHeader(HTTP_AUTHENTICATE_HEADER, parameters.getChallenge() + " " + parameters);
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}
}
