/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store.oauth1;

import com.neurologic.exception.StoreException;
import com.neurologic.oauth.service.provider.manager.store.AbstractDataStore;
import com.neurologic.oauth.service.provider.manager.store.data.oauth1.UsedNonceStoreData;

/**
 * @author Buhake Sindi
 * @since 02 September 2011
 *
 */
public abstract class AbstractUsedNonceDataStore extends AbstractDataStore<UsedNonceStoreData> {
	
	private static final String METHOD_UNSUPPORTED = "Method unsupported.";
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.AbstractDataStore#find(java.lang.String)
	 */
	@Override
	@Deprecated
	public final UsedNonceStoreData find(String id) throws StoreException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(METHOD_UNSUPPORTED);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.Store#delete(java.lang.String)
	 */
	@Override
	@Deprecated
	public final void delete(String id) throws StoreException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(METHOD_UNSUPPORTED);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.Store#keys()
	 */
	@Override
	@Deprecated
	public final String[] keys() throws StoreException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(METHOD_UNSUPPORTED);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.AbstractStore#fetch(java.lang.String)
	 */
	@Override
	@Deprecated
	protected final UsedNonceStoreData fetch(String id) throws StoreException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(METHOD_UNSUPPORTED);
	}
	
	/**
	 * Find a used nonce based on provided nonce.
	 * 
	 * @param nonce
	 * @return
	 * @throws StoreException
	 */
	public UsedNonceStoreData find(String consumerKey, String nonce) throws StoreException {
		if (nonce == null || nonce.isEmpty()) {
			return null;
		}
		
		UsedNonceStoreData[] values = values(consumerKey);
		if (values != null) {
			for (UsedNonceStoreData usedNonce : values) {
				if (nonce.equals(usedNonce.getNonce())) {
					if (isExpired(usedNonce)) {
						delete(consumerKey, nonce);
						return null;
					}
					
					return usedNonce;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Find a used nonce based on timestamp.
	 * 
	 * @param timestamp
	 * @return
	 * @throws StoreException
	 */
	public UsedNonceStoreData findByTimestamp(String consumerKey, long timestamp) throws StoreException {
		
		UsedNonceStoreData[] values = values(consumerKey);
		if (values != null) {
			for (UsedNonceStoreData usedNonce : values) {
				if (usedNonce.getTimestamp() == timestamp) {
					if (isExpired(usedNonce)) {
						delete(consumerKey, timestamp);
						return null;
					}
					
					return usedNonce;
				}
			}
		}
		
		return null;
	}
	
	public abstract void delete(String consumerKey, long timestamp) throws StoreException;
	public abstract void delete(String consumerKey, String nonce) throws StoreException;
	public abstract int size(String consumerKey) throws StoreException;
	public abstract UsedNonceStoreData[] values(String consumerKey) throws StoreException;
}
