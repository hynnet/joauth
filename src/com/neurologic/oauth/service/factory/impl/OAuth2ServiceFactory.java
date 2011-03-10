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
import com.neurologic.oauth.service.factory.OAuthServiceFactory;
import com.neurologic.oauth.service.impl.OAuth2Service;

/**
 * @author Bienfait Sindi
 * @since 06 December 2010
 *
 */
public class OAuth2ServiceFactory implements OAuthServiceFactory {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.factory.OAuthServiceFactory#createOAuthService(java.lang.Class, com.neurologic.oauth.config.ProviderConfig, com.neurologic.oauth.config.ConsumerConfig)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> OAuthService<T> createOAuthService(Class<?> clazz, ProviderConfig providerConfig, ConsumerConfig consumerConfig) throws Exception {
		// TODO Auto-generated method stub
		if (!OAuth2Service.class.isAssignableFrom(clazz)) {
			throw new Exception("Class '" + clazz.getName() + "' is not an instance of '" + OAuth2Service.class.getName() + "'.");
		}
		
		OAuth2ServiceProvider serviceProvider = new OAuth2ServiceProvider(providerConfig.getAuthorizationUrl(), providerConfig.getAccessTokenUrl());
		OAuth2Consumer consumer = new OAuth2Consumer(consumerConfig.getKey(), consumerConfig.getSecret(), serviceProvider);
		OAuth2Service service = (OAuth2Service) clazz.newInstance();
		service.setOAuthConsumer(consumer);
		
		return (OAuthService<T>) service;
	}
}
