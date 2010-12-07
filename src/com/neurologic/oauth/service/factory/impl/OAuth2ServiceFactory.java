/**
 * 
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
