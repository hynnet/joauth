/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store;

import com.neurologic.exception.StoreException;
import com.neurologic.oauth.service.provider.manager.store.data.ConsumerKeyStoreData;

/**
 * @author Buhake Sindi
 * @since 01 September 2011
 *
 */
public class InMemoryConsumerKeyDataStore extends InMemoryDataStore<ConsumerKeyStoreData> {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.Store#save(com.neurologic.oauth.service.provider.manager.store.StoreData)
	 */
	@Override
	public void save(ConsumerKeyStoreData consumerKeyData) throws StoreException {
		// TODO Auto-generated method stub
		if (contains(consumerKeyData.getConsumerKey())) {
			throw new StoreException("Consumer key '" + consumerKeyData.getConsumerKey() + "' already exists.");
		}
		
		super.tokenStore.put(consumerKeyData.getConsumerKey(), consumerKeyData);
	}
}
