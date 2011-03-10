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
