/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store.data.oauth1;

import com.neurologic.oauth.service.provider.manager.store.StoreData;

/**
 * @author Buhake Sindi
 * @since 31 August 2011
 *
 */
public abstract class AbstractTokenStoreData extends StoreData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8228131660772135833L;
	private String token;
	private String tokenSecret;
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * @return the tokenSecret
	 */
	public String getTokenSecret() {
		return tokenSecret;
	}
	
	/**
	 * @param tokenSecret the tokenSecret to set
	 */
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
}
