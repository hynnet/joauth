/**
 * 
 */
package com.neurologic.oauth.store;

/**
 * @author Buhake Sindi
 * @since 13 July 2011
 *
 */
public interface Entity<T> {

	public long getCreationTime();
	public void setCreationTime(long creationTime);
	public long getMaxiumTimeToLiveInterval();
	public void setMaximumTimeToLiveInterval(long maximumTimeToLiveInterval);
}
