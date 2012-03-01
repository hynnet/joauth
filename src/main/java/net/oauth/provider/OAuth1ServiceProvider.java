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
package net.oauth.provider;

/**
 * @author Bienfait Sindi
 * @since 31 March 2010
 *
 */
public class OAuth1ServiceProvider extends OAuthServiceProvider {

	public static final String PROTOCOL_VERSION = "1.0";
	
	private String requestTokenUrl;
	
	/**
	 * @param requestTokenUrl
	 * @param authorizationUrl
	 * @param accessTokenUrl
	 */
	public OAuth1ServiceProvider(String requestTokenUrl, String authorizationUrl, String accessTokenUrl) {
		super(authorizationUrl, accessTokenUrl);
		setRequestTokenUrl(requestTokenUrl);
	}
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getName() + " [getRequestTokenUrl()="
				+ getRequestTokenUrl() + ", getAuthorizationUrl()="
				+ getAuthorizationUrl() + ", getAccessTokenUrl()="
				+ getAccessTokenUrl() + "]";
	}
}
