/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store;

import java.io.Serializable;

/**
 * @author Buhake Sindi
 * @since 29 August 2011
 *
 */
public class StoreData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2064248275094867220L;
	private String consumerKey;
	private long creationTime;
	private long maximumValidity;
	
	/**
	 * @return the consumerKey
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	/**
	 * @param consumerKey the consumerKey to set
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * @return the creationTime
	 */
	public long getCreationTime() {
		return creationTime;
	}
	
	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the maximumValidity
	 */
	public long getMaximumValidity() {
		return maximumValidity;
	}

	/**
	 * @param maximumValidity the maximumValidity to set
	 */
	public void setMaximumValidity(long maximumValidity) {
		this.maximumValidity = maximumValidity;
	}
}
