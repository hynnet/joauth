/**
 * 
 */
package com.neurologic.oauth.service.provider;

import org.apache.log4j.Logger;

import com.neurologic.oauth.service.AbstractOAuthService;
import com.neurologic.oauth.service.OAuthProviderService;
import com.neurologic.oauth.service.provider.manager.OAuthTokenManager;
import com.neurologic.oauth.service.provider.manager.OAuthTokenManagerRepository;

/**
 * @author Buhake Sindi
 * @since 01 September 2011
 *
 */
public abstract class AbstractOAuthProviderService<TM extends OAuthTokenManager> extends AbstractOAuthService implements OAuthProviderService {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private TM oauthTokenManager;
	
	/**
	 * @return the oauthTokenManager
	 */
	protected TM getOauthTokenManager() {
		if (oauthTokenManager == null) {
			oauthTokenManager = OAuthTokenManagerRepository.getInstance().get(getOauthName());
		}
		
		return oauthTokenManager;
	}
}
