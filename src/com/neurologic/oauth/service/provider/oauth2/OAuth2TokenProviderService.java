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
package com.neurologic.oauth.service.provider.oauth2;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.enums.OAuth2Error;
import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth2ErrorParameters;
import net.oauth.parameters.OAuth2Parameters;
import net.oauth.parameters.OAuthParameters;
import net.oauth.provider.OAuth2ServiceProvider;

import org.json.JSONException;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.oauth.service.provider.OAuthTokenProviderService;
import com.neurologic.oauth.service.provider.manager.OAuth2TokenManager;
import com.neurologic.oauth.service.request.authentication.HttpAuthorizationChallenger;
import com.neurologic.oauth.service.request.authentication.HttpBasicAuthorizationChallenger;
import com.neurologic.oauth.service.response.OAuthResult;
import com.neurologic.oauth.service.response.authenticate.WWWAuthenticateResponse;
import com.neurologic.oauth.service.response.impl.JsonEncodedMessage;
import com.neurologic.oauth.service.response.impl.OAuthMessageResult;

/**
 * @author Buhake Sindi
 * @since 11 July 2011
 *
 */
public abstract class OAuth2TokenProviderService extends OAuthTokenProviderService<OAuth2TokenManager, OAuth2ServiceProvider> {
	
	private int statusCode;
	
	protected OAuth2ErrorParameters toError(OAuth2Error error, String description, String errorUri, String state) {
		OAuth2ErrorParameters errorParameters = new OAuth2ErrorParameters(error);
		
		if (description != null && !description.isEmpty()) {
			errorParameters.setErrorDescription(description);
		}
		
		if (errorUri != null && !errorUri.isEmpty()) {
			errorParameters.setErrorUri(errorUri);
		}
		
		if (state != null && !state.isEmpty()) {
			errorParameters.setState(state);
		}
		
		return errorParameters;
	}
	
	/**
	 * This parameter map returns a unique key/value pair (i.e. a parameter key must exists only once), else throw an exception.
	 * The returned map is an unmodifiable map.
	 * 
	 * @param request
	 * @return
	 * @throws OAuthException
	 */
	private Map<String, String> requestParameterMap(HttpServletRequest request) throws OAuthException {
		Map<String, String> parameterMap = new LinkedHashMap<String, String>();
		
		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			if (entry.getValue() != null && entry.getValue().length != 1) {
				throw new OAuthException(entry.getValue().length + " parameters found with key '" + entry.getKey() + "'.");
			}
			
			parameterMap.put(entry.getKey(), entry.getValue()[0]);
		}
		
		return Collections.unmodifiableMap(parameterMap);
	}
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.AbstractOAuthProviderService#executePost(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected OAuthResult executePost(HttpServletRequest request) {
		// TODO Auto-generated method stub
		OAuthMessageResult result = new OAuthMessageResult();
		OAuthParameters parameters = null;
		
		try {
			validateRequest(request, true);
			
			//Let's get the Authorization parameters
			HttpAuthorizationChallenger<String[]> challenge = new HttpBasicAuthorizationChallenger();
			String[] credentials = challenge.processChallenge(request.getHeader(HTTP_HEADER_AUTHORIZATION));
			
			String clientId = credentials[0];
			String clientSecret = credentials[1];
			if (!getOauthTokenManager().validateCredentials(clientId, clientSecret)) {
				throw new OAuthAuthorizationException("Invalid authorization credentials.");
			}
			
			parameters = executePostInternal(requestParameterMap(request), clientId);
			statusCode = HttpServletResponse.SC_OK;
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			logger.error(e.getClass().getName() + ": " + e.getLocalizedMessage(), e);
			OAuth2Error error = OAuth2Error.INVALID_REQUEST;
			statusCode = HttpServletResponse.SC_BAD_REQUEST;
			if (e instanceof OAuthAuthorizationException) {
				statusCode = HttpServletResponse.SC_UNAUTHORIZED;
				error = OAuth2Error.INVALID_CLIENT;
				
				if (request.getHeader(HTTP_HEADER_AUTHORIZATION) != null) {
					result.addHeader(WWWAuthenticateResponse.HTTP_AUTHENTICATE_HEADER, "Basic");
				}
			}
			
			parameters = toError(error, e.getLocalizedMessage(), null, request.getParameter(OAuth2Parameters.STATE));
		}
		
		result.setStatusCode(statusCode);
		result.addHeader("Cache-Control", "no-store");
		result.addHeader("Pragma", "no-cache");
		try {
			result.setMessage(new JsonEncodedMessage(parameters.getOAuthParameters(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("Unsupported charset", e);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			logger.error("JSONException", e);
		}
		
		return result;
	}
	
	protected void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	protected abstract OAuthParameters executePostInternal(Map<String, String> requestParameters, final String clientId);
	
//	protected static final class Credential implements Serializable {
//		
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = -6596523602416504101L;
//		private String clientId;
//		private String clientSecret;
//		
//		/**
//		 * @param clientId
//		 * @param clientSecret
//		 */
//		private Credential(String clientId, String clientSecret) {
//			super();
//			this.clientId = clientId;
//			this.clientSecret = clientSecret;
//		}
//
//		/**
//		 * @return the clientId
//		 */
//		public final String getClientId() {
//			return clientId;
//		}
//
//		/**
//		 * @return the clientSecret
//		 */
//		public final String getClientSecret() {
//			return clientSecret;
//		}
//	}
}
