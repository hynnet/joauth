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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.oauth.consumer.OAuth2Consumer;
import net.oauth.enums.GrantType;
import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth2Parameters;
import net.oauth.token.oauth2.AccessToken;
import net.oauth.token.oauth2.AuthorizationToken;
import net.oauth.util.OAuth2Util;

import org.json.JSONException;
import org.json.JSONObject;

import com.neurologic.oauth.service.response.OAuthResult;

/**
 * @author Bienfait Sindi
 * @since 30 November 2010
 *
 */
public abstract class OAuth2ConsumerService extends AbstractOAuthConsumerService<OAuth2Consumer, AccessToken> {

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.AbstractOAuthConsumerService#executeGet(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected OAuthResult executeGet(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			AccessToken accessToken = null;
			Map<String, String> parameterMap = extractParameterMap(request);
				
			if (parameterMap.containsKey("error")) {
				throwOAuthErrorException(parameterMap);
			}
			
			//Process when we received "code" from request parameter.
			if (parameterMap.containsKey(OAuth2Parameters.CODE)) {
				if (logger.isInfoEnabled()) {
					logger.info("\"" + OAuth2Parameters.CODE + "\" received.");
				}
				
				accessToken = retrieveAccessTokenViaAuthorizationToken(OAuth2Util.createAuthorizationToken(parameterMap));
			}
			
			//Process when we received "access_token" from request parameter.
			if (parameterMap.containsKey(OAuth2Parameters.ACCESS_TOKEN)) {
				if (logger.isInfoEnabled()) {
					logger.info("\"" + OAuth2Parameters.ACCESS_TOKEN + "\" received.");
				}
				
				accessToken = OAuth2Util.createAccessToken(parameterMap);
			}
			
			if (accessToken != null) {
				if (logger.isInfoEnabled()) {
					logger.info("Storing access token by calling `saveAccessToken()` method.");
				}
				
				saveAccessToken(request, accessToken);
			}
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			logger.error(e.getLocalizedMessage(), e);
		}
		
		//We don't need to return anything.
		return null;
	}
	
	/**
	 * Retrieve all HTTP parameters from the {@link HttpServletRequest}.
	 * @param request
	 * @return
	 */
	protected final Map<String, String> extractParameterMap(HttpServletRequest request) {
		Map<String, String> resultMap = new LinkedHashMap<String, String>();
		for (String parameterName : request.getParameterMap().keySet()) {
			String[] requestValue = request.getParameterMap().get(parameterName);
			
			if (requestValue != null) {
				resultMap.put(parameterName, requestValue[0]);
			}
		}
		
		return resultMap;
	}
	
	/**
	 * If the <code>parameterMap</code> contains a "error" key (OAuth error), then throw an {@link OAuthException}.
	 * 
	 * @param parameterMap
	 * @throws OAuthException
	 */
	protected final void throwOAuthErrorException(Map<String, String> parameterMap) throws OAuthException {
		JSONObject errorJson = new JSONObject();
		for (Entry<String, String> entry : parameterMap.entrySet()) {
			try {
				errorJson.put(entry.getKey(), entry.getValue());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//This should never happen?
				logger.error("JSONException: ", e);
			}
		}
		
		throw new OAuthException("OAuth response returned the following error:\r\n" + errorJson.toString());
	}
	
	/**
	 * This method retrieves the access token based on the authorization code received from the OAuth Service Provider.
	 * 
	 * @param authToken The Authorization code
	 * @return the access token, if successful, null otherwise (or if the autToken is null).
	 * @throws OAuthException
	 */
	private AccessToken retrieveAccessTokenViaAuthorizationToken(AuthorizationToken authToken) throws OAuthException {
//		if (authToken == null) {
//			return null;
//		}
//		
		String redirectUri = getRedirectUri();
		if (redirectUri == null || redirectUri.isEmpty()) {
			throw new OAuthException("No '" + OAuth2Parameters.REDIRECT_URI + "' provided. Please, implement the 'getRedirectUri()' method.");
		}
		
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setCode(authToken.getCode());
		parameters.setRedirectUri(redirectUri);
		
		return getConsumer().requestAcessToken(GrantType.AUTHORIZATION_CODE, parameters, getScopeDelimiter(), getScope());
	}
	
	/**
	 * The <code>redirect_uri</code> needed for the OAuth authorization and OAuth access token request. It is mandatory to implement this method.
	 * @return the <code>redirect_uri</code> string.
	 */
	protected abstract String getRedirectUri();
	protected abstract String getState();
	protected abstract String[] getScope();
	protected abstract String getScopeDelimiter();
}
