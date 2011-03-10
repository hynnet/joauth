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
public class OAuth1ServiceProvider {

	public static final String PROTOCOL_VERSION = "1.0";
	
	private String requestTokenUrl;
	private String userAuthorizationUrl;
	private String accessTokenUrl;
	
	/**
	 * @param requestTokenUrl
	 * @param userAuthorizationUrl
	 * @param accessTokenUrl
	 */
	public OAuth1ServiceProvider(String requestTokenUrl, String userAuthorizationUrl, String accessTokenUrl) {
		this.requestTokenUrl = requestTokenUrl;
		this.userAuthorizationUrl = userAuthorizationUrl;
		this.accessTokenUrl = accessTokenUrl;
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
	
	/**
	 * @return the userAuthorizationUrl
	 */
	public String getUserAuthorizationUrl() {
		return userAuthorizationUrl;
	}
	
	/**
	 * @param userAuthorizationUrl the userAuthorizationUrl to set
	 */
	public void setUserAuthorizationUrl(String userAuthorizationUrl) {
		this.userAuthorizationUrl = userAuthorizationUrl;
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
}
