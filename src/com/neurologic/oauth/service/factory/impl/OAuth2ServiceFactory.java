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
import com.neurologic.oauth.config.ProviderConfig;
import com.neurologic.oauth.service.OAuthService;
import com.neurologic.oauth.service.consumer.OAuth2Service;
import com.neurologic.oauth.service.factory.OAuthServiceFactory;
import com.neurologic.oauth.service.provider.v2.OAuth2ProviderService;
import com.neurologic.oauth.util.ClassLoaderUtil;

/**
 * @author Bienfait Sindi
 * @since 06 December 2010
 *
 */
public class OAuth2ServiceFactory implements OAuthServiceFactory {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.factory.OAuthServiceFactory#createOAuthProviderService(java.lang.String, java.lang.Class, com.neurologic.oauth.config.ProviderConfig)
	 */
	@Override
	public OAuthService createOAuthProviderService(String oauthName, Class<?> serviceClass, ProviderConfig providerConfig) throws Exception {
		// TODO Auto-generated method stub
		if (!OAuth2ProviderService.class.isAssignableFrom(serviceClass)) {
			throw new Exception("Class '" + serviceClass.getName() + "' is not an instance of '" + OAuth2ProviderService.class.getName() + "'.");
		}
		
		OAuth2ProviderService service = (OAuth2ProviderService) serviceClass.newInstance();
		service.setOAuthServiceProvider(createServiceProvider(providerConfig, oauthName));

		return (OAuthService) service;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.factory.OAuthServiceFactory#createOAuthConsumerService(java.lang.String, java.lang.Class, com.neurologic.oauth.config.ProviderConfig, com.neurologic.oauth.config.ConsumerConfig)
	 */
	@Override
	public OAuthService createOAuthConsumerService(String oauthName, Class<?> serviceClass, ProviderConfig providerConfig, ConsumerConfig consumerConfig) throws Exception {
		// TODO Auto-generated method stub
		if (!OAuth2Service.class.isAssignableFrom(serviceClass)) {
			throw new Exception("Class '" + serviceClass.getName() + "' is not an instance of '" + OAuth2Service.class.getName() + "'.");
		}
		
		OAuth2Consumer consumer = new OAuth2Consumer(consumerConfig.getKey(), consumerConfig.getSecret(), createServiceProvider(providerConfig, oauthName));
		OAuth2Service service = (OAuth2Service) serviceClass.newInstance();
		service.setOAuthConsumer(consumer);
		
		return (OAuthService) service;
	}
	
	private OAuth2ServiceProvider createServiceProvider(ProviderConfig providerConfig, String oauthName) throws Exception {
		OAuth2ServiceProvider serviceProvider = null;
		if (providerConfig.getClassName() == null || providerConfig.getClassName().isEmpty()) {
			if (providerConfig.getAuthorizationUrl() == null && providerConfig.getAccessTokenUrl() == null) {
				throw new Exception("No provider endpoints provider for oauth '" + oauthName + "'");
			}
			
			serviceProvider = new OAuth2ServiceProvider(providerConfig.getAuthorizationUrl(), providerConfig.getAccessTokenUrl());
		} else {
			Class<?> serviceProviderClass = ClassLoaderUtil.getInstance().getClassLoader().loadClass(providerConfig.getClassName());
			if (serviceProviderClass == null) {
				throw new Exception("Provider class '" + providerConfig.getClassName() + "' not found.");
			}
			
			if (!OAuth2ServiceProvider.class.isAssignableFrom(serviceProviderClass)) {
				throw new Exception("Provider class '" + providerConfig.getClassName() + "' is not an instance of '" + OAuth2ServiceProvider.class.getName() + "'");
			}
			
			serviceProvider = (OAuth2ServiceProvider) serviceProviderClass.newInstance();
		}
		
		return serviceProvider;
	}
}
