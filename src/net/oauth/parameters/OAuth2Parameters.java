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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bienfait Sindi
 * @since 24 July 2010
 *
 */
public class OAuth2Parameters {

	public static final String ACCESS_TOKEN = "access_token";
	public static final String CLIENT_ID = "client_id";
	public static final String CODE = "code";
	public static final String REDIRECT_URI = "redirect_uri";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String ASSERTION_TYPE = "assertion_type";
	public static final String ASSERTION = "assertion";
	public static final String REFRESH_TOKEN = "refresh_token";
	public static final String SCOPE = "scope";
	public static final String RESPONSE_TYPE = "response_type";
	public static final String GRANT_TYPE = "grant_type";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String STATE = "state";

	private Map<String, String> parameterMap;

	/**
	 * Default Constructor
	 */
	public OAuth2Parameters() {
		// TODO Auto-generated constructor stub
		parameterMap = new HashMap<String, String>();
	}
	
	private void addParameterValue(String name, String value) {
		if (name != null && !name.isEmpty() && value != null && !value.isEmpty()) {
			parameterMap.put(name, value);
		}
	}
	
	public synchronized String[] getParameterNames() {
		if (parameterMap == null || parameterMap.isEmpty()) {
			return null;
		}
		
		return parameterMap.keySet().toArray(new String[parameterMap.keySet().size()]);
	}
	
	public void removeParameter(String name) {
		if (name != null && !name.isEmpty()) {
			parameterMap.remove(name);
		}
	}
	
	public boolean isEmpty() {
		return (parameterMap == null || parameterMap.isEmpty());
	}
	
	public String getParameterValue(String name) {
		return parameterMap.get(name);
	}
	
	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return getParameterValue(CLIENT_ID);
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		addParameterValue(CLIENT_ID, clientId);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return getParameterValue(CODE);
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		addParameterValue(CODE, code);
	}

	/**
	 * @return the redirectUri
	 */
	public String getRedirectUri() {
		return getParameterValue(REDIRECT_URI);
	}

	/**
	 * @param redirectUri the redirectUri to set
	 */
	public void setRedirectUri(String redirectUri) {
		addParameterValue(REDIRECT_URI, redirectUri);
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return getParameterValue(USERNAME);
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		addParameterValue(USERNAME, userName);
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return getParameterValue(PASSWORD);
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		addParameterValue(PASSWORD, password);
	}

	/**
	 * @return the assertionType
	 */
	public String getAssertionType() {
		return getParameterValue(ASSERTION_TYPE);
	}

	/**
	 * @param assertionType the assertionType to set
	 */
	public void setAssertionType(String assertionType) {
		addParameterValue(ASSERTION_TYPE, assertionType);
	}

	/**
	 * @return the assertion
	 */
	public String getAssertion() {
		return getParameterValue(ASSERTION);
	}

	/**
	 * @param assertion the assertion to set
	 */
	public void setAssertion(String assertion) {
		addParameterValue(ASSERTION, assertion);
	}

	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return getParameterValue(REFRESH_TOKEN);
	}

	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		addParameterValue(REFRESH_TOKEN, refreshToken);
	}

	/**
	 * @return the responseType
	 */
	public String getResponseType() {
		return getParameterValue(RESPONSE_TYPE);
	}

	/**
	 * @param responseType the responseType to set
	 */
	public void setResponseType(String responseType) {
		addParameterValue(RESPONSE_TYPE, responseType);
	}

	/**
	 * @return the grantType
	 */
	public String getGrantType() {
		return getParameterValue(GRANT_TYPE);
	}

	/**
	 * @param grantType the grantType to set
	 */
	public void setGrantType(String grantType) {
		addParameterValue(GRANT_TYPE, grantType);
	}
}
