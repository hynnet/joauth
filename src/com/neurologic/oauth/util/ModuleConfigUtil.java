/**
 * 
 */
package com.neurologic.oauth.util;

import javax.servlet.http.HttpServletRequest;

import com.neurologic.oauth.config.ErrorRedirectConfig;
import com.neurologic.oauth.config.LoginRedirectConfig;
import com.neurologic.oauth.config.ModuleConfig;
import com.neurologic.oauth.config.ServiceConfig;

/**
 * @author Buhake Sindi
 * @since 20 September 2011
 *
 */
public class ModuleConfigUtil {
	
	private ModuleConfigUtil() {}

	public static String getLoginRedirectPath(HttpServletRequest request) {
		
		ModuleConfig moduleConfig = (ModuleConfig) request.getServletContext().getAttribute(Globals.MODULE_KEY);
		ServiceConfig serviceConfig = moduleConfig.getServiceConfigByPath(request.getPathInfo());
		LoginRedirectConfig loginRedirectConfig = serviceConfig.getLoginRedirectConfig();
		return loginRedirectConfig.getPath();
	}
	
	public static String getErrorRedirectPath(HttpServletRequest request) {
		
		ModuleConfig moduleConfig = (ModuleConfig) request.getServletContext().getAttribute(Globals.MODULE_KEY);
		ServiceConfig serviceConfig = moduleConfig.getServiceConfigByPath(request.getPathInfo());
		ErrorRedirectConfig errorRedirectConfig = serviceConfig.getErrorRedirectConfig();
		return errorRedirectConfig.getPath();	
	}
}
