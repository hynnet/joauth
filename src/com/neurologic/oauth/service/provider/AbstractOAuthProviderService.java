/**
 * 
 */
package com.neurologic.oauth.service.provider;

import java.net.HttpURLConnection;

import org.apache.log4j.Logger;

import com.neurologic.oauth.service.OAuthProviderService;
import com.neurologic.oauth.service.provider.manager.OAuthTokenManager;

/**
 * @author Buhake Sindi
 * @since 01 September 2011
 *
 */
public abstract class AbstractOAuthProviderService<TM extends OAuthTokenManager> implements OAuthProviderService<TM> {

	protected static final int HTTP_OK = HttpURLConnection.HTTP_OK;
	protected static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
	protected static final String CONTENT_TYPE_JSON = "application/json";
	protected final Logger logger = Logger.getLogger(this.getClass());
	private TM oauthTokenManager;

	/**
	 * @return the oauthTokenManager
	 */
	protected final TM getOauthTokenManager() {
		return oauthTokenManager;
	}

	/**
	 * @param oauthTokenManager the oauthTokenManager to set
	 */
	@Override
	public void setOauthTokenManager(TM oauthTokenManager) {
		this.oauthTokenManager = oauthTokenManager;
	}
}
