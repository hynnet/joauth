/**
 * 
 */
package za.co.sindi.oauth.providers;

import net.oauth.provider.OAuth2ServiceProvider;

/**
 * @author Buhake Sindi
 * @since 21 September 2011
 *
 */
public class GowallaServiceProvider extends OAuth2ServiceProvider {
	
	private static final String GOWALLA_OAUTH_URL_PATH = "https://api.gowalla.com/api/oauth/";
	
	/**
	 * Gowalla OAuth2 Service Provider
	 */
	public GowallaServiceProvider() {
		super(GOWALLA_OAUTH_URL_PATH + "new", GOWALLA_OAUTH_URL_PATH + "token");
		// TODO Auto-generated constructor stub
	}
}
