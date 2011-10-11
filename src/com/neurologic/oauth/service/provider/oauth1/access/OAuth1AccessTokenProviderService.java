/**
 * 
 */
package com.neurologic.oauth.service.provider.oauth1.access;

import javax.servlet.http.HttpServletRequest;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth1Parameters;
import net.oauth.token.oauth1.AccessToken;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.exception.OAuthRejectedException;
import com.neurologic.oauth.service.provider.oauth1.OAuth1TokenProviderService;

/**
 * @author Buhake Sindi
 * @since 29 July 2011
 *
 */
public class OAuth1AccessTokenProviderService extends OAuth1TokenProviderService {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.oauth1.OAuth1TokenProviderService#executeInternal(javax.servlet.http.HttpServletRequest, net.oauth.parameters.OAuth1Parameters)
	 */
	@Override
	protected OAuth1Parameters executeInternal(HttpServletRequest request, final OAuth1Parameters authorizationParameters) throws OAuthException {
		// TODO Auto-generated method stub
		if (!getOauthTokenManager().validateOAuthHeaderParameters(request.getMethod(), serviceProvider.getAccessTokenUrl(), authorizationParameters)) {
			throw new OAuthAuthorizationException("Cannot verify oauth authorization: " + request.getHeader(HTTP_HEADER_AUTHORIZATION));
		}
		
		AccessToken accessToken = getOauthTokenManager().createAccessToken(authorizationParameters.getOAuthToken(), authorizationParameters.getOAuthVerifier(), authorizationParameters.getOAuthConsumerKey());
		if (accessToken == null) {
			throw new OAuthRejectedException("Cannot create access token.");
		}
		
		OAuth1Parameters parameters = new OAuth1Parameters();
		parameters.setOAuthToken(accessToken.getToken());
		parameters.setOAuthTokenSecret(accessToken.getTokenSecret());
		return parameters;
	}
}
