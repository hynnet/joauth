/**
 * 
 */
package com.neurologic.oauth.service.provider.oauth1.authorize;

import javax.servlet.http.HttpServletRequest;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuthParameters;

import com.neurologic.exception.OAuthRejectedException;
import com.neurologic.exception.StoreException;
import com.neurologic.oauth.service.provider.OAuthRedirectProviderService;
import com.neurologic.oauth.service.provider.manager.OAuth1TokenManager;
import com.neurologic.oauth.service.provider.manager.store.data.oauth1.RequestTokenStoreData;
import com.neurologic.oauth.service.provider.response.DefaultRedirectResult;
import com.neurologic.oauth.service.provider.response.RedirectResult;
import com.neurologic.oauth.util.ModuleConfigUtil;

/**
 * @author Buhake Sindi
 * @since 15 September 2011
 *
 */
public class OAuth1AuthorizationRequestProviderService extends OAuthRedirectProviderService<OAuth1TokenManager> {

	
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
	 * @see com.neurologic.oauth.service.provider.OAuthRedirectProviderService#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected RedirectResult execute(HttpServletRequest request) throws OAuthException {
		// TODO Auto-generated method stub
		String redirectPath = "";
		String requestToken = request.getParameter(OAuthParameters.OAUTH_TOKEN);
		
		try {
			RequestTokenStoreData requestTokenData = getOauthTokenManager().getRequestTokenStore().find(requestToken);
			if (requestTokenData == null) {
				throw new OAuthRejectedException("Token seems invalid.");
			}
			
			if (requestTokenData.isAuthorized()) {
				throw new OAuthRejectedException("Token is authorized.");
			}
			
			redirectPath = ModuleConfigUtil.getLoginRedirectPath(request);
		} catch (StoreException e) {
			// TODO Auto-generated catch block
			logger.error("StoreException: " + e.getLocalizedMessage(), e);
			onException(e, request);
			redirectPath = ModuleConfigUtil.getErrorRedirectPath(request);
		}
		
		return new DefaultRedirectResult(redirectPath, true, true);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.OAuthRedirectProviderService#onException(java.lang.Exception, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void onException(Exception e, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}
}
