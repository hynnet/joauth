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
public class AuthorizationCodeStoreData extends StoreData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3634600984384522070L;
	private String code;
	private String redirectUri;
	
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
}
