/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store.data;

import com.neurologic.oauth.service.provider.manager.store.StoreData;

/**
 * @author Buhake Sindi
 * @since 29 August 2011
 *
 */
public class ConsumerKeyStoreData extends StoreData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3395959358305392184L;
	private String consumerKey;
	private String consumerSecret; 
	
	{
		super.setMaximumValidity(-1);
	}
	
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
	 * @return the consumerSecret
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}
	
	/**
	 * @param consumerSecret the consumerSecret to set
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}
}
