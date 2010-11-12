/**
 * 
 */
package net.oauth.provider;

/**
 * @author Bienfait Sindi
 * @since 31 March 2010
 *
 */
public class OAuthServiceProvider {

	public static final String PROTOCOL_VERSION = "1.0";
	
	private String requestTokenUrl;
	private String userAuthorizationUrl;
	private String accessTokenUrl;
	
	/**
	 * @param requestTokenUrl
	 * @param userAuthorizationUrl
	 * @param accessTokenUrl
	 */
	public OAuthServiceProvider(String requestTokenUrl, String userAuthorizationUrl, String accessTokenUrl) {
		this.requestTokenUrl = requestTokenUrl;
		this.userAuthorizationUrl = userAuthorizationUrl;
		this.accessTokenUrl = accessTokenUrl;
	}
	
	/**
	 * @return the requestTokenUrl
	 */
	public String getRequestTokenUrl() {
		return requestTokenUrl;
	}
	
	/**
	 * @param requestTokenUrl the requestTokenUrl to set
	 */
	public void setRequestTokenUrl(String requestTokenUrl) {
		this.requestTokenUrl = requestTokenUrl;
	}
	
	/**
	 * @return the userAuthorizationUrl
	 */
	public String getUserAuthorizationUrl() {
		return userAuthorizationUrl;
	}
	
	/**
	 * @param userAuthorizationUrl the userAuthorizationUrl to set
	 */
	public void setUserAuthorizationUrl(String userAuthorizationUrl) {
		this.userAuthorizationUrl = userAuthorizationUrl;
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
