/**
 * 
 */
package com.neurologic.oauth.service.response.authenticate;

import com.neurologic.oauth.service.response.authenticate.parameters.OAuth1WWWAuthenticateParameters;


/**
 * @author Buhake Sindi
 * @since 26 September 2011
 *
 */
public class OAuth1WWWAuthenticateResponse extends AbstractWWWAuthenticateResponse<OAuth1WWWAuthenticateParameters> {

	/**
	 * @param authenticateParameters
	 */
	public OAuth1WWWAuthenticateResponse(OAuth1WWWAuthenticateParameters authenticateParameters) {
		super("OAuth", authenticateParameters);
		// TODO Auto-generated constructor stub
	}	
}
