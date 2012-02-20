/**
 * 
 */
package com.neurologic.oauth.processor;

import org.apache.log4j.Logger;

import com.neurologic.oauth.config.ModuleConfig;

/**
 * @author Buhake Sindi
 *
 */
public abstract class GenericOAuthProcessor implements OAuthProcessor {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private ModuleConfig moduleConfig;
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.processor.OAuthProcessor#init(com.neurologic.oauth.config.ModuleConfig)
	 */
	@Override
	public void init(ModuleConfig moduleConfig) {
		// TODO Auto-generated method stub
		if (this.moduleConfig == null) {
			this.moduleConfig = moduleConfig;
		}
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.processor.OAuthProcessor#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		moduleConfig = null;
	}

	/**
	 * @return the moduleConfig
	 */
	protected ModuleConfig getModuleConfig() {
		return moduleConfig;
	}
}
