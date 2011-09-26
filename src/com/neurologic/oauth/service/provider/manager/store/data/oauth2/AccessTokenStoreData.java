/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store.data.oauth2;

import com.neurologic.oauth.service.provider.manager.store.StoreData;


/**
 * @author Buhake Sindi
 * @since 31 August 2011
 *
 */
public class AccessTokenStoreData extends StoreData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5015044630487212636L;
	private String token;
	private String refreshToken;
	
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
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}
	
	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
