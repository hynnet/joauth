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

import java.io.Serializable;

/**
 * @author Bienfait Sindi
 * @since 27 November 2010
 *
 */
public class ServiceConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1172982753650898515L;
	private String path;
	private String serviceClass;
	private String refOAuth;
	private SuccessConfig successConfig;

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the serviceClass
	 */
	public String getServiceClass() {
		return serviceClass;
	}

	/**
	 * @param serviceClass the serviceClass to set
	 */
	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}
	
	/**
	 * @return the refOAuth
	 */
	public String getRefOAuth() {
		return refOAuth;
	}

	/**
	 * @param refOAuth the refOAuth to set
	 */
	public void setRefOAuth(String refOAuth) {
		this.refOAuth = refOAuth;
	}

	/**
	 * @return the successConfig
	 */
	public SuccessConfig getSuccessConfig() {
		return successConfig;
	}

	/**
	 * @param successConfig the successConfig to set
	 */
	public void setSuccessConfig(SuccessConfig successConfig) {
		this.successConfig = successConfig;
	}
}
