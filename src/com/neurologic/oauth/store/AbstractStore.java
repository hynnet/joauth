/**
 * 
 */
package com.neurologic.oauth.store;

import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * @author Buhake Sindi
 * @since 02 August 2011
 *
 */
public abstract class AbstractStore<T> implements Store<T> {

	protected final Logger logger = Logger.getLogger(this.getClass());
	protected Class<T> parameterizedClassType = null;
	
	/**
	 * @return the parameterizedClassType
	 */
	protected Class<T> getParameterizedClassType() {
		return parameterizedClassType;
	}

	/**
	 * @param parameterizedClassType the parameterizedClassType to set
	 */
	protected void setParameterizedClassType(Class<T> parameterizedClassType) {
		this.parameterizedClassType = parameterizedClassType;
	}

	/**
	 * Remove all expired entities from the database.
	 */
	public void removeExpiredEntities()  {
		long now = System.currentTimeMillis();
		try {
			String[] keys = keys();
			if (keys != null && keys.length > 0) {
				for (String key : keys) {
					Entity<T> entity = get(key);
					if (entity != null) {
						int timeToLive = (int) ((now - entity.getCreationTime()) / 1000L);
						if (timeToLive < entity.getMaxiumTimeToLiveInterval()) {
							continue;
						}
						
						//Remove it from session.
						if (logger.isInfoEnabled()) {
							String className = getParameterizedClassType() != null ? getParameterizedClassType().getName() : "";
							logger.info("Removing entity " + className + " with ID: '" + key + "'.");
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
