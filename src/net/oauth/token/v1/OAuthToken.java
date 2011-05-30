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
package net.oauth.token.v1;

import java.io.Serializable;


/**
 * @author Bienfait Sindi
 * @since 07 December 2009
 *
 */
public abstract class OAuthToken implements Serializable {

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

	/**
	 * @param token the token to set
	 */
//	protected void setToken(String token) {
//		this.token = token;
//	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return ("oauth_token=" + OAuthUtil.encode(getToken()) + "&oauth_token_secret=" + OAuthUtil.encode(getTokenSecret()));
//	}
}