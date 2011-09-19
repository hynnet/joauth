/**
 * 
 */
package com.neurologic.oauth.service.provider.manager;

import com.neurologic.oauth.service.provider.generator.TokenStringGenerator;

/**
 * @author Buhake Sindi
 * @since 15 September 2011
 *
 */
public class OAuth2TokenManager extends AbstractOAuthTokenManager {

	private TokenStringGenerator authorizationTokenGenerator;
	
	/**
	 * @return the authorizationTokenGenerator
	 */
	public TokenStringGenerator getAuthorizationTokenGenerator() {
		return authorizationTokenGenerator;
	}

	/**
	 * @param authorizationTokenGenerator the authorizationTokenGenerator to set
	 */
	public void setAuthorizationTokenGenerator(TokenStringGenerator authorizationTokenGenerator) {
		this.authorizationTokenGenerator = authorizationTokenGenerator;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.OAuthTokenManager#garbageCollect()
	 */
	@Override
	public void garbageCollect() {
		// TODO Auto-generated method stub

	}

}
