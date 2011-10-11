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
package com.neurologic.oauth.service.provider.oauth1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth1Parameters;
import net.oauth.parameters.OAuthErrorParameter;
import net.oauth.parameters.OAuthParameters;
import net.oauth.provider.OAuth1ServiceProvider;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.oauth.service.provider.OAuthTokenProviderService;
import com.neurologic.oauth.service.provider.manager.OAuth1TokenManager;
import com.neurologic.oauth.service.request.authentication.HttpAuthorizationChallenger;
import com.neurologic.oauth.service.request.authentication.OAuth1HttpAuthorizationChallenger;
import com.neurologic.oauth.service.response.Result;
import com.neurologic.oauth.service.response.formatter.UrlEncodedParameterFormatter;
import com.neurologic.oauth.service.response.impl.OAuthMessageResult;

/**
 * @author Buhake Sindi
 * @since 11 July 2011
 *
 */
public abstract class OAuth1TokenProviderService extends OAuthTokenProviderService<OAuth1TokenManager, OAuth1ServiceProvider> {
	
	private OAuthErrorParameter packException(OAuthException exception) {
		OAuthErrorParameter parameter = new OAuthErrorParameter();
		if (exception != null) {
			parameter.setError(exception.getClass().getName() + ": " + exception.getLocalizedMessage());
		}
		
		return parameter;
	}
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.AbstractOAuthProviderService#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Result execute(HttpServletRequest request) {
		// TODO Auto-generated method stub
		OAuthMessageResult result = new OAuthMessageResult(new UrlEncodedParameterFormatter());
		OAuthParameters parameters = null;
		int statusCode;
		
		try {
			validateRequest(request);
			
			//Let's get the Authorization parameters
			HttpAuthorizationChallenger<OAuth1Parameters> challenger = new OAuth1HttpAuthorizationChallenger();
			parameters = executeInternal(request, challenger.processChallenge(request.getHeader(HTTP_HEADER_AUTHORIZATION)));
			statusCode = HttpServletResponse.SC_OK;
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			logger.error(e.getClass().getName() + ": " + e.getLocalizedMessage(), e);
			statusCode = HttpServletResponse.SC_BAD_REQUEST;
			if (e instanceof OAuthAuthorizationException) {
				statusCode = HttpServletResponse.SC_UNAUTHORIZED;
			}
			
			parameters = packException(e);
		}
		
		result.setOAuthParameters(parameters);
		result.setStatusCode(statusCode);
		return result;
	}
	
	protected abstract OAuth1Parameters executeInternal(HttpServletRequest request, final OAuth1Parameters authorizationParameters) throws OAuthException;
}
