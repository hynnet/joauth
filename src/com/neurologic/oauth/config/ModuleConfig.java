/**
 * 
 */
package com.neurologic.oauth.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bienfait Sindi
 * @since 27 November 2010
 *
 */
public class ModuleConfig {

	private List<OAuthConfig> oauthConfigList;
	private List<ServiceConfig> serviceConfigList;
	
	public void addOAuthConfig(OAuthConfig config) {
		if (oauthConfigList == null) {
			oauthConfigList = new ArrayList<OAuthConfig>();
		}
		
		oauthConfigList.add(config);
	}
	
	public void addServiceConfig(ServiceConfig config) {
		if (serviceConfigList == null) {
			serviceConfigList = new ArrayList<ServiceConfig>();
		}
		
		serviceConfigList.add(config);
	}
	
	public OAuthConfig getOAuthConfigByName(String oauthName) {
		if (oauthConfigList != null) {
			for (OAuthConfig oauth : oauthConfigList) {
				if (oauthName.equals(oauth.getName())) {
					return oauth;
				}
			}
		}
		
		return null;
	}
	
	public boolean oauthConfigExists(String oauthName) {
		return getOAuthConfigByName(oauthName) != null;
	}
	
	public ServiceConfig getServiceConfigByPath(String servicePath) {
		if (serviceConfigList != null) {
			for (ServiceConfig service : serviceConfigList) {
				if (servicePath.equals(service.getPath())) {
					return service;
				}
			}
		}
		
		return null;
	}
	
	public boolean serviceConfigExists(String servicePath) {
		return getServiceConfigByPath(servicePath) != null;
	}
}
