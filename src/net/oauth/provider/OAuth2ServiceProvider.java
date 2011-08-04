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
 * @since 24 July 2010
 *
 */
public class OAuth2ServiceProvider extends OAuthServiceProvider {
	
	/**
	 * @param authorizationUrl
	 * @param accessTokenUrl
	 */
	public OAuth2ServiceProvider(String authorizationUrl, String accessTokenUrl) {
		super(authorizationUrl, accessTokenUrl);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getName() + " [getAuthorizationUrl()="
				+ getAuthorizationUrl() + ", getAccessTokenUrl()="
				+ getAccessTokenUrl() + "]";
	}
}
