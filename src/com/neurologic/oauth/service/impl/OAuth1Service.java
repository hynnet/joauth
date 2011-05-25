/**
 *  Copyright 2010-2011 Buhake Sindi

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package com.neurologic.oauth.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.consumer.OAuth1Consumer;
import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuthParameters;
import net.oauth.token.v1.AccessToken;
import net.oauth.token.v1.AuthorizedToken;

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
		
		if (oauthToken == null && verifier == null) {
			throw new OAuthException("No OAuth Parameters (" + OAuthParameters.OAUTH_TOKEN + ", " + OAuthParameters.OAUTH_VERIFIER + ") found.");
		}
		
		AccessToken accessToken = processReceivedAuthorizedToken(request, new AuthorizedToken(oauthToken, verifier));
		if (accessToken == null) {
			throw new OAuthException("AccessToken is null, cannot register it to session.");
		}
		
		request.getSession(true).setAttribute(Globals.SESSION_OAUTH1_ACCESS_TOKEN, accessToken);
		if (logger.isInfoEnabled()) {
			logger.info("Access Token(" + accessToken.getToken() + ") stored in session(" + Globals.SESSION_OAUTH1_ACCESS_TOKEN + ").");
		}
	}

	protected abstract AccessToken processReceivedAuthorizedToken(HttpServletRequest request, AuthorizedToken authorizedToken) throws OAuthException;
}
