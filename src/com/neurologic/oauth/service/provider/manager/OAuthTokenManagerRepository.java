/**
 * 
 */
package com.neurologic.oauth.service.provider.manager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Buhake Sindi
 * @since 15 September 2011
 *
 */
public class OAuthTokenManagerRepository {

	private static OAuthTokenManagerRepository instance = new OAuthTokenManagerRepository();
	private Map<String, OAuthTokenManager> tokenManagerMap;
	
	private OAuthTokenManagerRepository() {
		if (tokenManagerMap == null) {
			tokenManagerMap = new HashMap<String, OAuthTokenManager>();
		}
	}

	/**
	 * @return the instance
	 */
	public static OAuthTokenManagerRepository getInstance() {
		if (instance == null) {
			instance = new OAuthTokenManagerRepository();
		}
		
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends OAuthTokenManager> T get(String oauthName) {
		return (T) tokenManagerMap.get(oauthName);
	}
	
	public <T extends OAuthTokenManager> void put(String oauthName, T tokenManager) {
		tokenManagerMap.put(oauthName, tokenManager);
	}
}
