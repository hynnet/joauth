/**
 * 
 */
package net.oauth.provider.providers;

import net.oauth.provider.OAuth2ServiceProvider;

/**
 * @author Buhake Sindi
 * @since 10 October 2011
 *
 */
public class SoundcloudServiceProvider extends OAuth2ServiceProvider {
	
	/**
	 * Default constructor.
	 */
	public SoundcloudServiceProvider() {
		super("https://soundcloud.com/connect", "https://api.soundcloud.com/oauth2/token");
		// TODO Auto-generated constructor stub
	}
}
