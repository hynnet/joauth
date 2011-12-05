/**
 * 
 */
package com.neurologic.oauth.service.provider;

import javax.servlet.http.HttpServletRequest;

import net.oauth.exception.OAuthException;

import org.apache.log4j.Logger;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.exception.RequestMethodException;
import com.neurologic.exception.SecureChannelException;
import com.neurologic.oauth.service.AbstractOAuthHttpService;
import com.neurologic.oauth.service.OAuthProviderService;
import com.neurologic.oauth.service.provider.manager.OAuthTokenManager;
import com.neurologic.oauth.service.provider.manager.OAuthTokenManagerRepository;

/**
 * @author Buhake Sindi
 * @since 01 September 2011
 *
 */
public abstract class AbstractOAuthProviderService<TM extends OAuthTokenManager> extends AbstractOAuthHttpService implements OAuthProviderService {

	protected final Logger logger = Logger.getLogger(this.getClass());
//	private TM oauthTokenManager;
	
	/**
	 * @return the oauthTokenManager
	 */
	protected TM getOauthTokenManager() {
//		if (oauthTokenManager == null) {
//			oauthTokenManager = OAuthTokenManagerRepository.getInstance().get(getOauthName());
//		}
//		
//		return oauthTokenManager;
		return OAuthTokenManagerRepository.getInstance().get(getOauthName());
	}
	
	/**
	 * This methods checks if the request received from the client conforms to OAuth protocol.
	 * 
	 * @param request
	 * @param checkAuthorizationHeader
	 * @throws OAuthException, if the check fails. We need the exception message.
	 */
	protected void validateRequest(HttpServletRequest request, boolean checkAuthorizationHeader) throws OAuthException {
		if (!request.isSecure()) {
			throw new SecureChannelException("This channel, '" + request.getScheme() + "' is unsecure.");
		}
		
		if (!"POST".equals(request.getMethod())) {
			throw new RequestMethodException("Cannot execute request with '" + request.getMethod() + "' HTTP method.");
		}
		
		if (checkAuthorizationHeader && request.getHeader(HTTP_HEADER_AUTHORIZATION) == null) {
			throw new OAuthAuthorizationException("No '" + HTTP_HEADER_AUTHORIZATION + "' header found.");
		}
	}
}
