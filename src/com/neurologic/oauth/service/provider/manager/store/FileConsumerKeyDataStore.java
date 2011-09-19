/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.neurologic.exception.StoreException;
import com.neurologic.oauth.service.provider.manager.store.data.ConsumerKeyStoreData;

/**
 * @author Buhake Sindi
 * @since 08 September 2011
 *
 */
public class FileConsumerKeyDataStore extends FileDataStore<ConsumerKeyStoreData> {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.DataStore#delete(java.lang.String)
	 */
	@Override
	public void delete(String id) throws StoreException {
		// TODO Auto-generated method stub
		if (contains(id)) {
			fileDataMap.remove(id);
		}
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.DataStore#save(com.neurologic.oauth.service.provider.manager.store.StoreData)
	 */
	@Override
	public void save(ConsumerKeyStoreData consumerKeyData) throws StoreException {
		// TODO Auto-generated method stub
		if (contains(consumerKeyData.getConsumerKey())) {
			throw new StoreException("Consumer key '" + consumerKeyData.getConsumerKey() + "' already exists.");
		}
		
		fileDataMap.put(consumerKeyData.getConsumerKey(), consumerKeyData);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.DataStore#size()
	 */
	@Override
	public int size() throws StoreException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.FileDataStore#load()
	 */
	@Override
	public void load() throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		File file = new File(directory(), getFileName());
		
		if (file != null && file.exists() && !file.isDirectory()) {
			Properties properties = new Properties();
			properties.load(new FileInputStream(file));
			
			for (Object key : properties.keySet()) {
				String consumerKey = (String) key;
				String consumerSecret = properties.getProperty(consumerKey);
				
				ConsumerKeyStoreData consumerKeySD = new ConsumerKeyStoreData();
				consumerKeySD.setConsumerKey(consumerKey);
				consumerKeySD.setConsumerSecret(consumerSecret);
				fileDataMap.put(consumerKey, consumerKeySD);
			}
		}
	}
}
