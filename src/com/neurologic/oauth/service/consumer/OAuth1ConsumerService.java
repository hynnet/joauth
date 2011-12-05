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
package com.neurologic.oauth.service.consumer;

import javax.servlet.http.HttpServletRequest;

import com.neurologic.oauth.service.response.OAuthResult;

import net.oauth.consumer.OAuth1Consumer;
import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth1Parameters;
import net.oauth.signature.OAuthSignature;
import net.oauth.signature.impl.OAuthHmacSha1Signature;
import net.oauth.signature.impl.OAuthPlainTextSignature;
import net.oauth.signature.impl.OAuthRsaSha1Signature;
import net.oauth.token.oauth1.AccessToken;
import net.oauth.token.oauth1.AuthorizedToken;
import net.oauth.token.oauth1.RequestToken;

/**
 * @author Bienfait Sindi
 * @since 27 November 2010
 *
 */
public abstract class OAuth1ConsumerService extends AbstractOAuthConsumerService<OAuth1Consumer, AccessToken> {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.AbstractOAuthConsumerService#executeGet(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected OAuthResult executeGet(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			logger.info("execute()");
			
			String oauthToken = request.getParameter(OAuth1Parameters.OAUTH_TOKEN);
			String verifier = request.getParameter(OAuth1Parameters.OAUTH_VERIFIER);
			
			if (oauthToken == null && verifier == null) {
				throw new OAuthException("No OAuth Parameters (" + OAuth1Parameters.OAUTH_TOKEN + ", " + OAuth1Parameters.OAUTH_VERIFIER + ") found.");
			}
			
			RequestToken requestToken = getRequestToken(request);
			if (requestToken == null) {
				throw new OAuthException("The request token is needed when requesting access token.");
			}
			
			if (getOAuthSignature() == null) {
				throw new OAuthException("No OAuth Signature method provided. Please implement the `getOAuthSignature()` method.");
			}
			
			AccessToken accessToken = getConsumer().requestAccessToken(getRealm(), requestToken, new AuthorizedToken(oauthToken, verifier), getOAuthSignature());
			if (accessToken != null) {
				if (logger.isInfoEnabled()) {
					logger.info("Saving Access Token by calling the `saveAccessToken()` method.");
				}
				
				saveAccessToken(request, accessToken);
			}
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return null;
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
