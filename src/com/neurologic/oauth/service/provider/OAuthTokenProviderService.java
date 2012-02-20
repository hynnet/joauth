/**
 * 
 */
package com.neurologic.oauth.service.provider;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.provider.OAuthServiceProvider;

import com.neurologic.oauth.service.provider.manager.OAuthTokenManager;
import com.neurologic.oauth.service.response.OAuthResult;
import com.neurologic.oauth.service.response.impl.OAuthMessageResult;
import com.neurologic.oauth.service.response.impl.StringMessage;

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
		try {
			return new OAuthMessageResult(HttpServletResponse.SC_METHOD_NOT_ALLOWED, new StringMessage(request.getMethod() + " not allowed in this method call.", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return null;
	}
}
