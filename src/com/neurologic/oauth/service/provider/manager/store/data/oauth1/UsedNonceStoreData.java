/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store.data.oauth1;

import com.neurologic.oauth.service.provider.manager.store.StoreData;

/**
 * @author Buhake Sindi
 * @since 31 August 2011
 *
 */
public class UsedNonceStoreData extends StoreData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1680010893827700882L;
	private String consumerKey;
	private long timestamp;
	private String nonce;
	private String token;
	
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
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * @return the nonce
	 */
	public String getNonce() {
		return nonce;
	}

	/**
	 * @param nonce the nonce to set
	 */
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
}
