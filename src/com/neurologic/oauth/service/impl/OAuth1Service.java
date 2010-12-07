/**
 * 
 */
package com.neurologic.oauth.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.consumer.OAuth1Consumer;
import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuthParameters;
import net.oauth.token.AccessToken;
import net.oauth.token.AuthorizedToken;

import org.apache.log4j.Logger;

import com.neurologic.oauth.service.OAuthService;
import com.neurologic.oauth.util.Globals;

/**
 * @author Bienfait Sindi
 * @since 27 November 2010
 *
 */
public abstract class OAuth1Service implements OAuthService<OAuth1Consumer> {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private OAuth1Consumer consumer;
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.OAuthService#setOAuthConsumer(java.lang.Object)
	 */
	@Override
	public void setOAuthConsumer(OAuth1Consumer consumer) {
		// TODO Auto-generated method stub
		this.consumer = consumer;
	}

	/**
	 * @return the consumer
	 */
	protected OAuth1Consumer getConsumer() {
		return consumer;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.OAuthService#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws OAuthException {
		// TODO Auto-generated method stub
		logger.info("execute()");
		
		String oauthToken = request.getParameter(OAuthParameters.OAUTH_TOKEN);
		String verifier = request.getParameter(OAuthParameters.OAUTH_VERIFIER);
		
		if (oauthToken == null &&  verifier== null) {
			throw new OAuthException("No OAuth Parameters (" + OAuthParameters.OAUTH_TOKEN + ", " + OAuthParameters.OAUTH_VERIFIER + ") found.");
		}
		
		AccessToken accessToken = processReceivedAuthorizedToken(request, new AuthorizedToken(oauthToken, verifier));
		if (accessToken == null) {
			throw new OAuthException("AccessToken = null, cannot register it to session.");
		}
		
		request.getSession().setAttribute(Globals.SESSION_OAUTH1_ACCESS_TOKEN, accessToken);
	}

	protected abstract AccessToken processReceivedAuthorizedToken(HttpServletRequest request, AuthorizedToken authorizedToken) throws OAuthException;
}
