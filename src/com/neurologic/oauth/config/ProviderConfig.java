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
public class ProviderConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1455414418461801128L;
	private String requestTokenUrl;
	private String authorizationUrl;
	private String accessTokenUrl;
	private String className;
	
	/**
	 * @return the requestTokenUrl
	 */
	public String getRequestTokenUrl() {
		return requestTokenUrl;
	}
	
	/**
	 * @param requestTokenUrl the requestTokenUrl to set
	 */
	public void setRequestTokenUrl(String requestTokenUrl) {
		this.requestTokenUrl = requestTokenUrl;
	}
	
	/**
	 * @return the authorizationUrl
	 */
	public String getAuthorizationUrl() {
		return authorizationUrl;
	}
	
	/**
	 * @param authorizationUrl the authorizationUrl to set
	 */
	public void setAuthorizationUrl(String authorizationUrl) {
		this.authorizationUrl = authorizationUrl;
	}
	
	/**
	 * @return the accessTokenUrl
	 */
	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}
	
	/**
	 * @param accessTokenUrl the accessTokenUrl to set
	 */
	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accessTokenUrl == null) ? 0 : accessTokenUrl.hashCode());
		result = prime
				* result
				+ ((authorizationUrl == null) ? 0 : authorizationUrl.hashCode());
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result
				+ ((requestTokenUrl == null) ? 0 : requestTokenUrl.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ProviderConfig)) {
			return false;
		}
		ProviderConfig other = (ProviderConfig) obj;
		if (accessTokenUrl == null) {
			if (other.accessTokenUrl != null) {
				return false;
			}
		} else if (!accessTokenUrl.equals(other.accessTokenUrl)) {
			return false;
		}
		if (authorizationUrl == null) {
			if (other.authorizationUrl != null) {
				return false;
			}
		} else if (!authorizationUrl.equals(other.authorizationUrl)) {
			return false;
		}
		if (className == null) {
			if (other.className != null) {
				return false;
			}
		} else if (!className.equals(other.className)) {
			return false;
		}
		if (requestTokenUrl == null) {
			if (other.requestTokenUrl != null) {
				return false;
			}
		} else if (!requestTokenUrl.equals(other.requestTokenUrl)) {
			return false;
		}
		return true;
	}
}
