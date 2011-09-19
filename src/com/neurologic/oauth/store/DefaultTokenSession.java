/**
 * 
 */
package com.neurologic.oauth.store;

import net.oauth.token.OAuthTokenBase;

/**
 * @author Buhake Sindi
 * @since 10 August 2011
 *
 */
public class DefaultTokenSession implements TokenSession {

	private OAuthTokenBase token;
	private long creationTime;
	private long maximumTimeToLiveInterval;
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.store.Entity#setToken(net.oauth.token.OAuthTokenBase)
	 */
	@Override
	public void setToken(OAuthTokenBase token) {
		// TODO Auto-generated method stub
		this.token = token;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.store.Entity#getToken()
	 */
	@Override
	public OAuthTokenBase getToken() {
		// TODO Auto-generated method stub
		return token;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.store.Entity#getCreationTime()
	 */
	@Override
	public long getCreationTime() {
		// TODO Auto-generated method stub
		return creationTime;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.store.Entity#setCreationTime(long)
	 */
	@Override
	public void setCreationTime(long creationTime) {
		// TODO Auto-generated method stub
		this.creationTime = creationTime;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.store.Entity#getMaximumTimeToLiveInterval()
	 */
	@Override
	public long getMaximumTimeToLiveInterval() {
		// TODO Auto-generated method stub
		return maximumTimeToLiveInterval;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.store.Entity#setMaximumTimeToLiveInterval(long)
	 */
	@Override
	public void setMaximumTimeToLiveInterval(long maximumTimeToLiveInterval) {
		// TODO Auto-generated method stub
		this.maximumTimeToLiveInterval = maximumTimeToLiveInterval;
	}
}
