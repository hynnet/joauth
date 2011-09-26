/**
 * 
 */
package com.neurologic.oauth.service.provider.oauth2.access;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.KeyValuePair;
import net.oauth.parameters.OAuthParameters;
import net.oauth.parameters.QueryKeyValuePair;
import net.oauth.token.oauth1.AccessToken;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.oauth.service.provider.manager.OAuth2TokenManager;
import com.neurologic.oauth.service.provider.oauth2.OAuth2TokenProviderService;
import com.neurologic.oauth.service.provider.response.PlainTextResponseMessage;
import com.neurologic.oauth.service.provider.response.ExceptionResponseMessage;
import com.neurologic.oauth.service.provider.response.OAuthResponseMessage;

/**
 * @author Buhake Sindi
 * @since 29 July 2011
 *
 */
public class OAuth2AccessTokenProviderService extends OAuth2TokenProviderService {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.AbstractOAuthProviderService#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected OAuthResponseMessage execute(HttpServletRequest request) throws OAuthException {
		// TODO Auto-generated method stub
		OAuthResponseMessage oauthMessage = null;
		String requestMethod = request.getMethod();
		if (!"POST".equals(requestMethod)) {
			throw new OAuthException("Cannot execute request with " + request.getMethod() + " HTTP method.");
		}
		
		String authorization = getOAuthAuthorizationParameters(request);
		if (authorization == null) {
			throw new OAuthAuthorizationException("HTTP " + HTTP_HEADER_AUTHORIZATION + " header required.");
		}
		
		String[] authSplit = authorization.split(":");
		String secretKey = authSplit[0];
		String secretPassword = authSplit[1];
		
		return oauthMessage;
	}
}
