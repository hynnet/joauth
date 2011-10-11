/**
 * 
 */
package com.neurologic.oauth.service.request.authentication;

import net.oauth.enums.TokenType;

/**
 * @author Buhake Sindi
 * @since 28 September 2011
 *
 */
public abstract class OAuth2HttpAuthorizationChallenger<T> extends AbstractHttpAuthorizationChallenger<T> {

	/**
	 * For OAuth 2 Draft 10 <b>only</b> (where the auth-scheme is <code>OAuth</code>).
	 */
	protected OAuth2HttpAuthorizationChallenger() {
		// TODO Auto-generated constructor stub
		super("OAuth", false);
	}

	/**
	 * Assigns an auth-scheme based on the {@link TokenType} (<code>Bearer</code>, <code>MAC</code>) on the extractor.
	 */
	protected OAuth2HttpAuthorizationChallenger(TokenType tokenType) {
		super(tokenType.toString(), true);
		// TODO Auto-generated constructor stub
	}
}
