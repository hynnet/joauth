/**
 * 
 */
package com.neurologic.oauth.service.provider.oauth2.access;

import java.util.Map;

import net.oauth.enums.GrantType;
import net.oauth.enums.OAuth2Error;
import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth2Parameters;
import net.oauth.parameters.OAuthParameters;
import net.oauth.token.oauth2.AccessToken;
import net.oauth.util.OAuth2Util;

import com.neurologic.oauth.service.provider.oauth2.OAuth2TokenProviderService;

/**
 * @author Buhake Sindi
 * @since 29 July 2011
 *
 */
public class OAuth2AccessTokenProviderService extends OAuth2TokenProviderService {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.oauth2.OAuth2TokenProviderService#executeInternal(java.util.Map, com.neurologic.oauth.service.provider.oauth2.OAuth2TokenProviderService.Credential)
	 */
	@Override
	protected OAuthParameters executeInternal(final Map<String, String> requestParameters, final Credential credential) {
		// TODO Auto-generated method stub
		String scope = requestParameters.get(OAuth2Parameters.SCOPE);
		String state = requestParameters.get(OAuth2Parameters.STATE);
		GrantType grantType = GrantType.Of(requestParameters.get(OAuth2Parameters.GRANT_TYPE));
		String code = requestParameters.get(OAuth2Parameters.CODE);
		String redirectUri = requestParameters.get(OAuth2Parameters.REDIRECT_URI);
		
		if (grantType == null) {
			return toError(OAuth2Error.INVALID_REQUEST, "No '" + OAuth2Parameters.GRANT_TYPE + "' provided.", null, state);
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("Grant Type received: " + grantType.toString());
		}
		
		if (grantType != GrantType.AUTHORIZATION_CODE) {
			return toError(OAuth2Error.INVALID_REQUEST, GrantType.AUTHORIZATION_CODE + " required.", null, state);
		}
		
		if (code == null || code.isEmpty()) {
			return toError(OAuth2Error.INVALID_REQUEST, OAuth2Parameters.CODE + " required.", null, state);
		}
		
		if (redirectUri == null || redirectUri.isEmpty()) {
			return toError(OAuth2Error.INVALID_REQUEST, OAuth2Parameters.REDIRECT_URI + " required.", null, state);
		}
		
		if (!OAuth2Util.isRedirectEndpointUriValid(redirectUri)) {
			return toError(OAuth2Error.INVALID_REQUEST, OAuth2Parameters.REDIRECT_URI + " isn't valid.", null, state);
		}
 		
		try {
			AccessToken accessToken = getOauthTokenManager().createAccessToken(credential.getClientId(), code, redirectUri, scope, state);
			OAuth2Parameters parameters = new OAuth2Parameters();
			parameters.setAccessToken(accessToken.getAccessToken());
			parameters.setTokenType(accessToken.getTokenType().toString());
			parameters.setExpiresIn(accessToken.getExpiresIn());
			parameters.setRefreshToken(accessToken.getRefreshToken());
			parameters.setScope(processScope(scope));
			
			return parameters;
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			logger.error(e.getLocalizedMessage(), e);
			return toError(OAuth2Error.UNAUTHORIZED_CLIENT, e.getLocalizedMessage(), null, state);
		}
	}
	
	protected String processScope(String scope) {
		//TODO: Override this method if you want to process scope.
		return scope;
	}
}
