/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.neurologic.exception.StoreException;

/**
 * @author Buhake Sindi
 * @since 25 August 2011
 *
 */
public abstract class InMemoryDataStore<T extends StoreData> extends AbstractDataStore<T> {
	
	protected final ConcurrentMap<String, T> tokenStore = new ConcurrentHashMap<String, T>();

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.Store#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String id) throws StoreException {
		// TODO Auto-generated method stub
		return tokenStore.containsKey(id);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.EntityTokenStore#delete(java.lang.String)
	 */
	@Override
	public void delete(String id) throws StoreException {
		// TODO Auto-generated method stub
		if (contains(id)) {
			tokenStore.remove(id);
		}
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.EntityTokenStore#keys()
	 */
	@Override
	public String[] keys() throws StoreException {
		// TODO Auto-generated method stub
		List<String> keyList = new ArrayList<String>();
		for (String key : tokenStore.keySet()) {
			keyList.add(key);
		}
		
		return keyList.toArray(new String[keyList.size()]);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.EntityTokenStore#fetch(java.lang.String)
	 */
	@Override
	protected T fetch(String id) throws StoreException {
		// TODO Auto-generated method stub
		return tokenStore.get(id);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.EntityTokenStore#size()
	 */
	@Override
	public int size() throws StoreException {
		// TODO Auto-generated method stub
		return tokenStore.size();
	}
}
