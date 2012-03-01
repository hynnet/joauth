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

import net.oauth.token.OAuthTokenBase;

/**
 * @author Bienfait Sindi
 * @since 24 May 2011
 *
 */
public abstract class OAuthToken extends OAuthTokenBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2078931353480505320L;
	private int expiresIn;
	private String scope;
	private String state;
	
	/**
	 * @return the expiresIn
	 */
	public int getExpiresIn() {
		return expiresIn;
	}
	
	/**
	 * @param expiresIn the expiresIn to set
	 */
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}
	
	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/* (non-Javadoc)
	 * @see net.oauth.token.OAuthTokenBase#getOAuthVersion()
	 */
	@Override
	public final int getOAuthVersion() {
		// TODO Auto-generated method stub
		return 2;
	}
}
