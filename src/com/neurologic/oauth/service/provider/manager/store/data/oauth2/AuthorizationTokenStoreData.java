/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store.data.oauth2;

import com.neurologic.oauth.service.provider.manager.store.StoreData;

/**
 * @author Buhake Sindi
 * @since 19 September 2011
 *
 */
public class AuthorizationTokenStoreData extends StoreData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3634600984384522070L;
	private String code;
	private String redirectUri;
	private String scope;
	private String state;
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return the redirectUri
	 */
	public String getRedirectUri() {
		return redirectUri;
	}
	
	/**
	 * @param redirectUri the redirectUri to set
	 */
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
}
