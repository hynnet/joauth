/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store.data.oauth1;


/**
 * @author Buhake Sindi
 * @since 30 August 2011
 *
 */
public class RequestTokenStoreData extends AbstractTokenStoreData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6804141132209629725L;
	private String callbackUrl;
	private boolean authorized = false;
	private String verifier = null;

	/**
	 * @return the callbackUrl
	 */
	public String getCallbackUrl() {
		return callbackUrl;
	}

	/**
	 * @param callbackUrl the callbackUrl to set
	 */
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	/**
	 * @return the authorized
	 */
	public boolean isAuthorized() {
		return authorized;
	}
	
	/**
	 * @param authorized the authorized to set
	 */
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	/**
	 * @return the verifier
	 */
	public String getVerifier() {
		return verifier;
	}

	/**
	 * @param verifier the verifier to set
	 */
	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
}
