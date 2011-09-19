/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.neurologic.exception.StoreException;

/**
 * @author Buhake Sindi
 * @since 18 August 2011
 *
 */
public abstract class AbstractDataStore<T extends StoreData> implements DataStore<T> {

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.Store#find(java.lang.String)
	 */
	@Override
	public T find(String id) throws StoreException {
		// TODO Auto-generated method stub
		T data = fetch(id);
		if (data != null && isExpired(data)) {
			delete(id);
			data = null;
		}
		
		return data;
	}
	
	public List<T> findByConsumerKey(String consumerKey) throws StoreException {
		List<T> list = new ArrayList<T>();
		String[] keys = keys();
		
		if (keys != null) {
			for (String key : keys) {
				T data = find(key);
				if (data != null) {
					list.add(data);
				}
			}
		}
		
		return list;
	}

	public boolean isExpired(T data) {
		if (data.getMaximumValidity() == -1) {
			return false;
		}
		
		long now = System.currentTimeMillis();
		return ((now - data.getCreationTime()) > data.getMaximumValidity());
	}
	
//	public void processExpired() throws StoreException {
//		
//		String[] keys = keys();
//		long now = System.currentTimeMillis();
//		
//		if (keys != null) {
//			for (String key : keys) {
//				T data = fetch(key);
//				if (data != null && ((now - data.getCreationTime()) > data.getMaximumValidity())) {
//					if (logger.isInfoEnabled()) {
//						logger.info(this.getClass() + ": data '" + key + "' expired. Removing...");
//					}
//					
//					delete(key);
//				}
//			}
//		}
//	}
//	
	protected abstract T fetch(String id) throws StoreException;
}
