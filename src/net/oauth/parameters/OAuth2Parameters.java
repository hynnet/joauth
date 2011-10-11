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
package net.oauth.parameters;


/**
 * @author Bienfait Sindi
 * @since 24 July 2010
 *
 */
public class OAuth2Parameters extends OAuthParameters {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3474806351864043349L;
	public static final String ACCESS_TOKEN = "access_token";
	public static final String CLIENT_ID = "client_id";
	public static final String CODE = "code";
	public static final String REDIRECT_URI = "redirect_uri";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String ASSERTION_TYPE = "assertion_type";
	public static final String ASSERTION = "assertion";
	public static final String REFRESH_TOKEN = "refresh_token";
	public static final String TOKEN_TYPE = "token_type";
	public static final String SCOPE = "scope";
	public static final String RESPONSE_TYPE = "response_type";
	public static final String GRANT_TYPE = "grant_type";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String STATE = "state";
	public static final String EXPIRES_IN = "expires_in";
	
	/**
	 * Default Constructor
	 */
	public OAuth2Parameters() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return get(CLIENT_ID);
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		if (clientId != null)
			put(CLIENT_ID, clientId);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return get(CODE);
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		if (code != null)
			put(CODE, code);
	}

	/**
	 * @return the redirectUri
	 */
	public String getRedirectUri() {
		return get(REDIRECT_URI);
	}

	/**
	 * @param redirectUri the redirectUri to set
	 */
	public void setRedirectUri(String redirectUri) {
		if (redirectUri != null)
			put(REDIRECT_URI, redirectUri);
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return get(USERNAME);
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		if (userName != null)
			put(USERNAME, userName);
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return get(PASSWORD);
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		if (password != null)
			put(PASSWORD, password);
	}

	/**
	 * @return the assertionType
	 */
	public String getAssertionType() {
		return get(ASSERTION_TYPE);
	}

	/**
	 * @param assertionType the assertionType to set
	 */
	public void setAssertionType(String assertionType) {
		if (assertionType != null)
			put(ASSERTION_TYPE, assertionType);
	}

	/**
	 * @return the assertion
	 */
	public String getAssertion() {
		return get(ASSERTION);
	}

	/**
	 * @param assertion the assertion to set
	 */
	public void setAssertion(String assertion) {
		if (assertion != null)
			put(ASSERTION, assertion);
	}

	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return get(REFRESH_TOKEN);
	}

	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		if (refreshToken != null)
			put(REFRESH_TOKEN, refreshToken);
	}

	/**
	 * @return the responseType
	 */
	public String getResponseType() {
		return get(RESPONSE_TYPE);
	}

	/**
	 * @param responseType the responseType to set
	 */
	public void setResponseType(String responseType) {
		if (responseType != null)
			put(RESPONSE_TYPE, responseType);
	}

	/**
	 * @return the grantType
	 */
	public String getGrantType() {
		return get(GRANT_TYPE);
	}

	/**
	 * @param grantType the grantType to set
	 */
	public void setGrantType(String grantType) {
		if (grantType != null)
			put(GRANT_TYPE, grantType);
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return get(ACCESS_TOKEN);
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		if (accessToken != null && !accessToken.isEmpty())
			put(ACCESS_TOKEN, accessToken);
	}

	/**
	 * @return the clientSecret
	 */
	public String getClientSecret() {
		return get(CLIENT_SECRET);
	}

	/**
	 * @param clientSecret the clientSecret to set
	 */
	public void setClientSecret(String clientSecret) {
		if (clientSecret != null && !clientSecret.isEmpty()) {
			put(CLIENT_SECRET, clientSecret);
		}
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return get(SCOPE);
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		if (scope != null && !scope.isEmpty()) {
			put(SCOPE, scope);
		}
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return get(STATE);
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		if (state != null && !state.isEmpty()) {
			put(STATE, state);
		}
	}

	/**
	 * @return the tokenType
	 */
	public String getTokenType() {
		return get(TOKEN_TYPE);
	}

	/**
	 * @param tokenType the tokenType to set
	 */
	public void setTokenType(String tokenType) {
		if (tokenType != null && !tokenType.isEmpty()) {
			put(TOKEN_TYPE, tokenType);
		}
	}

	/**
	 * @return the expiresIn
	 */
	public int getExpiresIn() {
		String s = get(EXPIRES_IN);
		if (s == null) {
			return 0;
		}
		
		return Integer.parseInt(s);
	}

	/**
	 * @param expiresIn the expiresIn to set
	 */
	public void setExpiresIn(int expiresIn) {
		put(EXPIRES_IN, String.valueOf(expiresIn));
	}
}
