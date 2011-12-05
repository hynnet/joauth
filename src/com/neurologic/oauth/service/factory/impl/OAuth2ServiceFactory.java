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

import net.oauth.consumer.OAuth2Consumer;
import net.oauth.provider.OAuth2ServiceProvider;

import com.neurologic.oauth.config.ConsumerConfig;
import com.neurologic.oauth.config.ManagerConfig;
import com.neurologic.oauth.config.ProviderConfig;
import com.neurologic.oauth.service.provider.manager.OAuth2TokenManager;
import com.neurologic.oauth.util.ApplicationUtil;

/**
 * @author Bienfait Sindi
 * @since 06 December 2010
 *
 */
public class OAuth2ServiceFactory extends AbstactOAuthServiceFactory<OAuth2ServiceProvider, OAuth2TokenManager, OAuth2Consumer> {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.factory.impl.AbstactOAuthServiceFactory#createOAuthServiceProvider(com.neurologic.oauth.config.ProviderConfig, java.lang.String)
	 */
	@Override
	protected OAuth2ServiceProvider createOAuthServiceProvider(ProviderConfig providerConfig, String oauthName) throws Exception {
		// TODO Auto-generated method stub
		OAuth2ServiceProvider serviceProvider = null;
		if (providerConfig.getClassName() == null || providerConfig.getClassName().isEmpty()) {
			if (providerConfig.getAuthorizationUrl() == null && providerConfig.getAccessTokenUrl() == null) {
				throw new Exception("No provider endpoints provider for oauth '" + oauthName + "'");
			}
			
			serviceProvider = new OAuth2ServiceProvider(providerConfig.getAuthorizationUrl(), providerConfig.getAccessTokenUrl());
		} else {
			Class<OAuth2ServiceProvider> serviceProviderClass = ApplicationUtil.applicationClass(providerConfig.getClassName());
			if (serviceProviderClass == null) {
				throw new Exception("Provider class '" + providerConfig.getClassName() + "' not found.");
			}
			
			//Just in case...
			if (!OAuth2ServiceProvider.class.isAssignableFrom(serviceProviderClass)) {
				throw new Exception("Provider class '" + providerConfig.getClassName() + "' is not an instance of '" + OAuth2ServiceProvider.class.getName() + "'");
			}
			
			serviceProvider = serviceProviderClass.newInstance();
		}
		
		return serviceProvider;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.factory.impl.AbstactOAuthServiceFactory#createOAuthTokenManager(com.neurologic.oauth.config.ManagerConfig, java.lang.String)
	 */
	@Override
	protected OAuth2TokenManager createOAuthTokenManager(ManagerConfig managerConfig, String oauthName) throws Exception {
		// TODO Auto-generated method stub
		OAuth2TokenManager manager = null;
		if (managerConfig.getClassName() == null || managerConfig.getClassName().isEmpty()) {
			logger.info("No 'class' attribute set, using default '" + OAuth2TokenManager.class.getName() + "'.");
			manager = new OAuth2TokenManager();
		} else {
			logger.info("Creating oauth manager '" + managerConfig.getClassName() + "'.");
			Class<?> managerClass = ApplicationUtil.applicationClass(managerConfig.getClassName());
			if (managerClass == null) {
				throw new Exception("Manager class '" + managerConfig.getClassName() + "' not found.");
			}
			
			//Just in case...
			if (!OAuth2TokenManager.class.isAssignableFrom(managerClass)) {
				throw new Exception("Manager class '" + managerConfig.getClassName() + "' is not an instance of '" + OAuth2TokenManager.class.getName() + "'");
			}
			
			manager = (OAuth2TokenManager) managerClass.newInstance();
		}
		
		return manager;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.factory.impl.AbstactOAuthServiceFactory#createOAuthConsumer(com.neurologic.oauth.config.ConsumerConfig, com.neurologic.oauth.config.ProviderConfig, java.lang.String)
	 */
	@Override
	protected OAuth2Consumer createOAuthConsumer(ConsumerConfig consumerConfig,	ProviderConfig providerConfig, String oauthName) throws Exception {
		// TODO Auto-generated method stub
		return new OAuth2Consumer(consumerConfig.getKey(), consumerConfig.getSecret(), createOAuthServiceProvider(providerConfig, oauthName));
	}
}
