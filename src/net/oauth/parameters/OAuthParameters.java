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

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Bienfait Sindi
 * @since 30 March 2010
 *
 */
public class OAuthParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6032618043340507250L;
	
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

	private Map<String, String> parameterMap;
		
	/**
	 * 
	 */
	public OAuthParameters() {
		// TODO Auto-generated constructor stub
		parameterMap = new LinkedHashMap<String, String>();
	}
	
	public OAuthParameters(Map<String, String> parameters) {
		this();
		
		if (parameters != null) {
			for (String parameter : parameters.keySet()) {
				String value = parameters.get(parameter);
				if (parameter.startsWith("oauth_") || OAUTH_REALM.equals(parameter)) {
					setOAuthParameter(parameter, value);
				}
			}
		}
	}
	
	/**
	 * @param oauthCallback the oauthCallback to set
	 */
	public void setOAuthCallback(String oauthCallback) {
		if (oauthCallback != null) {
			setOAuthParameter(OAUTH_CALLBACK, oauthCallback);
		}
	}
	
	/**
	 * @param oauthCallbackConfirmed the oauthCallbackConfirmed to set
	 */
	public void setOAuthCallbackConfirmed(String oauthCallbackConfirmed) {
		if (oauthCallbackConfirmed != null) {
			setOAuthParameter(OAUTH_CALLBACK_CONFIRMED, oauthCallbackConfirmed);
		}
	}
	
	/**
	 * @param oauthConsumerKey the oauthConsumerKey to set
	 */
	public void setOAuthConsumerKey(String oauthConsumerKey) {
		if (oauthConsumerKey != null) {
			setOAuthParameter(OAUTH_CONSUMER_KEY, oauthConsumerKey);
		}
	}
	
	/**
	 * @param oauthNonce the oauthNonce to set
	 */
	public void setOAuthNonce(String oauthNonce) {
		if (oauthNonce != null) {
			setOAuthParameter(OAUTH_NONCE, oauthNonce);
		}
	}
	
	/**
	 * @param realm the realm to set
	 */
	public void setOAuthRealm(String realm) {
		if (realm != null && !realm.isEmpty()) {
			setOAuthParameter(OAUTH_REALM, realm);
		}
	}
	
	/**
	 * @param oauthSignature the oauthSignature to set
	 */
	public void setOAuthSignature(String oauthSignature) {
		if (oauthSignature != null) {
			setOAuthParameter(OAUTH_SIGNATURE, oauthSignature);
		}
	}
	
	/**
	 * @param oauthSignatureMethod the oauthSignatureMethod to set
	 */
	public void setOAuthSignatureMethod(String oauthSignatureMethod) {
		if (oauthSignatureMethod != null) {
			setOAuthParameter(OAUTH_SIGNATURE_METHOD, oauthSignatureMethod);
		}
	}
	
	/**
	 * @param oauthTimestamp the oauthTimestamp to set
	 */
	public void setOAuthTimestamp(String oauthTimestamp) {
		if (oauthTimestamp != null) {
			setOAuthParameter(OAUTH_TIMESTAMP, oauthTimestamp);
		}
	}
	
	/**
	 * @param oauthToken the oauthToken to set
	 */
	public void setOAuthToken(String oauthToken) {
		if (oauthToken != null) {
			setOAuthParameter(OAUTH_TOKEN, oauthToken);
		}
	}
	
	/**
	 * @param oauthTokenSecret the oauthTokenSecret to set
	 */
	public void setOAuthTokenSecret(String oauthTokenSecret) {
		if (oauthTokenSecret != null) {
			setOAuthParameter(OAUTH_TOKEN_SECRET, oauthTokenSecret);
		}
	}
	
	/**
	 * @param oauthVerifier the oauthVerifier to set
	 */
	public void setOAuthVerifier(String oauthVerifier) {
		if (oauthVerifier != null) {
			setOAuthParameter(OAUTH_VERIFIER, oauthVerifier);
		}
	}
	
	/**
	 * @param oauthVersion the oauthVersion to set
	 */
	public void setOAuthVersion(String oauthVersion) {
		if (oauthVersion != null) {
			setOAuthParameter(OAUTH_VERSION, oauthVersion);
		}
	}
	
	public final Map<String, String> getOAuthParameters() {
		return parameterMap;
	}
	
	public String getValue(String key) {
		return parameterMap.get(key);
	}
	
	private void setOAuthParameter(String key, String value) {
		if (key != null && !key.isEmpty() && value != null) {
			if (!key.startsWith("oauth_") && !OAUTH_REALM.equals(key)) {
				throw new IllegalArgumentException("OAuth parameters must start with \"oauth_\" or is \"" + OAUTH_REALM + "\".");
			}
			
			parameterMap.put(key, value);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OAuthParameters [parameterMap=" + parameterMap + "]";
	}
}
