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
package net.oauth.token.oauth1;

import java.util.Map;

/**
 * @author Bienfait Sindi
 * @since 20 November 2010
 *
 */
public class AccessToken extends TokenSecretBasedToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1584406119580124615L;
	private Map<String, String> additionalParameters;
	
	/**
	 * @param token
	 * @param tokenSecret
	 * @param additionalParameters
	 */
	public AccessToken(String token, String tokenSecret, Map<String, String> additionalParameters) {
		super(token, tokenSecret);
		this.additionalParameters = additionalParameters;
	}

	/**
	 * @return the additionalParameters
	 */
	public Map<String, String> getAdditionalParameters() {
		return additionalParameters;
	}
}
