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
package net.oauth.signature;


/**
 * @author Bienfait Sindi
 * @since 23 November 2010
 *
 */
public abstract class ConsumerSecretBasedOAuthSignature implements OAuthSignature {

	private String consumerSecret;
	private String tokenSecret;
	
	/**
	 * 
	 */
	protected ConsumerSecretBasedOAuthSignature() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param consumerSecret the consumerSecret to set
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}
	
	/**
	 * @param tokenSecret the tokenSecret to set
	 */
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	
	/**
	 * @return the consumerSecret
	 */
	protected String getConsumerSecret() {
		return consumerSecret;
	}
	
	/**
	 * @return the tokenSecret
	 */
	protected String getTokenSecret() {
		return tokenSecret;
	}
}
