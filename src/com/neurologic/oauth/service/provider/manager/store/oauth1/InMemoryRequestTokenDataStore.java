/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store.oauth1;

import com.neurologic.exception.StoreException;
import com.neurologic.oauth.service.provider.manager.store.InMemoryDataStore;
import com.neurologic.oauth.service.provider.manager.store.data.oauth1.RequestTokenStoreData;

/**
 * @author Buhake Sindi
 * @since 01 September 2011
 *
 */
public class InMemoryRequestTokenDataStore extends InMemoryDataStore<RequestTokenStoreData> {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.Store#save(com.neurologic.oauth.service.provider.manager.store.StoreData)
	 */
	@Override
	public void save(RequestTokenStoreData requestTokenData) throws StoreException {
		// TODO Auto-generated method stub
		if (contains(requestTokenData.getToken())) {
			throw new StoreException("Request Token '" + requestTokenData.getToken() + "' already exists.");
		}
		
		super.tokenStore.put(requestTokenData.getToken(), requestTokenData);
	}
}
