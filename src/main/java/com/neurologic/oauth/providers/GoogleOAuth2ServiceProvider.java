/**
 * 
 */
package com.neurologic.oauth.providers;

import net.oauth.provider.OAuth2ServiceProvider;

/**
 * @author Buhake Sindi
 * @since 30 August 2011
 *
 */
public class GoogleOAuth2ServiceProvider extends OAuth2ServiceProvider {

	private static final String GOOGLE_OAUTH_URL_PATH = "https://accounts.google.com/o/oauth2/auth";
	
	/**
	 * 
	 */
	public GoogleOAuth2ServiceProvider() {
		super(GOOGLE_OAUTH_URL_PATH, GOOGLE_OAUTH_URL_PATH);
		// TODO Auto-generated constructor stub
	}
}
