/**
 * 
 */
package com.neurologic.oauth.service.provider.oauth1.authorize;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth1Parameters;
import net.oauth.parameters.OAuthErrorParameter;
import net.oauth.parameters.OAuthParameters;
import net.oauth.parameters.QueryKeyValuePair;
import net.oauth.token.oauth1.AuthorizedToken;
import net.oauth.util.OAuth1Util;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.exception.OAuthRejectedException;
import com.neurologic.exception.StoreException;
import com.neurologic.oauth.service.provider.OAuthRedirectProviderService;
import com.neurologic.oauth.service.provider.manager.OAuth1TokenManager;
import com.neurologic.oauth.service.provider.manager.store.data.oauth1.RequestTokenStoreData;
import com.neurologic.oauth.service.response.Result;
import com.neurologic.oauth.service.response.impl.OAuthRedirectResult;
import com.neurologic.oauth.util.Globals;

/**
 * @author Buhake Sindi
 * @since 15 September 2011
 *
 */
public class OAuth1AuthorizationRequestProviderService extends OAuthRedirectProviderService<OAuth1TokenManager> {

	private static final String OAUTH_AUTHORIZATION_CONFIRMED = "oauth_authorization_confirmed";
	
	protected String getAuthorizedTokenCallbackUrl(String token) {
		String oauthCallback = null;
		try {
			RequestTokenStoreData requestTokenData = getOauthTokenManager().getRequestTokenStore().find(token);
			if (requestTokenData != null && !requestTokenData.isAuthorized()) {
				oauthCallback = requestTokenData.getCallbackUrl();
			}
		} catch (StoreException e) {
			// TODO Auto-generated catch block
			logger.error("Error getting oauth_callback based on token '" + token + "'.", e);
		}
		
		return oauthCallback;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.AbstractOAuthProviderService#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Result execute(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String redirectPath = "";
		OAuthParameters parameters = null;
		
		try {
			String requestToken = request.getParameter(OAuth1Parameters.OAUTH_TOKEN);
			String oauthAuthorizationConfirmed = request.getParameter(OAUTH_AUTHORIZATION_CONFIRMED);
			
			if (requestToken == null || requestToken.isEmpty()) {
				throw new OAuthException("No '" + OAuth1Parameters.OAUTH_TOKEN + "' provided.");
			}
			
			if (oauthAuthorizationConfirmed == null) {
				RequestTokenStoreData requestTokenData = getOauthTokenManager().getRequestTokenStore().find(requestToken);
				if (requestTokenData == null) {
					throw new OAuthRejectedException("Token seems invalid.");
				}
				
				if (requestTokenData.isAuthorized()) {
					throw new OAuthRejectedException("Token is authorized.");
				}
				
				redirectPath = getLoginRedirectPath(request);
			} else {
				String userId = (String) request.getSession().getAttribute(Globals.AUTHORIZATION_USER_ID);
				if (userId == null || userId.isEmpty()) {
					logger.error("No user ID provided");
					throw new OAuthAuthorizationException("No authorization confirmed.");
				}
				
				parameters = authorizeToken(requestToken, userId);
				redirectPath = getAuthorizedTokenCallbackUrl(requestToken);
			}
		} catch (StoreException e) {
			// TODO Auto-generated catch block
			logger.error("StoreException: " + e.getLocalizedMessage(), e);
			parameters = packException(e);
			redirectPath = getErrorRedirectPath(request);
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			logger.error("StoreException: " + e.getLocalizedMessage(), e);
			parameters = packException(e);
			redirectPath = getErrorRedirectPath(request);
		}
		
		if ("oob".startsWith(redirectPath)) {
			return null;
		}
		
		boolean contextRelative = false;
		if (redirectPath.startsWith("/")) {
			contextRelative = true;
		}
		
		int questionMarkPos = redirectPath.indexOf('?');
		Map<String, String> parameterMap = new LinkedHashMap<String, String>();
		if (questionMarkPos > 0) {
			parameterMap.putAll(OAuth1Util.parseQueryString(redirectPath.substring(questionMarkPos + 1)));
		}
		
		if (parameters != null) {
			parameterMap.putAll(parameters.getOAuthParameters());
		}
		
		if (questionMarkPos > 1) {
			redirectPath = redirectPath.substring(0, questionMarkPos + 1) + OAuth1Util.getQueryString(parameterMap, new QueryKeyValuePair());
		}
		if (logger.isInfoEnabled()) {
			logger.info(this.getClass().getName() + ": Redirecting to '" + redirectPath + "'.");
		}
		
		return new OAuthRedirectResult(redirectPath, contextRelative);
	}
	
	private OAuth1Parameters authorizeToken(String requestToken, String userId) throws OAuthException {
		AuthorizedToken authorizedToken = getOauthTokenManager().authorizeRequestToken(requestToken, userId);
		if (authorizedToken == null) {
			return null;
		}
		
		OAuth1Parameters parameters = new OAuth1Parameters();
		parameters.setOAuthToken(authorizedToken.getToken());
		parameters.setOAuthVerifier(authorizedToken.getVerifier());
		return parameters;
	}
	
	private OAuthErrorParameter packException(Exception exception) {
		if (exception == null) {
			return null;
		}
		
		OAuthErrorParameter errorParameter = new OAuthErrorParameter();
		errorParameter.setError(exception.getClass().getName() + ": " + exception.getLocalizedMessage());
		return errorParameter;
	}
}
