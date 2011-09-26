/**
 * 
 */
package net.oauth.provider.providers;

import net.oauth.provider.OAuth2ServiceProvider;

/**
 * @author Buhake Sindi
 * @since 21 September 2011
 *
 */
public class WindowsLiveServiceProvider extends OAuth2ServiceProvider {

	private static final String WINDOWSLIVE_OAUTH_URL_PATH = "https://oauth.live.com/authorize";
	
	/**
	 * Windows Live Service Provider constructor.
	 */
	public WindowsLiveServiceProvider() {
		super(WINDOWSLIVE_OAUTH_URL_PATH, WINDOWSLIVE_OAUTH_URL_PATH);
		// TODO Auto-generated constructor stub
	}
}
