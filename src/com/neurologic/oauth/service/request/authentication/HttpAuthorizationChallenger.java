/**
 * 
 */
package com.neurologic.oauth.service.request.authentication;

import com.neurologic.exception.OAuthAuthorizationException;

/**
 * @author Buhake Sindi
 * @since 06 October 2011
 *
 */
public interface HttpAuthorizationChallenger<T> {

	public T processChallenge(String authorizationString) throws OAuthAuthorizationException;
}
