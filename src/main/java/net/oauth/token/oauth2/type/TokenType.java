/**
 * 
 */
package net.oauth.token.oauth2.type;

import net.oauth.exception.OAuthAuthorizationException;
import net.oauth.token.oauth2.AccessToken;


/**
 * @author Buhake Sindi
 * @since 21 September 2011
 *
 */
public interface TokenType {

	public net.oauth.enums.TokenType getType();
	public String generateValue(AccessToken accessToken) throws OAuthAuthorizationException;
}
