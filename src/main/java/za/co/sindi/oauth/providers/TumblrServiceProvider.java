/**
 * 
 */
package za.co.sindi.oauth.providers;

import net.oauth.provider.OAuth1ServiceProvider;

/**
 * @author Buhake Sindi
 * @since 11 October 2011
 *
 */
public class TumblrServiceProvider extends OAuth1ServiceProvider {

	private static final String TUMBLR_OAUTH_URL_PATH = "http://www.tumblr.com/oauth/";
	
	/**
	 * 
	 */
	public TumblrServiceProvider() {
		super(TUMBLR_OAUTH_URL_PATH + "request_token", TUMBLR_OAUTH_URL_PATH + "authorize", TUMBLR_OAUTH_URL_PATH + "access_token");
		// TODO Auto-generated constructor stub
	}
}
