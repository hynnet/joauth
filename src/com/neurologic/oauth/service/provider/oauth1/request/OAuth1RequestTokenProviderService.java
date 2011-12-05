/**
 * 
 */
package com.neurologic.oauth.service.provider.oauth1.request;

import javax.servlet.http.HttpServletRequest;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth1Parameters;
import net.oauth.token.oauth1.RequestToken;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.exception.OAuthRejectedException;
import com.neurologic.oauth.service.provider.oauth1.OAuth1TokenProviderService;

/**
 * @author Buhake Sindi
 * @since 29 July 2011
 *
 */
public class OAuth1RequestTokenProviderService extends OAuth1TokenProviderService {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.oauth1.OAuth1TokenProviderService#executePostInternal(javax.servlet.http.HttpServletRequest, net.oauth.parameters.OAuth1Parameters)
	 */
	@Override
	protected OAuth1Parameters executePostInternal(HttpServletRequest request, final OAuth1Parameters authorizationParameters) throws OAuthException {
		// TODO Auto-generated method stub
		if (!getOauthTokenManager().validateOAuthHeaderParameters(request.getMethod(), serviceProvider.getRequestTokenUrl(), authorizationParameters)) {
			throw new OAuthAuthorizationException("Cannot verify OAuth Authorization: " + request.getHeader(HTTP_HEADER_AUTHORIZATION));	
		}
		
		//Just in case....
		String token = authorizationParameters.getOAuthToken();
		if (token != null && !token.isEmpty()) {
			logger.error("Received " + OAuth1Parameters.OAUTH_TOKEN + " \"" + token +"\" on request token generation call.");
			throw new OAuthRejectedException("Invalid OAuth Authorization header: " + OAuth1Parameters.OAUTH_TOKEN + " value is set.");
		}
		
		RequestToken requestToken = getOauthTokenManager().createRequestToken(authorizationParameters.getOAuthConsumerKey(), authorizationParameters.getOAuthCallback());
		if (requestToken == null) {
			throw new OAuthAuthorizationException("Cannot create request token.");
		}
		
		OAuth1Parameters parameters = new OAuth1Parameters();
		parameters.setOAuthToken(requestToken.getToken());
		parameters.setOAuthTokenSecret(requestToken.getTokenSecret());
		parameters.setOAuthCallbackConfirmed(true);
		return parameters;
	}
}
