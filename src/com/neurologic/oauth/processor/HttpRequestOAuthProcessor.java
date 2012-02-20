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

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neurologic.oauth.config.ConsumerConfig;
import com.neurologic.oauth.config.ManagerConfig;
import com.neurologic.oauth.config.ModuleConfig;
import com.neurologic.oauth.config.OAuthConfig;
import com.neurologic.oauth.config.ProviderConfig;
import com.neurologic.oauth.config.ServiceConfig;
import com.neurologic.oauth.config.SuccessConfig;
import com.neurologic.oauth.service.Service;
import com.neurologic.oauth.service.factory.OAuthServiceAbstractFactory;

/**
 * @author Bienfait Sindi
 * @since 23 November 2010
 *
 */
public class HttpRequestOAuthProcessor extends GenericOAuthProcessor {

	//This is where we keep all our created services.
	private Map<String, Service> services = new LinkedHashMap<String, Service>();

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.processor.OAuthProcessor#init(com.neurologic.oauth.config.ModuleConfig)
	 */
	@Override
	public void init(ModuleConfig moduleConfig) {
		// TODO Auto-generated method stub
		super.init(moduleConfig);
		
		synchronized (services) {
			services.clear();
		}
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.processor.OAuthProcessor#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		synchronized (services) {
			services.clear();
		}
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.processor.OAuthProcessor#process(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	public void process(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		process((HttpServletRequest)request, (HttpServletResponse)response);
	}

	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getPathInfo();
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieved path info \"" + path + "\".");
		}
		
		ServiceConfig serviceConfig = getModuleConfig().getServiceConfigByPath(path);
		if (serviceConfig == null) {
			throw new Exception("No <service> defined for path='" + path + "'.");
		}
		
		Service service = createOAuthService(serviceConfig);
		
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
	
	private Service createOAuthService(ServiceConfig serviceConfig) throws Exception {
		Service service = null;
		synchronized (services) {
			service = services.get(serviceConfig.getPath());
		}
	
		if (service == null) {
			if (logger.isInfoEnabled()) {
				logger.info("Creating service '" + serviceConfig.getServiceClass() + "' for service path '" + serviceConfig.getPath() + "'.");
			}
			
			String serviceClassName = serviceConfig.getServiceClass();
			if (serviceClassName == null || serviceClassName.isEmpty()) {
				throw new Exception("No service class defined.");
			}
			
			String oauthName = serviceConfig.getRefOAuth();
			if (oauthName == null || oauthName.isEmpty()) {
				throw new Exception("No service reference OAuth is defined.");
			}
			
			OAuthConfig oauthConfig = getModuleConfig().getOAuthConfigByName(serviceConfig.getRefOAuth());
			if (oauthConfig == null) {
				throw new Exception("No <oauth> defined with name='" + serviceConfig.getRefOAuth() + "'.");
			}
			
			ProviderConfig providerConfig = oauthConfig.getProviderConfig();
			
			if (providerConfig == null) {
				throw new Exception("No <provider> defined under <oauth>. Cannot create OAuth Service Provider.");
			}
			
			if (!oauthConfig.isProvider()) {
				ConsumerConfig consumerConfig = oauthConfig.getConsumerConfig();
				if (consumerConfig == null) {
					throw new Exception("No <consumer> defined under <oauth>. Cannot create OAuth Consumer.");
				}
				
				service = OAuthServiceAbstractFactory.getOAuthServiceFactory(oauthConfig.getVersion()).createOAuthConsumerService(oauthConfig.getName(), serviceClassName, providerConfig, consumerConfig);
			} else {
				ManagerConfig managerConfig = oauthConfig.getManagerConfig();
				if (managerConfig == null) {
					throw new Exception("No <manager> defined under <oauth>. Cannot create OAuth Manager.");
				}
				
				service = OAuthServiceAbstractFactory.getOAuthServiceFactory(oauthConfig.getVersion()).createOAuthProviderService(oauthConfig.getName(), serviceClassName, providerConfig, managerConfig);
			}
			
			if (service == null) {
				throw new Exception("Strange! No service found for class '" + serviceConfig.getServiceClass() + "'");
			}
			
			//save this instantiated service.
			services.put(serviceConfig.getPath(), service);
		}
		
		return service;
	}
}
