/**
 * 
 */
package com.neurologic.oauth.service.provider.manager;

import org.apache.log4j.Logger;

import com.neurologic.oauth.service.provider.generator.TokenStringGenerator;
import com.neurologic.oauth.service.provider.manager.store.DataStore;
import com.neurologic.oauth.service.provider.manager.store.data.ConsumerKeyStoreData;

/**
 * @author Buhake Sindi
 * @since 01 September 2011
 *
 */
public abstract class AbstractOAuthTokenManager implements OAuthTokenManager {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private DataStore<ConsumerKeyStoreData> consumerKeyStore;
	private TokenStringGenerator accessTokenGenerator;
	private long authorizedTokenValidity = 30 * 60 * 1000;	//30 minutes
	private long accessTokenValidity = 24 * 60 * 60 * 1000;	//24 hours
	
	/**
	 * @return the consumerKeyStore
	 */
	public DataStore<ConsumerKeyStoreData> getConsumerKeyStore() {
		return consumerKeyStore;
	}
	
	/**
	 * @param consumerKeyStore the consumerKeyStore to set
	 */
	public void setConsumerKeyStore(DataStore<ConsumerKeyStoreData> consumerKeyStore) {
		this.consumerKeyStore = consumerKeyStore;
	}
	
	/**
	 * @return the accessTokenGenerator
	 */
	public TokenStringGenerator getAccessTokenGenerator() {
		return accessTokenGenerator;
	}
	
	/**
	 * @param accessTokenGenerator the accessTokenGenerator to set
	 */
	public void setAccessTokenGenerator(TokenStringGenerator accessTokenGenerator) {
		this.accessTokenGenerator = accessTokenGenerator;
	}
	
	/**
	 * @return the authorizedTokenValidity
	 */
	public long getAuthorizedTokenValidity() {
		return authorizedTokenValidity;
	}
	
	/**
	 * @param authorizedTokenValidity the authorizedTokenValidity to set
	 */
	public void setAuthorizedTokenValidity(long authorizedTokenValidity) {
		this.authorizedTokenValidity = authorizedTokenValidity;
	}
	
	/**
	 * @return the accessTokenValidity
	 */
	public long getAccessTokenValidity() {
		return accessTokenValidity;
	}
	
	/**
	 * @param accessTokenValidity the accessTokenValidity to set
	 */
	public void setAccessTokenValidity(long accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}
}
