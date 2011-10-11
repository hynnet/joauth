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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;

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
	 * According to OAuth 2, section 3.1.2 (Redirect Endpoint), a redirection endpoint <b>MUST</b> be an 
	 * absolute URI and <b>MUST not</b> include a fragment component.
	 * @param redirectUri
	 * @return
	 */
	public static boolean isRedirectEndpointUriValid(String redirectUri) {
		try {
			URI uri = new URI(redirectUri);
			return (uri.isAbsolute() && uri.getFragment() == null);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			// FIXME Never print stacktrace!!!
			//e.printStackTrace();
			return false;
		}
	}
	
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
			for (Entry<String, String> entry : responseMap.entrySet()) {
				
				if (OAuth2Parameters.CODE.equals(entry.getKey())) {
					authorizationToken.setCode(entry.getValue());
				} else if (EXPIRES_IN.equals(entry.getKey())) {
					authorizationToken.setExpiresIn(Integer.parseInt(entry.getValue()));
				} else if (OAuth2Parameters.SCOPE.equals(entry.getKey())) {
					authorizationToken.setScope(entry.getValue());
				} else if (OAuth2Parameters.STATE.equals(entry.getKey())) {
					authorizationToken.setState(entry.getValue());
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
			for (Entry<String, String> entry : responseMap.entrySet()) {
				
				if (OAuth2Parameters.ACCESS_TOKEN.equals(entry.getKey())) {
					accessToken.setAccessToken(entry.getValue());
				} else if (TOKEN_TYPE.equals(entry.getKey())) {
					accessToken.setTokenType(TokenType.of(entry.getValue()));
				} else if (EXPIRES_IN.equals(entry.getKey())) {
					accessToken.setExpiresIn(Integer.parseInt(entry.getValue()));
				} else if (OAuth2Parameters.REFRESH_TOKEN.equals(entry.getKey())) {
					accessToken.setRefreshToken(entry.getValue());
				} else if (OAuth2Parameters.SCOPE.equals(entry.getKey())) {
					accessToken.setScope(entry.getValue());
				} else if (OAuth2Parameters.STATE.equals(entry.getKey())) {
					accessToken.setState(entry.getValue());
				} else {
					accessToken.addAdditionalParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		
		return accessToken;
	}
}
