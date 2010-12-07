/**
 * 
 */
package net.oauth.provider;

/**
 * @author Bienfait Sindi
 * @since 24 July 2010
 *
 */
public class OAuth2ServiceProvider {

	private String authorizationUrl;
	private String accessTokenUrl;
	
	/**
	 * @param authorizationUrl
	 * @param accessTokenUrl
	 */
	public OAuth2ServiceProvider(String authorizationUrl, String accessTokenUrl) {
		this.authorizationUrl = authorizationUrl;
		this.accessTokenUrl = accessTokenUrl;
	}
	
	/**
	 * @return the authorizationUrl
	 */
	public String getAuthorizationUrl() {
		return authorizationUrl;
	}
	
	/**
	 * @param authorizationUrl the authorizationUrl to set
	 */
	public void setAuthorizationUrl(String authorizationUrl) {
		this.authorizationUrl = authorizationUrl;
	}
	
	/**
	 * @return the accessTokenUrl
	 */
	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}
	
	/**
	 * @param accessTokenUrl the accessTokenUrl to set
	 */
	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}
}
