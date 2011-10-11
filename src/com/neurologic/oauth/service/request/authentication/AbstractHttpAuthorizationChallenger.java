/**
 * 
 */
package com.neurologic.oauth.service.request.authentication;

import org.apache.log4j.Logger;

import com.neurologic.exception.OAuthAuthorizationException;

/**
 * @author Buhake Sindi
 * @since 28 September 2011
 *
 */
public abstract class AbstractHttpAuthorizationChallenger<T> implements HttpAuthorizationChallenger<T> {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private String authScheme;
	private boolean caseSensitive;
	
	/**
	 * @param authScheme
	 * @param caseSensitive whether the auth-scheme provided is case sensitive or not.
	 */
	protected AbstractHttpAuthorizationChallenger(String authScheme, boolean caseSensitive) {
		super();
		this.authScheme = ((authScheme == null || authScheme.isEmpty() ? "" : authScheme.trim()));
		this.caseSensitive = caseSensitive;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.request.authorization.HttpAuthorizationChallenger#processChallenge(String)
	 */
	@Override
	public T processChallenge(String authorizationString) throws OAuthAuthorizationException {
		// TODO Auto-generated method stub
		if (logger.isInfoEnabled()) {
			logger.info("Authorization: " + authorizationString);
		}
		
		String authSchemeFound = authorizationString.substring(0, authScheme.length() + 1);
		boolean validSchemeFound = (caseSensitive ? authSchemeFound.equals(authScheme) : authSchemeFound.equalsIgnoreCase(authScheme));
		if (!validSchemeFound) {
			throw new OAuthAuthorizationException("HTTP Authorization auth-scheme is invalid.");
		}
		
		return parseAndGenerateData(authorizationString.substring(authScheme.length()));
	}
	
	protected abstract T parseAndGenerateData(String authorizationString) throws OAuthAuthorizationException;
}
