/**
 * 
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

/**
 * @author Bienfait Sindi
 * @since 23 November 2010
 *
 */
public class OAuthProcessor {

	private static final Logger logger = Logger.getLogger(OAuthProcessor.class);
	private ModuleConfig module;
	
	/**
	 * @param module
	 */
	public OAuthProcessor(ModuleConfig module) {
		super();
		this.module = module;
	}

	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getPathInfo();
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieved path info \"" + path + "\".");
		}
		
		ServiceConfig serviceConfig = module.getServiceConfigByPath(path);
		if (serviceConfig == null) {
			throw new Exception("No <service> defined for path='" + path + "'.");
		}
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = this.getClass().getClassLoader();
		}
		
		Class<?> clazz = classLoader.loadClass(serviceConfig.getServiceClass());
		if (clazz == null) {
			throw new Exception("No class exits for " + serviceConfig.getServiceClass());
		}
		
		OAuthConfig oauthConfig = module.getOAuthConfigByName(serviceConfig.getRefOAuth());
		if (oauthConfig == null) {
			throw new Exception("No <oauth> defined with name='" + serviceConfig.getRefOAuth() + "'.");
		}
		
		ProviderConfig providerConfig = oauthConfig.getProvider();
		ConsumerConfig consumerConfig = oauthConfig.getConsumer();
		if (providerConfig == null) {
			throw new Exception("No <provider> defined under <oauth>. Cannot create OAuth Service Provider.");
		}
		
		if (consumerConfig == null) {
			throw new Exception("No <consumer> defined under <oauth>. Cannot create OAuth Consumer.");
		}
		
		OAuthService<?> service = OAuthServiceAbstractFactory.getOAuthServiceFactory(oauthConfig.getVersion()).createOAuthService(clazz, providerConfig, consumerConfig);
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
