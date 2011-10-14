/**
 * 
 */
package com.neurologic.oauth.service.provider;

import javax.servlet.http.HttpServletRequest;

import net.oauth.exception.OAuthException;
import net.oauth.provider.OAuthServiceProvider;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.exception.RequestMethodException;
import com.neurologic.exception.SecureChannelException;
import com.neurologic.oauth.service.provider.manager.OAuthTokenManager;

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
	
	/**
	 * This methods checks if the request received from the client conforms to OAuth protocol.
	 * 
	 * @param request
	 * @throws OAuthException, if the check fails. We need the exception message.
	 */
	protected void validateRequest(HttpServletRequest request) throws OAuthException {
		if (!isSecure(request)) {
			throw new SecureChannelException("This channel, '" + request.getScheme() + "' is unsecure.");
		}
		
		if (!"POST".equals(request.getMethod())) {
			throw new RequestMethodException("Cannot execute request with '" + request.getMethod() + "' HTTP method.");
		}
		
		if (request.getHeader(HTTP_HEADER_AUTHORIZATION) == null) {
			throw new OAuthAuthorizationException("No '" + HTTP_HEADER_AUTHORIZATION + "' header found.");
		}
	}
}
