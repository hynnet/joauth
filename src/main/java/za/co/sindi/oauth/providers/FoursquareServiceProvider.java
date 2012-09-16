/**
 * 
 */
package za.co.sindi.oauth.providers;

import net.oauth.provider.OAuth1ServiceProvider;

/**
 * @author Buhake Sindi
 * @since 25 August 2011
 *
 */
public class FoursquareServiceProvider extends OAuth1ServiceProvider {

	private static final String FOURSQUARE_OAUTH_URL_PATH = "http://foursquare.com/oauth/";

	/**
	 * Constructor for the Foursquare OAuth Service Provider.
	 */
	public FoursquareServiceProvider() {
		super(FOURSQUARE_OAUTH_URL_PATH + "request_token", FOURSQUARE_OAUTH_URL_PATH + "authorize", FOURSQUARE_OAUTH_URL_PATH + "access_token");
		// TODO Auto-generated constructor stub
	}
}
