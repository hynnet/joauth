/**
 * 
 */
package com.neurologic.oauth.service.provider.oauth1.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuthParameters;
import net.oauth.token.oauth1.RequestToken;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.exception.OAuthRejectedException;
import com.neurologic.oauth.service.provider.manager.OAuth1TokenManager;
import com.neurologic.oauth.service.provider.oauth1.OAuth1TokenProviderService;
import com.neurologic.oauth.service.provider.response.OAuthResponseMessage;
import com.neurologic.oauth.service.provider.response.UrlEncodedFormResponseMessage;

/**
 * @author Buhake Sindi
 * @since 29 July 2011
 *
 */
public class OAuth1RequestTokenProviderService extends OAuth1TokenProviderService {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.AbstractOAuthProviderService#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected OAuthResponseMessage execute(HttpServletRequest request) throws OAuthException {
		// TODO Auto-generated method stub
		String requestMethod = request.getMethod();
		if (!"POST".equals(requestMethod)) {
			throw new OAuthException("Cannot execute request with " + request.getMethod() + " HTTP method.");
		}
		
		String requestTokenUrl = serviceProvider.getRequestTokenUrl();
		OAuth1TokenManager tokenManager = (OAuth1TokenManager) getOauthTokenManager();
		OAuthParameters parameters = getOAuthAuthorizationParameters(request);
		if (!tokenManager.validateOAuthHeaderParameters(requestMethod, requestTokenUrl, parameters)) {
			throw new OAuthAuthorizationException("Cannot verify oauth authorization: " + request.getHeader(HTTP_HEADER_AUTHORIZATION));	
		}
		
		RequestToken requestToken = tokenManager.createRequestToken(requestMethod, requestTokenUrl, parameters);
		if (requestToken == null) {
			throw new OAuthAuthorizationException(new OAuthRejectedException("Cannot create request token."));
		}
		
		OAuthResponseMessage oauthMessage = new UrlEncodedFormResponseMessage();
		
		((UrlEncodedFormResponseMessage)oauthMessage).put(OAuthParameters.OAUTH_TOKEN, requestToken.getToken());
		((UrlEncodedFormResponseMessage)oauthMessage).put(OAuthParameters.OAUTH_TOKEN_SECRET, requestToken.getTokenSecret());
		((UrlEncodedFormResponseMessage)oauthMessage).put(OAuthParameters.OAUTH_CALLBACK_CONFIRMED, String.valueOf(requestToken.isOauthCallbackConfirmed()));
		
		oauthMessage.setStatusCode(HttpServletResponse.SC_OK);
		return oauthMessage;
	}
}
