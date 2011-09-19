/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store;

import com.neurologic.exception.StoreException;

/**
 * @author Buhake Sindi
 * @since 18 August 2011
 *
 */
public interface DataStore<T extends StoreData> {

	public boolean contains(String id) throws StoreException;
	public void delete(String id) throws StoreException;
	public String[] keys() throws StoreException;
	public T find(String id) throws StoreException;
	public void save(T storeData) throws StoreException;
	public int size() throws StoreException;
}
