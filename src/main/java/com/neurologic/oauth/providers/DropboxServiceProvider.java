/**
 * 
 */
package com.neurologic.oauth.providers;

import net.oauth.provider.OAuth1ServiceProvider;

/**
 * @author Buhake Sindi
 * @since 12 July 2011
 *
 */
public class DropboxServiceProvider extends OAuth1ServiceProvider {

	private static final String DROPBOX_OAUTH_URL_PATH = "https://api.dropbox.com/0/oauth/";
	
	/**
	 * 
	 */
	public DropboxServiceProvider() {
		super(DROPBOX_OAUTH_URL_PATH + "request_token", DROPBOX_OAUTH_URL_PATH + "authorize", DROPBOX_OAUTH_URL_PATH + "access_token");
		// TODO Auto-generated constructor stub
	}
}
