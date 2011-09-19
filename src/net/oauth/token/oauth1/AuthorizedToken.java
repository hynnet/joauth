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

/**
 * @author Bienfait Sindi
 * @since 20 November 2010
 *
 */
public class AuthorizedToken extends OAuthToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2383224891942308204L;
	private String verifier;
	
	/**
	 * @param token
	 * @param verifier
	 */
	public AuthorizedToken(String token, String verifier) {
		super(token);
		// TODO Auto-generated constructor stub
		this.verifier = verifier;
	}

	/**
	 * @return the verifier
	 */
	public String getVerifier() {
		return verifier;
	}
}
