/**
 * 
 */
package com.neurologic.oauth.service.provider.v1.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.exception.OAuthException;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.oauth.service.provider.v1.OAuth1ProviderService;

/**
 * @author Buhake Sindi
 * @since 29 July 2011
 *
 */
public class OAuth1RequestTokenProviderService extends OAuth1ProviderService {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.OAuthService#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws OAuthException {
		// TODO Auto-generated method stub
		if (!"POST".equals(request.getMethod())) {
			throw new OAuthException("Cannot execute request with " + request.getMethod() + " HTTP method.");
		}
		
		if (!verifyOAuthAuthorizationHeader(request)) {
			throw new OAuthAuthorizationException("Cannot verify oauth authorization: " + request.getHeader(HTTP_HEADER_AUTHORIZATION));
		}
	}
}
