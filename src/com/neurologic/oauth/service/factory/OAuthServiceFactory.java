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
package com.neurologic.oauth.service.factory;

import com.neurologic.oauth.config.ConsumerConfig;
import com.neurologic.oauth.config.ManagerConfig;
import com.neurologic.oauth.config.ProviderConfig;
import com.neurologic.oauth.service.OAuthService;

/**
 * @author Bienfait Sindi
 * @since 06 December 2010
 *
 */
public interface OAuthServiceFactory {

	public OAuthService createOAuthProviderService(String oauthName, String serviceClassName, ProviderConfig providerConfig, ManagerConfig managerConfig) throws Exception;
	public OAuthService createOAuthConsumerService(String oauthName, String serviceClassName, ProviderConfig providerConfig, ConsumerConfig consumerConfig) throws Exception;
}
