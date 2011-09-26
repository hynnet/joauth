/**
 * 
 */
package com.neurologic.oauth.service;


/**
 * @author Buhake Sindi
 * @since 24 September 2011
 *
 */
public abstract class AbstractOAuthService implements OAuthService {

	private String oauthName;
	private boolean nameSet;
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.OAuthService#setOAuthName(java.lang.String)
	 */
	@Override
	public void setOAuthName(String oauthName) {
		// TODO Auto-generated method stub
		if (nameSet) {
			throw new IllegalArgumentException("OAuth name has been set.");
		}
		
		this.oauthName = oauthName;
		nameSet = true;
	}

	/**
	 * @return the oauthName
	 */
	protected String getOauthName() {
		return oauthName;
	}
}
