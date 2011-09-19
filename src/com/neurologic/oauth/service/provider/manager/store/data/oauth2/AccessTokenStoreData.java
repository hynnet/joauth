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

	private String token;
	private long expiresIn;
	private String refreshToken;
}
