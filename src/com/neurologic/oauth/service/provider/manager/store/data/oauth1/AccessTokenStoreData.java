/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store.data.oauth1;


/**
 * @author Buhake Sindi
 * @since 31 August 2011
 *
 */
public class AccessTokenStoreData extends AbstractTokenStoreData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -66589842316038382L;
	private String userId;
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
