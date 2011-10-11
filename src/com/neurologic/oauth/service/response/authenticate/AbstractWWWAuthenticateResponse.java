/**
 * 
 */
package com.neurologic.oauth.service.response.authenticate;

import com.neurologic.oauth.service.response.authenticate.parameters.WWWAuthenticateParameters;

/**
 * @author Buhake Sindi
 * @since 05 October 2011
 *
 */
public abstract class AbstractWWWAuthenticateResponse<T extends WWWAuthenticateParameters> implements WWWAuthenticateResponse {

	private String challenge;
	private T authenticateParameters;
	
	/**
	 * @param challenge
	 * @param authenticateParameters
	 */
	protected AbstractWWWAuthenticateResponse(String challenge,T authenticateParameters) {
		super();
		this.challenge = challenge;
		this.authenticateParameters = authenticateParameters;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.authenticate.WWWAuthenticateResponse#getChallenge()
	 */
	@Override
	public String getChallenge() {
		// TODO Auto-generated method stub
		return challenge;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.authenticate.WWWAuthenticateResponse#getValue()
	 */
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return getChallenge() + " " + authenticateParameters.toString();
	}
}
