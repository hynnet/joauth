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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.oauth.parameters.OAuthParameters;
import net.oauth.provider.OAuth1ServiceProvider;
import net.oauth.util.OAuth1Util;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.oauth.service.provider.OAuthTokenProviderService;
import com.neurologic.oauth.service.provider.manager.OAuth1TokenManager;

/**
 * @author Buhake Sindi
 * @since 11 July 2011
 *
 */
public abstract class OAuth1TokenProviderService extends OAuthTokenProviderService<OAuth1TokenManager, OAuth1ServiceProvider> {

	private static final String OAUTH_AUTHORIZATION_HEADER_START = "OAuth ";

	protected OAuthParameters getOAuthAuthorizationParameters(HttpServletRequest request) throws OAuthAuthorizationException {
		if (request == null) {
			return null;
		}
		
		String header = request.getHeader(HTTP_HEADER_AUTHORIZATION);
		
		if (header == null || header.isEmpty()) {
			throw new OAuthAuthorizationException("Cannot find HTTP Header '" + HTTP_HEADER_AUTHORIZATION + "'.");
		}
		
		if (!header.startsWith(OAUTH_AUTHORIZATION_HEADER_START)) {
			throw new OAuthAuthorizationException("HTTP Authorization header is invalid.");
		}
		
		if (logger.isInfoEnabled()) {
			logger.info(HTTP_HEADER_AUTHORIZATION + ": " + header);
		}
		
		//TODO: This is ugly, but what can I do?
		Map<String, String> oauthHeaderParameters = new HashMap<String, String>();
		String[] headerValues = header.substring(OAUTH_AUTHORIZATION_HEADER_START.length()).split(",");
		for (String headerValue : headerValues) {
			String[] headerTokens = headerValue.split("=");
			String key = headerTokens[0];
			if (!OAuthParameters.OAUTH_REALM.equals(key) && !key.startsWith("oauth_")) {
				throw new OAuthAuthorizationException("OAuth Authorization has unrecognizable key '" + key + "' (String: " + headerValue + ".");
			}
			
			String hv = headerTokens[1];
			if (!hv.startsWith("\"") && !hv.endsWith("\"")) {
				throw new OAuthAuthorizationException("OAuth Authorization has an incorrect value (String: " + headerValue + ".");
			}
			oauthHeaderParameters.put(key, OAuth1Util.decode(hv.substring(1, hv.length() - 1)));
		}
		
		return new OAuthParameters(oauthHeaderParameters);
	}
}
