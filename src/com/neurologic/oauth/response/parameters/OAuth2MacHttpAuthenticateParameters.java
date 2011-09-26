/**
 * 
 */
package com.neurologic.oauth.response.parameters;

import net.oauth.enums.TokenType;

/**
 * @author Buhake Sindi
 * @since 26 September 2011
 *
 */
public class OAuth2MacHttpAuthenticateParameters extends OAuth2HttpAuthenticateParameters {

	/**
	 * This is the HTTP Authenticate header with <code>MAC</code> challenge.
	 */
	public OAuth2MacHttpAuthenticateParameters() {
		super(TokenType.MAC);
		// TODO Auto-generated constructor stub
	}
}
