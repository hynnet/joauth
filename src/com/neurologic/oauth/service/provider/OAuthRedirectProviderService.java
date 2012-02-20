/**
 * 
 */
package com.neurologic.oauth.service.provider;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neurologic.oauth.config.ErrorRedirectConfig;
import com.neurologic.oauth.config.LoginRedirectConfig;
import com.neurologic.oauth.config.ModuleConfig;
import com.neurologic.oauth.config.ServiceConfig;
import com.neurologic.oauth.service.provider.manager.OAuthTokenManager;
import com.neurologic.oauth.service.response.OAuthResult;
import com.neurologic.oauth.service.response.impl.OAuthMessageResult;
import com.neurologic.oauth.service.response.impl.StringMessage;
import com.neurologic.oauth.util.Globals;

/**
 * @author Buhake Sindi
 * @since 17 September 2011
 *
 */
public abstract class OAuthRedirectProviderService<TM extends OAuthTokenManager> extends AbstractOAuthProviderService<TM> {
	
	protected String getLoginRedirectPath(HttpServletRequest request) {
		
		ModuleConfig moduleConfig = (ModuleConfig) request.getServletContext().getAttribute(Globals.MODULE_KEY);
		ServiceConfig serviceConfig = moduleConfig.getServiceConfigByPath(request.getPathInfo());
		LoginRedirectConfig loginRedirectConfig = serviceConfig.getLoginRedirectConfig();
		return loginRedirectConfig.getPath();
	}
	
	protected String getErrorRedirectPath(HttpServletRequest request) {
		
		ModuleConfig moduleConfig = (ModuleConfig) request.getServletContext().getAttribute(Globals.MODULE_KEY);
		ServiceConfig serviceConfig = moduleConfig.getServiceConfigByPath(request.getPathInfo());
		ErrorRedirectConfig errorRedirectConfig = serviceConfig.getErrorRedirectConfig();
		return errorRedirectConfig.getPath();	
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.AbstractOAuthProviderService#executePost(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected OAuthResult executePost(HttpServletRequest request) {
		// TODO Auto-generated method stub
//		throw new UnsupportedOperationException("\"" + request.getMethod() + "\" method not support for OAuth Redirect call.");
		try {
			return new OAuthMessageResult(HttpServletResponse.SC_METHOD_NOT_ALLOWED, new StringMessage(request.getMethod() + " not allowed in this method call.", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return null;
	}
}
