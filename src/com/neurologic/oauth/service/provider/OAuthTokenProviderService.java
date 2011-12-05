/**
 * 
 */
package com.neurologic.oauth.service.provider;

import javax.servlet.http.HttpServletRequest;

import net.oauth.provider.OAuthServiceProvider;

import com.neurologic.oauth.service.provider.manager.OAuthTokenManager;
import com.neurologic.oauth.service.response.OAuthResult;

/**
 * @author Buhake Sindi
 * @since 15 August 2011
 *
 */
public abstract class OAuthTokenProviderService<TM extends OAuthTokenManager, SP extends OAuthServiceProvider> extends AbstractOAuthProviderService<TM> {
	
	protected SP serviceProvider;
	
	/**
	 * Set the service provider required.
	 * @param serviceProvider
	 */
	public final void setOAuthServiceProvider(SP serviceProvider) {
		// TODO Auto-generated method stub
		this.serviceProvider = serviceProvider;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.AbstractOAuthHttpService#executeGet(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected OAuthResult executeGet(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
