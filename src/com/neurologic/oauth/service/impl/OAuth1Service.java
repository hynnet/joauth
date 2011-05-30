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
import net.oauth.signature.OAuthSignature;
import net.oauth.signature.impl.OAuthHmacSha1Signature;
import net.oauth.signature.impl.OAuthPlainTextSignature;
import net.oauth.signature.impl.OAuthRsaSha1Signature;
import net.oauth.token.v1.AccessToken;
import net.oauth.token.v1.AuthorizedToken;
import net.oauth.token.v1.RequestToken;

import org.apache.log4j.Logger;

import com.neurologic.oauth.service.OAuthService;

/**
 * @author Bienfait Sindi
 * @since 27 November 2010
 *
 */
public abstract class OAuth1Service implements OAuthService<OAuth1Consumer, AccessToken> {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private OAuth1Consumer consumer;
	
	/* (non-Javadoc)
	$ * @see com.neurologic.oauth.service.OAuthService#setOAuthConsumer(java.lang.Object)
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
		
		RequestToken requestToken = getRequestToken(request);
		if (requestToken == null) {
			throw new OAuthException("The request token is needed when requesting access token.");
		}
		
		if (getOAuthSignature() == null) {
			throw new OAuthException("No OAuth Signature method provided. Please implement the `getOAuthSignature()` method.");
		}
		
		AccessToken accessToken = getConsumer().requestAccessToken(getRealm(), new AuthorizedToken(oauthToken, verifier), requestToken.getTokenSecret(), getOAuthSignature());
		if (accessToken != null) {
			if (logger.isInfoEnabled()) {
				logger.info("Saving Access Token by calling the `saveAccessToken()` method.");
			}
			
			saveAccessToken(request, accessToken);
		}
	}

	protected abstract String getRealm();
	
	/**
	 * It is <b>mandatory</b> to specify your OAuth Signature if you want to have a successful authorization workflow to receive access token.
	 * 
	 * @see OAuthHmacSha1Signature
	 * @see OAuthPlainTextSignature
	 * @see OAuthRsaSha1Signature
	 * @return an {@link OAuthSignature}
	 */
	protected abstract OAuthSignature getOAuthSignature();
	protected abstract RequestToken getRequestToken(HttpServletRequest request);
}
