/**
 * 
 */
package com.neurologic.oauth.store;

import net.oauth.token.OAuthTokenBase;

/**
 * @author Buhake Sindi
 * @since 13 July 2011
 *
 */
public interface TokenSession {

	public void setToken(OAuthTokenBase oauthToken);
	public OAuthTokenBase getToken();
	public long getCreationTime();
	public void setCreationTime(long creationTime);
	public long getMaximumTimeToLiveInterval();
	public void setMaximumTimeToLiveInterval(long maximumTimeToLiveInterval);
}
