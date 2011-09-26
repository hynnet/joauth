/**
 * 
 */
package net.oauth.authorization;

import com.neurologic.exception.OAuthAuthorizationException;

/**
 * @author Buhake Sindi
 * @since 21 September 2011
 *
 */
public interface OAuthProtectedResourceAuthorization {

	public String generateAuthorizationString() throws OAuthAuthorizationException;
//	public String generateWWWAuthenticateString();
}
