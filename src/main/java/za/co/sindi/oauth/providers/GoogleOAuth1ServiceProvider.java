/**
 * 
 */
package za.co.sindi.oauth.providers;

import net.oauth.provider.OAuth1ServiceProvider;

/**
 * @author Buhake Sindi
 * @since 30 August 2011
 *
 */
public class GoogleOAuth1ServiceProvider extends OAuth1ServiceProvider {

	private static final String GOOGLE_OAUTH_URL_PATH = "https://www.google.com/accounts/";
	
	/**
	 * 
	 */
	public GoogleOAuth1ServiceProvider() {
		super(GOOGLE_OAUTH_URL_PATH + "OAuthGetRequestToken", GOOGLE_OAUTH_URL_PATH + "OAuthAuthorizeToken", GOOGLE_OAUTH_URL_PATH + "OAuthGetAccessToken");
		// TODO Auto-generated constructor stub
	}
}
