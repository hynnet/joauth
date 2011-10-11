/**
 * 
 */
package com.neurologic.oauth.service.provider.oauth2.authorize;

import javax.servlet.http.HttpServletRequest;

import net.oauth.enums.ResponseType;
import net.oauth.parameters.OAuth2Parameters;

import com.neurologic.oauth.service.provider.OAuthRedirectProviderService;
import com.neurologic.oauth.service.provider.manager.OAuth2TokenManager;
import com.neurologic.oauth.service.response.Result;
import com.neurologic.oauth.util.ExceptionUtil;

/**
 * @author Buhake Sindi
 * @since 19 September 2011
 *
 */
public class OAuth2AuthorizeRequestProviderService extends OAuthRedirectProviderService<OAuth2TokenManager> {

	private String errorMessage = "";
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.OAuthRedirectProviderService#execute(javax.servlet.http.HttpServletRequest)
	 */
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.AbstractOAuthProviderService#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Result execute(HttpServletRequest request) {
		// TODO Auto-generated method stub
		ResponseType responseType = ResponseType.valueOf(request.getParameter(OAuth2Parameters.RESPONSE_TYPE));
		String clientId = request.getParameter(OAuth2Parameters.CLIENT_ID);
		String redirectUri = request.getParameter(OAuth2Parameters.REDIRECT_URI);
		String scope = request.getParameter(OAuth2Parameters.SCOPE);
		String state = request.getParameter(OAuth2Parameters.STATE);
		
		try {
			ExceptionUtil.throwIfNull(responseType, OAuth2Parameters.RESPONSE_TYPE + " is not provided.");
			ExceptionUtil.throwIfNull(clientId, OAuth2Parameters.CLIENT_ID + " is required.");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			logger.error("NullPointerException: " + e.getLocalizedMessage());
		}
		
		return null;
	}
}
