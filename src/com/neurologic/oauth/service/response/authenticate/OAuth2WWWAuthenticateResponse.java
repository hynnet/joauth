/**
 * 
 */
package com.neurologic.oauth.service.response.authenticate;

import net.oauth.enums.TokenType;

import com.neurologic.oauth.service.response.authenticate.parameters.OAuth2WWWAuthenticateParameters;


/**
 * @author Buhake Sindi
 * @since 26 September 2011
 *
 */
public class OAuth2WWWAuthenticateResponse extends AbstractWWWAuthenticateResponse<OAuth2WWWAuthenticateParameters> {

	/**
	 * 
	 * @param tokenType
	 * @param authenticateParameters
	 */
	public OAuth2WWWAuthenticateResponse(TokenType tokenType, OAuth2WWWAuthenticateParameters authenticateParameters) {
		super(tokenType.toString(), authenticateParameters);
		// TODO Auto-generated constructor stub
	}	
}
