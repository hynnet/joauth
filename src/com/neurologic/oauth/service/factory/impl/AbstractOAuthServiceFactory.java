/**
 * 
 */
package com.neurologic.oauth.service.factory.impl;

import net.oauth.provider.OAuthServiceProvider;

import org.apache.log4j.Logger;

import com.neurologic.oauth.config.ConsumerConfig;
import com.neurologic.oauth.config.ManagerConfig;
import com.neurologic.oauth.config.ProviderConfig;
import com.neurologic.oauth.service.OAuthConsumerService;
import com.neurologic.oauth.service.OAuthProviderService;
import com.neurologic.oauth.service.Service;
import com.neurologic.oauth.service.factory.OAuthServiceFactory;
import com.neurologic.oauth.service.provider.OAuthTokenProviderService;
import com.neurologic.oauth.service.provider.manager.OAuthTokenManager;
import com.neurologic.oauth.service.provider.manager.OAuthTokenManagerRepository;
import com.neurologic.oauth.util.ApplicationUtil;

/**
 * @author Buhake Sindi
 * @since 15 September 2011
 *
 */
public abstract class AbstractOAuthServiceFactory<SP extends OAuthServiceProvider, T extends OAuthTokenManager, C> implements OAuthServiceFactory {

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.factory.OAuthServiceFactory#createOAuthProviderService(java.lang.String, java.lang.String, com.neurologic.oauth.config.ProviderConfig, com.neurologic.oauth.config.ManagerConfig)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Service createOAuthProviderService(String oauthName, String serviceClassName, ProviderConfig providerConfig, ManagerConfig managerConfig) throws Exception {
		// TODO Auto-generated method stub
		Class<?> serviceClass = ApplicationUtil.applicationClass(serviceClassName);
		if (serviceClass == null) {
			throw new Exception("No class exits for '" + serviceClassName + "'.");
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("Creating provider service for class '" + serviceClassName +"'.");
		}
		
		OAuthProviderService providerService = (OAuthProviderService) serviceClass.newInstance();
		providerService.setOAuthName(oauthName);
		T tokenManager = OAuthTokenManagerRepository.getInstance().get(oauthName);
		if (tokenManager == null) {
			tokenManager = createOAuthTokenManager(managerConfig, oauthName);
			OAuthTokenManagerRepository.getInstance().put(oauthName, tokenManager);
		}
		
		if (providerService instanceof OAuthTokenProviderService) {
			((OAuthTokenProviderService<T, SP>)providerService).setOAuthServiceProvider(createOAuthServiceProvider(providerConfig, oauthName));
		}
		
		return providerService;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.factory.OAuthServiceFactory#createOAuthConsumerService(java.lang.String, java.lang.String, com.neurologic.oauth.config.ProviderConfig, com.neurologic.oauth.config.ConsumerConfig)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Service createOAuthConsumerService(String oauthName, String serviceClassName, ProviderConfig providerConfig, ConsumerConfig consumerConfig) throws Exception {
		// TODO Auto-generated method stub
		Class<?> serviceClass = ApplicationUtil.applicationClass(serviceClassName);
		if (serviceClass == null) {
			throw new Exception("No class exits for '" + serviceClassName + "'.");
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("Creating consumer service for class '" + serviceClassName +"'.");
		}
		
		OAuthConsumerService<C, ?> consumerService = (OAuthConsumerService<C, ?>) serviceClass.newInstance();
		consumerService.setOAuthConsumer(createOAuthConsumer(consumerConfig, providerConfig, oauthName));
		return consumerService;
	}

	protected abstract SP createOAuthServiceProvider(ProviderConfig providerConfig, String oauthName) throws Exception;
	protected abstract T createOAuthTokenManager(ManagerConfig managerConfig, String oauthName) throws Exception;
	protected abstract C createOAuthConsumer(ConsumerConfig consumerConfig, ProviderConfig providerConfig, String oauthName) throws Exception;
}
