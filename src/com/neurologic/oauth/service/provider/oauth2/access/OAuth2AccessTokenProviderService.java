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

import com.neurologic.oauth.service.provider.manager.OAuth2TokenManager;
import com.neurologic.oauth.service.provider.oauth2.OAuth2TokenProviderService;
import com.neurologic.oauth.service.provider.response.DefaultOAuthResponseMessage;
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
		try {
			String requestMethod = request.getMethod();
			if (!"POST".equals(requestMethod)) {
				throw new OAuthException("Cannot execute request with " + request.getMethod() + " HTTP method.");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Caught exception : ", e);
			oauthMessage = new ExceptionResponseMessage(e);
		}
		
		return oauthMessage;
	}
}
