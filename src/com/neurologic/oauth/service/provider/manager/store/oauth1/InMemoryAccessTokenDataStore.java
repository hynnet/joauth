/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store.oauth1;

import com.neurologic.exception.StoreException;
import com.neurologic.oauth.service.provider.manager.store.InMemoryDataStore;
import com.neurologic.oauth.service.provider.manager.store.data.oauth1.AccessTokenStoreData;

/**
 * @author Buhake Sindi
 * @since 01 September 2011
 *
 */
public class InMemoryAccessTokenDataStore extends InMemoryDataStore<AccessTokenStoreData> {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.Store#save(com.neurologic.oauth.service.provider.manager.store.StoreData)
	 */
	@Override
	public void save(AccessTokenStoreData accessTokenData) throws StoreException {
		// TODO Auto-generated method stub
		if (contains(accessTokenData.getToken())) {
			throw new StoreException("Access Token '" + accessTokenData.getToken() + "' already exists.");
		}
		
		super.tokenStore.put(accessTokenData.getToken(), accessTokenData);
	}
}
