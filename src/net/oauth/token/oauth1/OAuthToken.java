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

import net.oauth.token.OAuthTokenBase;


/**
 * @author Bienfait Sindi
 * @since 07 December 2009
 *
 */
public abstract class OAuthToken extends OAuthTokenBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2805831035072950221L;
	private String token;
	
	/**
	 * @param token
	 */
	protected OAuthToken(String token) {
		this.token = token;
	}
	
	/**
	 * @return the token
	 */
	public final String getToken() {
		return token;
	}

	/* (non-Javadoc)
	 * @see net.oauth.token.OAuthTokenBase#getOAuthVersion()
	 */
	@Override
	public final int getOAuthVersion() {
		// TODO Auto-generated method stub
		return 1;
	}
}
