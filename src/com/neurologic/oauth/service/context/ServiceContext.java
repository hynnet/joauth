/**
 * 
 */
package com.neurologic.oauth.service.context;

import com.neurologic.oauth.config.ModuleConfig;
import com.neurologic.oauth.util.Globals;

/**
 * @author Buhake Sindi
 * @since 30 November 2011
 *
 */
public class ServiceContext implements Context {

	private ServletContext context;
	private ModuleConfig moduleConfig;
	
	/**
	 * @param context
	 * @param moduleConfig
	 */
	public ServiceContext(ServletContext context, ModuleConfig moduleConfig) {
		super();
		
		if (context == null) {
			throw new IllegalArgumentException("ServletContext is required.");
		}
		
		this.context = context;
		this.moduleConfig = moduleConfig;
	}

	/**
	 * @return the context
	 */
	public ServletContext getContext() {
		return context;
	}

	/**
	 * @return the moduleConfig
	 */
	public ModuleConfig getModuleConfig() {
		if (moduleConfig == null) {
			moduleConfig = (ModuleConfig) context.getContext().getAttribute(Globals.MODULE_KEY);
		}
		
		return moduleConfig;
	}
}
