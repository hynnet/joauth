/**
 * 
 */
package com.neurologic.oauth.service.factory;

import com.neurologic.oauth.service.factory.impl.OAuth1ServiceFactory;
import com.neurologic.oauth.service.factory.impl.OAuth2ServiceFactory;

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
