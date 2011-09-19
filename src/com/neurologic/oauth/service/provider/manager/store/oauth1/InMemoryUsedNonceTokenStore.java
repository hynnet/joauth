/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store.oauth1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.neurologic.exception.StoreException;
import com.neurologic.oauth.service.provider.manager.store.data.oauth1.UsedNonceStoreData;

/**
 * @author Buhake Sindi
 * @since 06 September 2011
 *
 */
public class InMemoryUsedNonceTokenStore extends AbstractUsedNonceDataStore {
	
	private final Map<String, LinkedList<UsedNonceStoreData>> inMemoryMap = Collections.synchronizedMap(new HashMap<String, LinkedList<UsedNonceStoreData>>());

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.oauth1.AbstractUsedNonceDataStore#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String consumerKey, String nonce) throws StoreException {
		if (nonce == null || nonce.isEmpty()) {
			return ;
		}
		
		// TODO Auto-generated method stub
		LinkedList<UsedNonceStoreData> usedNonceList = inMemoryMap.get(consumerKey);
		if (usedNonceList != null) {
			Iterator<UsedNonceStoreData> usedNonceIterator = usedNonceList.iterator();
			while (usedNonceIterator.hasNext()) {
				UsedNonceStoreData usedNonce = usedNonceIterator.next();
				if (nonce.equals(usedNonce.getNonce())) {
					usedNonceIterator.remove();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.oauth1.AbstractUsedNonceDataStore#delete(java.lang.String, long)
	 */
	@Override
	public void delete(String consumerKey, long timestamp) throws StoreException {
		// TODO Auto-generated method stub
		LinkedList<UsedNonceStoreData> usedNonceList = inMemoryMap.get(consumerKey);
		if (usedNonceList != null) {
			Iterator<UsedNonceStoreData> usedNonceIterator = usedNonceList.iterator();
			while (usedNonceIterator.hasNext()) {
				UsedNonceStoreData usedNonce = usedNonceIterator.next();
				if (usedNonce.getTimestamp() == timestamp) {
					usedNonceIterator.remove();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.oauth1.AbstractUsedNonceDataStore#size(java.lang.String)
	 */
	@Override
	public int size(String consumerKey) throws StoreException {
		// TODO Auto-generated method stub
		LinkedList<UsedNonceStoreData> usedNonceList = inMemoryMap.get(consumerKey);
		if (usedNonceList != null) {
			return usedNonceList.size();
		}
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.oauth1.AbstractUsedNonceDataStore#values(java.lang.String)
	 */
	@Override
	public UsedNonceStoreData[] values(String consumerKey) throws StoreException {
		// TODO Auto-generated method stub
		LinkedList<UsedNonceStoreData> usedNonceList = inMemoryMap.get(consumerKey);
		if (usedNonceList == null) {
			return null;
		}
		
		return usedNonceList.toArray(new UsedNonceStoreData[usedNonceList.size()]);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.Store#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String consumerKey) throws StoreException {
		// TODO Auto-generated method stub
		return inMemoryMap.containsKey(consumerKey);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.Store#save(com.neurologic.oauth.service.provider.manager.store.StoreData)
	 */
	@Override
	public void save(UsedNonceStoreData usedNonceData) throws StoreException {
		// TODO Auto-generated method stub
		String consumerKey = usedNonceData.getConsumerKey();
		if (consumerKey == null || consumerKey.isEmpty()) {
			throw new StoreException("No consumer key provided.");
		}
		
		if (!contains(consumerKey)) {
			inMemoryMap.put(consumerKey, new LinkedList<UsedNonceStoreData>());
		}
		
		inMemoryMap.get(consumerKey).add(usedNonceData);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.Store#size()
	 */
	@Override
	public int size() throws StoreException {
		// TODO Auto-generated method stub
		int totalSize = 0;
		for (String consumerKey : inMemoryMap.keySet()) {
			totalSize += size(consumerKey);
		}
		
		return totalSize;
	}
}
