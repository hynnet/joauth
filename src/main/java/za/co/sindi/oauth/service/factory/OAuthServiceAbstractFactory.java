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
package za.co.sindi.oauth.service.factory;

import za.co.sindi.oauth.service.factory.impl.OAuth1ServiceFactory;
import za.co.sindi.oauth.service.factory.impl.OAuth2ServiceFactory;

/**
 * @author Bienfait Sindi
 * @since 06 December 2010
 *
 */
public class OAuthServiceAbstractFactory {

	/**
	 * 
	 */
	private OAuthServiceAbstractFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static OAuthServiceFactory getOAuthServiceFactory(int oauthVersion) {
		switch(oauthVersion) {
			case 1:
				return new OAuth1ServiceFactory();
				
			case 2:
				return new OAuth2ServiceFactory();
		}
		
		return null;
	}
}
