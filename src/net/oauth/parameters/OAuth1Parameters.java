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

import java.util.Map;

/**
 * @author Bienfait Sindi
 * @since 30 March 2010
 *
 */
public class OAuth1Parameters extends OAuthParameters {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2022829899431411819L;
	public static final String OAUTH_BODY_HASH = "oauth_body_hash";
	public static final String OAUTH_CALLBACK = "oauth_callback";
	public static final String OAUTH_CALLBACK_CONFIRMED = "oauth_callback_confirmed";
	public static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
	public static final String OAUTH_NONCE = "oauth_nonce";
	public static final String OAUTH_TOKEN = "oauth_token";
	public static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
	public static final String OAUTH_SIGNATURE = "oauth_signature";
	public static final String OAUTH_TIMESTAMP = "oauth_timestamp";
	public static final String OAUTH_TOKEN_SECRET = "oauth_token_secret";
	public static final String OAUTH_VERIFIER = "oauth_verifier";
	public static final String OAUTH_VERSION = "oauth_version";
	public static final String OAUTH_REALM = "realm";

		
	/**
	 * 
	 */
	public OAuth1Parameters() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public OAuth1Parameters(Map<String, String> parameters) {
		this();
		
		if (parameters != null) {
			for (String parameter : parameters.keySet()) {
				String value = parameters.get(parameter);
				if (parameter.startsWith("oauth_") || OAUTH_REALM.equals(parameter)) {
					put(parameter, value);
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see net.oauth.parameters.OAuthParameters#put(java.lang.String, java.lang.String)
	 */
	@Override
	protected void put(String key, String value) {
		// TODO Auto-generated method stub
		if (key != null && !key.isEmpty() && value != null) {
			if (!key.startsWith("oauth_") && !OAUTH_REALM.equals(key)) {
				throw new IllegalArgumentException("OAuth parameters must start with \"oauth_\" or is \"" + OAUTH_REALM + "\".");
			}
			
			super.put(key, value);
		}
	}
	
	/**
	 * 
	 * @return oauthBodyHash
	 */
	public String getOAuthBodyHash() {
		return get(OAUTH_BODY_HASH);
	}

	/**
	 * @param oauthBodyHash the oauthBodyHash to set
	 */
	public void setOAuthBodyHash(String oauthBodyHash) {
		if (oauthBodyHash != null) {
			put(OAUTH_BODY_HASH, oauthBodyHash);
		}
	}
	
	/**
	 * 
	 * @return oauthCallback
	 */
	public String getOAuthCallback() {
		return get(OAUTH_CALLBACK);
	}
	
	/**
	 * @param oauthCallback the oauthCallback to set
	 */
	public void setOAuthCallback(String oauthCallback) {
		if (oauthCallback != null) {
			put(OAUTH_CALLBACK, oauthCallback);
		}
	}
	
	/**
	 * 
	 * @return oauthCallbackConfirmed
	 */
	public boolean getOAuthCallbackConfirmed() {
		return Boolean.valueOf(get(OAUTH_CALLBACK_CONFIRMED));
	}
	
	/**
	 * @param oauthCallbackConfirmed the oauthCallbackConfirmed to set
	 */
	public void setOAuthCallbackConfirmed(boolean oauthCallbackConfirmed) {
		put(OAUTH_CALLBACK_CONFIRMED, Boolean.toString(oauthCallbackConfirmed));
	}
	
	/**
	 * 
	 * @return oauthConsumerKey
	 */
	public String getOAuthConsumerKey() {
		return get(OAUTH_CONSUMER_KEY);
	}
	
	/**
	 * @param oauthConsumerKey the oauthConsumerKey to set
	 */
	public void setOAuthConsumerKey(String oauthConsumerKey) {
		if (oauthConsumerKey != null) {
			put(OAUTH_CONSUMER_KEY, oauthConsumerKey);
		}
	}
	
	/**
	 * 
	 * @return oauthNonce;
	 */
	public String getOAuthNonce() {
		return get(OAUTH_NONCE);
	}
	
	/**
	 * @param oauthNonce the oauthNonce to set
	 */
	public void setOAuthNonce(String oauthNonce) {
		if (oauthNonce != null) {
			put(OAUTH_NONCE, oauthNonce);
		}
	}
	
	/**
	 * 
	 * @return oauthRealm
	 */
	public String getOAuthRealm() {
		return get(OAUTH_REALM);
	}
	
	/**
	 * @param realm the realm to set
	 */
	public void setOAuthRealm(String realm) {
		if (realm != null && !realm.isEmpty()) {
			put(OAUTH_REALM, realm);
		}
	}
	
	/**
	 * 
	 * @return oauthSignature;
	 */
	public String getOAuthSignature() {
		return get(OAUTH_SIGNATURE);
	}
	
	/**
	 * @param oauthSignature the oauthSignature to set
	 */
	public void setOAuthSignature(String oauthSignature) {
		if (oauthSignature != null) {
			put(OAUTH_SIGNATURE, oauthSignature);
		}
	}
	
	/**
	 * 
	 * @return oauthSignatureMethod
	 */
	public String getOAuthSignatureMethod() {
		return get(OAUTH_SIGNATURE_METHOD);
	}
	
	/**
	 * @param oauthSignatureMethod the oauthSignatureMethod to set
	 */
	public void setOAuthSignatureMethod(String oauthSignatureMethod) {
		if (oauthSignatureMethod != null) {
			put(OAUTH_SIGNATURE_METHOD, oauthSignatureMethod);
		}
	}
	
	/**
	 * 
	 * @return oauthTimestamp
	 */
	public String getOAuthTimestamp() {
		return get(OAUTH_TIMESTAMP);
	}
	
	/**
	 * @param oauthTimestamp the oauthTimestamp to set
	 */
	public void setOAuthTimestamp(String oauthTimestamp) {
		if (oauthTimestamp != null) {
			put(OAUTH_TIMESTAMP, oauthTimestamp);
		}
	}
	
	/**
	 * 
	 * @return oauthToken
	 */
	public String getOAuthToken() {
		return get(OAUTH_TOKEN);
	}
	
	/**
	 * @param oauthToken the oauthToken to set
	 */
	public void setOAuthToken(String oauthToken) {
		if (oauthToken != null) {
			put(OAUTH_TOKEN, oauthToken);
		}
	}
	
	/**
	 * 
	 * @return oauthTokenSecret
	 */
	public String getOAuthTokenSecret() {
		return get(OAUTH_TOKEN_SECRET);
	}
	
	/**
	 * @param oauthTokenSecret the oauthTokenSecret to set
	 */
	public void setOAuthTokenSecret(String oauthTokenSecret) {
		if (oauthTokenSecret != null) {
			put(OAUTH_TOKEN_SECRET, oauthTokenSecret);
		}
	}
	
	/**
	 * 
	 * @return oauthVerifier
	 */
	public String getOAuthVerifier() {
		return get(OAUTH_VERIFIER);
	}
	
	/**
	 * @param oauthVerifier the oauthVerifier to set
	 */
	public void setOAuthVerifier(String oauthVerifier) {
		if (oauthVerifier != null) {
			put(OAUTH_VERIFIER, oauthVerifier);
		}
	}
	
	/**
	 * 
	 * @return oauthVersion
	 */
	public String getOAuthVersion() {
		return get(OAUTH_VERSION);
	}
	
	/**
	 * @param oauthVersion the oauthVersion to set
	 */
	public void setOAuthVersion(String oauthVersion) {
		if (oauthVersion != null) {
			put(OAUTH_VERSION, oauthVersion);
		}
	}
}
