/**
 *  Copyright 2010-2011 Buhake Sindi

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package com.neurologic.oauth.service.factory.impl;

import net.oauth.consumer.OAuth1Consumer;
import net.oauth.provider.OAuth1ServiceProvider;

import com.neurologic.oauth.config.ConsumerConfig;
import com.neurologic.oauth.config.ManagerConfig;
import com.neurologic.oauth.config.ProviderConfig;
import com.neurologic.oauth.config.StoreConfig;
import com.neurologic.oauth.service.provider.generator.TokenStringGenerator;
import com.neurologic.oauth.service.provider.manager.OAuth1TokenManager;
import com.neurologic.oauth.service.provider.manager.store.DataStore;
import com.neurologic.oauth.service.provider.manager.store.FileDataStore;
import com.neurologic.oauth.service.provider.manager.store.JDBCDataStore;
import com.neurologic.oauth.service.provider.manager.store.data.ConsumerKeyStoreData;
import com.neurologic.oauth.service.provider.manager.store.data.oauth1.AccessTokenStoreData;
import com.neurologic.oauth.service.provider.manager.store.data.oauth1.RequestTokenStoreData;
import com.neurologic.oauth.service.provider.manager.store.oauth1.AbstractUsedNonceDataStore;
import com.neurologic.oauth.util.ApplicationUtil;

/**
 * @author Bienfait Sindi
 * @since 06 December 2010
 *
 */
public class OAuth1ServiceFactory extends AbstractOAuthServiceFactory<OAuth1ServiceProvider, OAuth1TokenManager, OAuth1Consumer> {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.factory.impl.AbstactOAuthServiceFactory#createOAuthServiceProvider(com.neurologic.oauth.config.ProviderConfig, java.lang.String)
	 */
	@Override
	protected OAuth1ServiceProvider createOAuthServiceProvider(ProviderConfig providerConfig, String oauthName) throws Exception {
		// TODO Auto-generated method stub
		OAuth1ServiceProvider serviceProvider = null;
		if (providerConfig.getClassName() == null || providerConfig.getClassName().isEmpty()) {
			if (providerConfig.getRequestTokenUrl() == null && providerConfig.getAuthorizationUrl() == null && providerConfig.getAccessTokenUrl() == null) {
				throw new Exception("No provider endpoints provider for oauth '" + oauthName + "'");
			}
			
			serviceProvider = new OAuth1ServiceProvider(providerConfig.getRequestTokenUrl(), providerConfig.getAuthorizationUrl(), providerConfig.getAccessTokenUrl());
		} else {
			
			Class<OAuth1ServiceProvider> serviceProviderClass = ApplicationUtil.applicationClass(providerConfig.getClassName());
			if (serviceProviderClass == null) {
				throw new Exception("Provider class '" + providerConfig.getClassName() + "' not found.");
			}
			
			//Just to make sure....
			if (!OAuth1ServiceProvider.class.isAssignableFrom(serviceProviderClass)) {
				throw new Exception("Provider class '" + providerConfig.getClassName() + "' is not an instance of '" + OAuth1ServiceProvider.class.getName() + "'");
			}
			
			serviceProvider = serviceProviderClass.newInstance();
		}
		
		return serviceProvider;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.factory.impl.AbstactOAuthServiceFactory#createOAuthTokenManager(com.neurologic.oauth.config.ManagerConfig, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected OAuth1TokenManager createOAuthTokenManager(ManagerConfig managerConfig, String oauthName) throws Exception {
		// TODO Auto-generated method stub
		OAuth1TokenManager manager = null;
		if (managerConfig.getClassName() == null || managerConfig.getClassName().isEmpty()) {
			logger.info("No 'class' attribute set, using default '" + OAuth1TokenManager.class.getName() + "'.");
			manager = new OAuth1TokenManager();
		} else {
			logger.info("Creating oauth manager '" + managerConfig.getClassName() + "'.");
			Class<?> managerClass = ApplicationUtil.applicationClass(managerConfig.getClassName());
			if (managerClass == null) {
				throw new Exception("Manager class '" + managerConfig.getClassName() + "' not found.");
			}
			
			//Just in case...
			if (!OAuth1TokenManager.class.isAssignableFrom(managerClass)) {
				throw new Exception("Manager class '" + managerConfig.getClassName() + "' is not an instance of '" + OAuth1TokenManager.class.getName() + "'");
			}
			
			manager = (OAuth1TokenManager) managerClass.newInstance();
		}
		
		//Create stores for each.
		manager.setAccessTokenGenerator((TokenStringGenerator) ApplicationUtil.applicationInstance(managerConfig.getAccessTokenGeneratorClass()));
		manager.setAccessTokenStore((DataStore<AccessTokenStoreData>) createStore(managerConfig.getStoreConfig(managerConfig.getAccessTokenStoreName())));
		manager.setAuthVerifierGenerator((TokenStringGenerator) ApplicationUtil.applicationInstance(managerConfig.getAuthVerifierGeneratorClass()));
		manager.setConsumerKeyStore((DataStore<ConsumerKeyStoreData>) createStore(managerConfig.getStoreConfig(managerConfig.getConsumerKeyStoreName())));
		manager.setRequestTokenGenerator((TokenStringGenerator) ApplicationUtil.applicationInstance(managerConfig.getRequestTokenGeneratorClass()));
		manager.setRequestTokenStore((DataStore<RequestTokenStoreData>) createStore(managerConfig.getStoreConfig(managerConfig.getRequestTokenStoreName())));
		manager.setTokenSecretGenerator((TokenStringGenerator) ApplicationUtil.applicationInstance(managerConfig.getTokenSecretGeneratorClass()));
		manager.setUsedNonceStore((AbstractUsedNonceDataStore) createStore(managerConfig.getStoreConfig(managerConfig.getUsedNonceStoreName())));
		if (managerConfig.getAccessTokenValidity() > -1) {
			manager.setAccessTokenValidity(managerConfig.getAccessTokenValidity());
		}
		
		if (managerConfig.getAuthorizedTokenValidity() > -1) {
			manager.setAuthorizedTokenValidity(managerConfig.getAccessTokenValidity());
		}
		
		if (managerConfig.getRequestTokenValidity() > -1) {
			manager.setRequestTokenValidity(managerConfig.getRequestTokenValidity());
		}
		
		if (managerConfig.getTokenSecretLength() > -1) {
			manager.setTokenSecretLength(manager.getTokenSecretLength());
		}
		
		if (managerConfig.getUsedNonceTokenValidity() > -1) {
			manager.setUsedNonceValidity(managerConfig.getUsedNonceTokenValidity());
		}
		
		return manager;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.factory.impl.AbstactOAuthServiceFactory#createOAuthConsumer(com.neurologic.oauth.config.ConsumerConfig, com.neurologic.oauth.config.ProviderConfig, java.lang.String)
	 */
	@Override
	protected OAuth1Consumer createOAuthConsumer(ConsumerConfig consumerConfig, ProviderConfig providerConfig, String oauthName) throws Exception {
		// TODO Auto-generated method stub
		return new OAuth1Consumer(consumerConfig.getKey(), consumerConfig.getSecret(), createOAuthServiceProvider(providerConfig, oauthName));
	}
	
	private DataStore<?> createStore(StoreConfig storeConfig) throws Exception {
		if (storeConfig == null) {
			return null;
		}
		
		DataStore<?> store = (DataStore<?>)ApplicationUtil.applicationInstance(storeConfig.getClassName());
		if (store != null) {
			if (store instanceof JDBCDataStore) {
				((JDBCDataStore<?>)store).setConnectionUrl(storeConfig.getConnectionUrl());
				((JDBCDataStore<?>)store).setConnectionUserName(storeConfig.getUserName());
				((JDBCDataStore<?>)store).setConnectionPassword(storeConfig.getPassword());
				((JDBCDataStore<?>)store).setDataSourceName(storeConfig.getDataSourceName());
				((JDBCDataStore<?>)store).setDriverName(storeConfig.getDriverName());
			} else if (store instanceof FileDataStore) {
				String fileName = storeConfig.getFileName();
				if (fileName == null || fileName.isEmpty()) {
					throw new Exception("Store with name '" + storeConfig.getName() + "' has no 'fileName' attribute set.");
				}
				
				String directory = storeConfig.getDirectory();
				if (directory != null && !directory.isEmpty()) {
					((FileDataStore<?>)store).setDirectory(directory);
				}
				((FileDataStore<?>)store).setFileName(fileName);
				
				//load
				((FileDataStore<?>)store).load();
			}
		}
		
		return store;
	}
}
