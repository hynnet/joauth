/**
 * 
 */
package com.neurologic.oauth.service.provider;

import javax.servlet.http.HttpServletRequest;

import com.neurologic.oauth.config.ErrorRedirectConfig;
import com.neurologic.oauth.config.LoginRedirectConfig;
import com.neurologic.oauth.config.ModuleConfig;
import com.neurologic.oauth.config.ServiceConfig;
import com.neurologic.oauth.service.provider.manager.OAuthTokenManager;
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
}
