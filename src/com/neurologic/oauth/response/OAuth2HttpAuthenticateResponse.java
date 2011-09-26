/**
 * 
 */
package com.neurologic.oauth.response;

import javax.servlet.http.HttpServletResponse;

import net.oauth.enums.TokenType;

import com.neurologic.oauth.response.parameters.ErrorCode;
import com.neurologic.oauth.response.parameters.OAuth2HttpAuthenticateParameters;

/**
 * @author Buhake Sindi
 *
 */
public class OAuth2HttpAuthenticateResponse implements HttpAuthenticateResponse<OAuth2HttpAuthenticateParameters> {
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.response.HttpAuthenticateResponse#apply(javax.servlet.http.HttpServletResponse, com.neurologic.oauth.response.OAuth2HttpAuthenticateParameters)
	 */
	@Override
	public void apply(HttpServletResponse response, OAuth2HttpAuthenticateParameters parameters) {
		// TODO Auto-generated method stub
		if (parameters == null) {
			throw new IllegalArgumentException("No parameters provided.");
		}
		
		response.setHeader(HTTP_AUTHENTICATE_HEADER, parameters.getChallenge() + " " + parameters);
		int statusCode = HttpServletResponse.SC_NOT_FOUND;
		
		if (TokenType.BEARER.toString().equals(parameters.getChallenge())) {
			//It is a Bearer, so get the relevant status code....
			ErrorCode errorCode = ErrorCode.of(parameters.getError());
			if (errorCode != null) {
				statusCode = errorCode.getStatusCode();
			}
		}
		
		response.setStatus(statusCode);
	}
}
