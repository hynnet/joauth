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
package net.oauth.provider.providers;

import net.oauth.provider.OAuth1ServiceProvider;

/**
 * @author Buhake Sindi
 * @since 29 June 2011
 *
 */
public class YahooServiceProvider extends OAuth1ServiceProvider {

	private static final String YAHOO_OAUTH_URL_PATH = "https://api.login.yahoo.com/oauth/v2/";
	
	/**
	 * 
	 */
	public YahooServiceProvider() {
		super(YAHOO_OAUTH_URL_PATH + "get_request_token", YAHOO_OAUTH_URL_PATH + "request_auth", YAHOO_OAUTH_URL_PATH + "get_token");
		// TODO Auto-generated constructor stub
	}
}
