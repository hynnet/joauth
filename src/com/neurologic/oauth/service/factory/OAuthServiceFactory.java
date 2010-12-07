/**
 * 
 */
package com.neurologic.oauth.service.factory;

import com.neurologic.oauth.config.ConsumerConfig;
import com.neurologic.oauth.config.ProviderConfig;
import com.neurologic.oauth.service.OAuthService;

/**
 * @author Bienfait Sindi
 * @since 06 December 2010
 *
 */
public interface OAuthServiceFactory {

	public <T> OAuthService<T> createOAuthService(Class<?> clazz, ProviderConfig provider, ConsumerConfig consumer) throws Exception;
}
