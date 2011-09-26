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
public class OAuthConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5799834794283130836L;
	private String name;
	private int version;
	private boolean provider = false;
	private ConsumerConfig consumerConfig;
	private ProviderConfig providerConfig;
	private ManagerConfig managerConfig;
	private AccessConfig accessConfig;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}
	
	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	
	/**
	 * @return the provider
	 */
	public boolean isProvider() {
		return provider;
	}
	
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(boolean provider) {
		this.provider = provider;
	}
	
	/**
	 * @return the consumerConfig
	 */
	public ConsumerConfig getConsumerConfig() {
		return consumerConfig;
	}
	
	/**
	 * @param consumerConfig the consumerConfig to set
	 */
	public void setConsumerConfig(ConsumerConfig consumerConfig) {
		this.consumerConfig = consumerConfig;
	}
	
	/**
	 * @return the providerConfig
	 */
	public ProviderConfig getProviderConfig() {
		return providerConfig;
	}
	
	/**
	 * @param providerConfig the providerConfig to set
	 */
	public void setProviderConfig(ProviderConfig providerConfig) {
		this.providerConfig = providerConfig;
	}

	/**
	 * @return the managerConfig
	 */
	public ManagerConfig getManagerConfig() {
		return managerConfig;
	}

	/**
	 * @param managerConfig the managerConfig to set
	 */
	public void setManagerConfig(ManagerConfig managerConfig) {
		this.managerConfig = managerConfig;
	}

	/**
	 * @return the accessConfig
	 */
	public AccessConfig getAccessConfig() {
		return accessConfig;
	}

	/**
	 * @param accessConfig the accessConfig to set
	 */
	public void setAccessConfig(AccessConfig accessConfig) {
		this.accessConfig = accessConfig;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accessConfig == null) ? 0 : accessConfig.hashCode());
		result = prime * result
				+ ((consumerConfig == null) ? 0 : consumerConfig.hashCode());
		result = prime * result
				+ ((managerConfig == null) ? 0 : managerConfig.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (provider ? 1231 : 1237);
		result = prime * result
				+ ((providerConfig == null) ? 0 : providerConfig.hashCode());
		result = prime * result + version;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OAuthConfig other = (OAuthConfig) obj;
		if (accessConfig == null) {
			if (other.accessConfig != null)
				return false;
		} else if (!accessConfig.equals(other.accessConfig))
			return false;
		if (consumerConfig == null) {
			if (other.consumerConfig != null)
				return false;
		} else if (!consumerConfig.equals(other.consumerConfig))
			return false;
		if (managerConfig == null) {
			if (other.managerConfig != null)
				return false;
		} else if (!managerConfig.equals(other.managerConfig))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (provider != other.provider)
			return false;
		if (providerConfig == null) {
			if (other.providerConfig != null)
				return false;
		} else if (!providerConfig.equals(other.providerConfig))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
