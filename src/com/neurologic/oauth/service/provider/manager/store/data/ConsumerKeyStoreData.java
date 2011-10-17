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
	private String applicationName;
	private String applicationDescription;
	private String applicationUrl;
	
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

	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * @param applicationName the applicationName to set
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * @return the applicationDescription
	 */
	public String getApplicationDescription() {
		return applicationDescription;
	}

	/**
	 * @param applicationDescription the applicationDescription to set
	 */
	public void setApplicationDescription(String applicationDescription) {
		this.applicationDescription = applicationDescription;
	}

	/**
	 * @return the applicationUrl
	 */
	public String getApplicationUrl() {
		return applicationUrl;
	}

	/**
	 * @param applicationUrl the applicationUrl to set
	 */
	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
