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
package com.neurologic.oauth.processor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.neurologic.oauth.config.ConsumerConfig;
import com.neurologic.oauth.config.ModuleConfig;
import com.neurologic.oauth.config.OAuthConfig;
import com.neurologic.oauth.config.ProviderConfig;
import com.neurologic.oauth.config.ServiceConfig;
import com.neurologic.oauth.config.SuccessConfig;
import com.neurologic.oauth.service.OAuthService;
import com.neurologic.oauth.service.factory.OAuthServiceAbstractFactory;
import com.neurologic.oauth.util.ClassLoaderUtil;

/**
 * @author Bienfait Sindi
 * @since 23 November 2010
 *
 */
public class DefaultOAuthProcessor implements OAuthProcessor {

	private static final Logger logger = Logger.getLogger(DefaultOAuthProcessor.class);
	private ModuleConfig module;
	
	/**
	 * @param module
	 */
	public DefaultOAuthProcessor(ModuleConfig module) {
		super();
		this.module = module;
	}

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getPathInfo();
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieved path info \"" + path + "\".");
		}
		
		ServiceConfig serviceConfig = module.getServiceConfigByPath(path);
		if (serviceConfig == null) {
			throw new Exception("No <service> defined for path='" + path + "'.");
		}
		
		Class<?> serviceClass = ClassLoaderUtil.getInstance().getClassLoader().loadClass(serviceConfig.getServiceClass());
		if (serviceClass == null) {
			throw new Exception("No class exits for " + serviceConfig.getServiceClass());
		}
		
		OAuthConfig oauthConfig = module.getOAuthConfigByName(serviceConfig.getRefOAuth());
		if (oauthConfig == null) {
			throw new Exception("No <oauth> defined with name='" + serviceConfig.getRefOAuth() + "'.");
		}
		
		ProviderConfig providerConfig = oauthConfig.getProviderConfig();
		ConsumerConfig consumerConfig = oauthConfig.getConsumerConfig();
		OAuthService service = null;
		
		if (providerConfig == null) {
			throw new Exception("No <provider> defined under <oauth>. Cannot create OAuth Service Provider.");
		}
		
		if (!oauthConfig.isProvider()) {
			if (consumerConfig == null) {
				throw new Exception("No <consumer> defined under <oauth>. Cannot create OAuth Consumer.");
			}
			
			service = OAuthServiceAbstractFactory.getOAuthServiceFactory(oauthConfig.getVersion()).createOAuthConsumerService(oauthConfig.getName(), serviceClass, providerConfig, consumerConfig);
		} else {
			service = OAuthServiceAbstractFactory.getOAuthServiceFactory(oauthConfig.getVersion()).createOAuthProviderService(oauthConfig.getName(), serviceClass, providerConfig);
		}
		
		if (service == null) {
			throw new Exception("Strange! No service found for class '" + serviceConfig.getServiceClass() + "'");
		}
		
		//execute the service.
		service.execute(request, response);
		
		//Finally
		SuccessConfig successConfig = serviceConfig.getSuccessConfig();
		if (successConfig != null) {
			if (logger.isInfoEnabled()) {
				logger.info("Dispatching to path \"" + successConfig.getPath() + "\".");
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(successConfig.getPath());
			dispatcher.forward(request, response);
		}
	}
}
