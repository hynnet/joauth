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
package net.oauth.enums;

/**
 * @author Bienfait Sindi
 * @since 24 July 2010
 *
 */
public enum GrantType {
	AUTHORIZATION_CODE("authorization_code"),
	PASSWORD("password"),
	ASSERTION("assertion"),
	REFRESH_TOKEN("refresh_token"),
	NONE("none"),
	CLIENT_CREDENTIALS("client_credentials")
	;
	private final String grantType;
	
	/**
	 * 
	 * @param grantType
	 */
	private GrantType(final String grantType) {
		// TODO Auto-generated constructor stub
		this.grantType = grantType;
	}

	public static GrantType Of(String grantType) {
		if (AUTHORIZATION_CODE.toString().equals(grantType)) {
			return AUTHORIZATION_CODE;
		}
		
		if (PASSWORD.toString().equals(grantType)) {
			return PASSWORD;
		}
		
		if (ASSERTION.toString().equals(grantType)) {
			return ASSERTION;
		}
		
		if (REFRESH_TOKEN.toString().equals(grantType)) {
			return REFRESH_TOKEN;
		}
		
		if (NONE.toString().equals(grantType)) {
			return NONE;
		}
		
		if (CLIENT_CREDENTIALS.toString().equals(grantType)) {
			return CLIENT_CREDENTIALS;
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public final String toString() {
		// TODO Auto-generated method stub
		return grantType;
	}
}
