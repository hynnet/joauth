/**
 * 
 */
package net.oauth.authorization.builder;

import net.oauth.authorization.OAuthProtectedResourceAuthorization;

import com.neurologic.exception.OAuthAuthorizationException;

/**
 * @author Buhake Sindi
 * @since 21 September 2011
 *
 */
public interface OAuthAuthorizationBuilder<T extends OAuthProtectedResourceAuthorization> {

	public T build() throws OAuthAuthorizationException;
}
