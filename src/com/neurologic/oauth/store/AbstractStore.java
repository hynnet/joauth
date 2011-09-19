/**
 * 
 */
package com.neurologic.oauth.store;

import java.io.IOException;

import net.oauth.token.OAuthTokenBase;

import org.apache.log4j.Logger;

/**
 * @author Buhake Sindi
 * @since 02 August 2011
 *
 */
public abstract class AbstractStore implements Store {

	protected final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @param session
	 * @throws IOException
	 */
	protected void createTokenSession(OAuthTokenBase token, long maximumTimeToLiveInterval) throws IOException {
		if (token != null) {
			TokenSession session = new DefaultTokenSession();
			session.setToken(token);
			session.setCreationTime(System.currentTimeMillis());
			session.setMaximumTimeToLiveInterval(maximumTimeToLiveInterval);
			
			//save it
			save(session);
		}
	}

	/**
	 * Remove all expired entities from the database.
	 */
	protected void removeExpiredEntities()  {
		long now = System.currentTimeMillis();
		try {
			String[] keys = keys();
			if (keys != null && keys.length > 0) {
				for (String key : keys) {
					TokenSession session = get(key);
					if (session != null) {
						int timeToLive = (int) ((now - session.getCreationTime()) / 1000L);
						if (timeToLive < session.getMaximumTimeToLiveInterval()) {
							continue;
						}
						
						//Remove it from session.
						if (logger.isInfoEnabled()) {
							logger.info("Removing token " + session.getToken().getClass().getName() + " with ID: '" + key + "'.");
						}
						
						remove(key);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("removeExpiredEntities() Exception: " + e.getLocalizedMessage(), e);
		}
	}
}
