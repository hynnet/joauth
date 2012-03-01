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
package com.neurologic.oauth.providers;

import net.oauth.provider.OAuth2ServiceProvider;

/**
 * @author Buhake Sindi
 * @since 30 June 2011
 *
 */
public class FacebookServiceProvider extends OAuth2ServiceProvider {

	private static final String FACEBOOK_OAUTH_URL_PATH = "https://graph.facebook.com/oauth/";
	
	/**
	 *
	 */
	public FacebookServiceProvider() {
		super(FACEBOOK_OAUTH_URL_PATH + "authorize", FACEBOOK_OAUTH_URL_PATH + "access_token");
		// TODO Auto-generated constructor stub
	}
}
