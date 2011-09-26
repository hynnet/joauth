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
package net.oauth.util;

import java.util.Map;

import net.oauth.enums.TokenType;
import net.oauth.parameters.OAuth2Parameters;
import net.oauth.token.oauth2.AccessToken;
import net.oauth.token.oauth2.AuthorizationToken;

/**
 * @author Bienfait Sindi
 * @since 24 May 2011
 *
 */
public class OAuth2Util {
	
	private static final String TOKEN_TYPE = "token_type";
	private static final String EXPIRES_IN = "expires_in";
	
	private OAuth2Util() {}
	
	/**
	 * Creates an authorization token based on a response map received by the OAuth Server.
	 * 
	 * @param responseMap - OAuth responses
	 * @return a new authorization token if "code" key is found, null otherwise.
	 */
	public static AuthorizationToken createAuthorizationToken(Map<String, String> responseMap) {
		if (responseMap == null || responseMap.isEmpty()) {
			return null;
		}
		
		if (!responseMap.containsKey(OAuth2Parameters.CODE)) {
			return null;
		}
		
		AuthorizationToken authorizationToken = new AuthorizationToken();
		
		synchronized (responseMap) {
			for (String key : responseMap.keySet()) {
				String value = responseMap.get(key);
				
				if (OAuth2Parameters.CODE.equals(key)) {
					authorizationToken.setCode(value);
				} else if (EXPIRES_IN.equals(key)) {
					authorizationToken.setExpiresIn(Integer.parseInt(value));
				} else if (OAuth2Parameters.SCOPE.equals(key)) {
					authorizationToken.setScope(value);
				} else if (OAuth2Parameters.STATE.equals(key)) {
					authorizationToken.setState(value);
				}
			}
		}
		
		return authorizationToken;
	}
	
	/**
	 * Creates an access token based on a response map received by the OAuth Server.
	 * 
	 * @param responseMap - OAuth responses
	 * @return a new access token if "access_token" key is found, null otherwise.
	 */
	public static AccessToken createAccessToken(Map<String, String> responseMap) {
		if (responseMap == null || responseMap.isEmpty()) {
			return null;
		}
		
		if (!responseMap.containsKey(OAuth2Parameters.ACCESS_TOKEN)) {
			return null;
		}
		
		AccessToken accessToken = new AccessToken();
		synchronized (responseMap) {
			for (String key : responseMap.keySet()) {
				String value = responseMap.get(key);
				
				if (OAuth2Parameters.ACCESS_TOKEN.equals(key)) {
					accessToken.setAccessToken(value);
				} else if (TOKEN_TYPE.equals(key)) {
					accessToken.setTokenType(TokenType.of(value));
				} else if (EXPIRES_IN.equals(key)) {
					accessToken.setExpiresIn(Integer.parseInt(value));
				} else if (OAuth2Parameters.REFRESH_TOKEN.equals(key)) {
					accessToken.setRefreshToken(value);
				} else if (OAuth2Parameters.SCOPE.equals(key)) {
					accessToken.setScope(value);
				} else if (OAuth2Parameters.STATE.equals(key)) {
					accessToken.setState(value);
				} else {
					accessToken.addAdditionalParameter(key, value);
				}
			}
		}
		
		return accessToken;
	}
}
