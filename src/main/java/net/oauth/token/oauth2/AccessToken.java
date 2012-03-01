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
package net.oauth.token.oauth2;

import java.util.LinkedHashMap;
import java.util.Map;

import net.oauth.enums.TokenType;

/**
 * @author Bienfait Sindi
 * @since 24 May 2011
 *
 */
public class AccessToken extends OAuthToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4419729971477912556L;
	private String accessToken;
	private TokenType tokenType;
	private String refreshToken;
	private Map<String, String> additionalParameters;
	
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}
	
	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	/**
	 * @return the tokenType
	 */
	public TokenType getTokenType() {
		return tokenType;
	}
	
	/**
	 * @param tokenType the tokenType to set
	 */
	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}
	
	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}
	
	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * @return the additionalParameters
	 */
	public Map<String, String> getAdditionalParameters() {
		return additionalParameters;
	}
	
	/**
	 * @param additionalParameters the additionalParameters to set
	 */
	public void addAdditionalParameter(String key, String value) {
		if (additionalParameters == null) {
			additionalParameters = new LinkedHashMap<String, String>();
		}
		
		additionalParameters.put(key, value);
	}
}
