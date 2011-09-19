/**
 * 
 */
package com.neurologic.oauth.service.provider.oauth1.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.KeyValuePair;
import net.oauth.parameters.OAuthParameters;
import net.oauth.parameters.QueryKeyValuePair;
import net.oauth.token.oauth1.RequestToken;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.exception.OAuthRejectedException;
import com.neurologic.oauth.service.provider.manager.OAuth1TokenManager;
import com.neurologic.oauth.service.provider.oauth1.OAuth1TokenProviderService;
import com.neurologic.oauth.service.provider.response.DefaultOAuthResponseMessage;
import com.neurologic.oauth.service.provider.response.ExceptionResponseMessage;
import com.neurologic.oauth.service.provider.response.OAuthResponseMessage;

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
		OAuthResponseMessage oauthMessage = new DefaultOAuthResponseMessage();
		try {
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
				
			KeyValuePair kvp = new QueryKeyValuePair();
			kvp.add(OAuthParameters.OAUTH_TOKEN, requestToken.getToken());
			kvp.add(OAuthParameters.OAUTH_TOKEN_SECRET, requestToken.getTokenSecret());
			kvp.add(OAuthParameters.OAUTH_CALLBACK_CONFIRMED, String.valueOf(requestToken.isOauthCallbackConfirmed()));
			
			oauthMessage = new DefaultOAuthResponseMessage(kvp.toString());
			oauthMessage.setStatusCode(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Caught exception : ", e);
			oauthMessage = new ExceptionResponseMessage(e);
		}
		
		return oauthMessage;
	}
}
