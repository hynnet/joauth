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
package net.oauth.consumer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.HeaderKeyValuePair;
import net.oauth.parameters.OAuth1Parameters;
import net.oauth.parameters.QueryKeyValuePair;
import net.oauth.provider.OAuth1ServiceProvider;
import net.oauth.signature.ConsumerSecretBasedOAuthSignature;
import net.oauth.signature.OAuthSignature;
import net.oauth.token.oauth1.AccessToken;
import net.oauth.token.oauth1.AuthorizedToken;
import net.oauth.token.oauth1.RequestToken;
import net.oauth.util.OAuth1Util;

import org.apache.log4j.Logger;

import com.neurologic.exception.HttpException;
import com.neurologic.http.HttpClient;

/**
 * @author Bienfait Sindi
 * @since 31 March 2010
 *
 */
public class OAuth1Consumer extends OAuthConsumer<OAuth1ServiceProvider> {

	private static final Logger logger = Logger.getLogger(OAuth1Consumer.class);
	private static final String ERROR_NO_SERVICE_PROVIDER = "No OAuth Service Provider has been provided. Call \"setServiceProvider()\" method to assign an OAuth Service Provider.";
	private static final String ERROR_NO_SIGNATURE = "No OAuth Signature (HMAC-SHA1, RSA-SHA1, PLAINTEXT) method provided.";
	private static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
	
	private String consumerKey;
	private String consumerSecret;
	
	/**
	 * @param consumerKey
	 * @param consumerSecret
	 */
	public OAuth1Consumer(String consumerKey, String consumerSecret) {
		this(consumerKey, consumerSecret, null);
	}

	/**
	 * @param consumerKey
	 * @param consumerSecret
	 * @param serviceProvider
	 */
	public OAuth1Consumer(String consumerKey, String consumerSecret, OAuth1ServiceProvider serviceProvider) {
		super(serviceProvider);
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}
	
	public RequestToken requestUnauthorizedToken(String realm, String callbackUrl, Map<String, String> additionalParameters, OAuthSignature signature) throws OAuthException {
		if (getClient() == null) {
			throw new OAuthException("HttpClient is required.");
		}
		
		if (getServiceProvider() == null) {
			throw new OAuthException(ERROR_NO_SERVICE_PROVIDER);
		}
		
		if (signature == null) {
			throw new OAuthException(ERROR_NO_SIGNATURE);
		}
		
		if (signature instanceof ConsumerSecretBasedOAuthSignature) {
			((ConsumerSecretBasedOAuthSignature)signature).setConsumerSecret(consumerSecret);
		}
		
		RequestToken requestToken = null;
		String requestTokenUrl = getServiceProvider().getRequestTokenUrl();
		String httpRequestMethod = "POST";
		HttpClient client = getClient();
		
		try {
			long timestamp = OAuth1Util.getTimestamp();
			OAuth1Parameters oauthParameters = new OAuth1Parameters();
			oauthParameters.setOAuthConsumerKey(consumerKey);
			oauthParameters.setOAuthNonce(OAuth1Util.getNONCE());
			oauthParameters.setOAuthSignatureMethod(signature.getOAuthSignatureMethod());
			oauthParameters.setOAuthTimestamp(Long.toString(timestamp));
			oauthParameters.setOAuthVersion(OAuth1ServiceProvider.PROTOCOL_VERSION);
			if (callbackUrl != null && !callbackUrl.isEmpty()) {
				oauthParameters.setOAuthCallback(callbackUrl);
			}
			
			//We are cloning here...
			Map<String, String> map = oauthParameters.getOAuthParameters(); //new LinkedHashMap<String, String>(oauthParameters.getOAuthParameters());
			if (additionalParameters != null) {
				map.putAll(additionalParameters);
			}
			String baseString = OAuth1Util.getSignatureBaseString(httpRequestMethod, requestTokenUrl, map);
			oauthParameters.setOAuthSignature(signature.sign(baseString));
			
			//clean
			map.clear(); map = null;
			
			//Add realm (if provided)
			if (realm != null && !realm.isEmpty()) {
				oauthParameters.setOAuthRealm(realm);
			}
			
			client.addRequestHeader(HTTP_HEADER_AUTHORIZATION, "OAuth " + OAuth1Util.getQueryString(oauthParameters.getOAuthParameters(), new HeaderKeyValuePair()));
			String data = streamToString(client.connect(httpRequestMethod, requestTokenUrl));
			if (client.getStatusCode() != 200) {
				throw new OAuthException("HTTP/1.0 " + client.getStatusCode() + " " + client.getStatusReason() + "\n" + data);
			}

			Map<String, String> responseMap = generateToken(data);
			if (responseMap != null) {
				requestToken = new RequestToken(responseMap.get(OAuth1Parameters.OAUTH_TOKEN), responseMap.get(OAuth1Parameters.OAUTH_TOKEN_SECRET), Boolean.valueOf(responseMap.get(OAuth1Parameters.OAUTH_CALLBACK_CONFIRMED)));
			}
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			logger.error("Security Exception: ", e);
			throw new OAuthException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Network Exception: ", e);
			throw new OAuthException(e);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			logger.error("Http Exception: ", e);
			throw new OAuthException(e);
		} /*finally {
			if (client != null) {
				client.close();
			}
		}*/
		
		return requestToken;
	}
	
	public String createOAuthUserAuthorizationUrl(RequestToken requestToken, Map<String, String> additionalParameters) throws OAuthException {
		if (requestToken == null) {
			throw new OAuthException("No Request Token provided.");
		}
		
		return createOAuthUserAuthorizationUrl(requestToken.getToken(), additionalParameters);
	}
	
	private String createOAuthUserAuthorizationUrl(String requestToken, Map<String, String> additionalParameters) throws OAuthException {
		if (getServiceProvider() == null) {
			throw new OAuthException(ERROR_NO_SERVICE_PROVIDER);
		}
		
		if (additionalParameters == null) {
			additionalParameters = new HashMap<String, String>();
		}
		
		if (requestToken != null && !requestToken.isEmpty()) {
			additionalParameters.put(OAuth1Parameters.OAUTH_TOKEN, requestToken);
		}
		
		String oauthAuthorizeUrl = getServiceProvider().getAuthorizationUrl();	
		return oauthAuthorizeUrl + ((oauthAuthorizeUrl.indexOf('?') > -1) ? "&" : "?") + OAuth1Util.getQueryString(additionalParameters, new QueryKeyValuePair());
	}
	
	public AccessToken requestAccessToken(String realm, RequestToken requestToken, AuthorizedToken authorizedToken, OAuthSignature signature) throws OAuthException {
		if (getClient() == null) {
			throw new OAuthException("HttpClient is required.");
		}
		
		if (getServiceProvider() == null) {
			throw new OAuthException(ERROR_NO_SERVICE_PROVIDER);
		}
		
		if (requestToken == null) {
			throw new OAuthException("No Request Token provided.");
		}
		
		if (authorizedToken == null) {
			throw new OAuthException("No Authorized Token provided.");
		}
		
		if (!requestToken.getToken().equals(authorizedToken.getToken())) {
			throw new OAuthException("Request Token and Authorized token don't match! (" + requestToken.getToken() + " != " + authorizedToken.getToken() + ")");
		}
		
		if (signature == null) {
			throw new OAuthException(ERROR_NO_SIGNATURE);
		}
		
		if (signature instanceof ConsumerSecretBasedOAuthSignature) {
			((ConsumerSecretBasedOAuthSignature)signature).setConsumerSecret(consumerSecret);
			((ConsumerSecretBasedOAuthSignature)signature).setTokenSecret(requestToken.getTokenSecret());
		}
		
		AccessToken accessToken = null;
		HttpClient client = getClient();
		String accessTokenUrl = getServiceProvider().getAccessTokenUrl();
		String httpRequestMethod = "POST";
		
		try {
			long timestamp = OAuth1Util.getTimestamp();
			OAuth1Parameters oauthParameters = new OAuth1Parameters();
			oauthParameters.setOAuthConsumerKey(consumerKey);
			oauthParameters.setOAuthNonce(OAuth1Util.getNONCE());
			oauthParameters.setOAuthSignatureMethod(signature.getOAuthSignatureMethod());
			oauthParameters.setOAuthTimestamp(Long.toString(timestamp));
			oauthParameters.setOAuthToken(requestToken.getToken());
			oauthParameters.setOAuthVersion(OAuth1ServiceProvider.PROTOCOL_VERSION);
			oauthParameters.setOAuthVerifier(authorizedToken.getVerifier());
			
			String baseString = OAuth1Util.getSignatureBaseString(httpRequestMethod, accessTokenUrl, oauthParameters.getOAuthParameters());
			oauthParameters.setOAuthSignature(signature.sign(baseString));
			
			//Add realm (if provided)
			if (realm != null && !realm.isEmpty()) {
				oauthParameters.setOAuthRealm(realm);
			}
			
			client.addRequestHeader(HTTP_HEADER_AUTHORIZATION, "OAuth " + OAuth1Util.getQueryString(oauthParameters.getOAuthParameters(), new HeaderKeyValuePair()));
			String data = streamToString(client.connect(httpRequestMethod, accessTokenUrl));
			if (client.getStatusCode() != 200) {
				throw new OAuthException("HTTP/1.0 " + client.getStatusCode() + " " + client.getStatusReason() + "\n" + data);
			}

			Map<String, String> responseMap = generateToken(data);
			if (responseMap != null) {
				accessToken = new AccessToken(responseMap.remove(OAuth1Parameters.OAUTH_TOKEN), responseMap.remove(OAuth1Parameters.OAUTH_TOKEN_SECRET), responseMap);
			}
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e.getLocalizedMessage(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e.getLocalizedMessage(), e);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e.getLocalizedMessage(), e);
		} /*finally {
			if (client != null) {
				client.close();
			}
		}*/
		
		return accessToken;
	}
	
	private String streamToString(InputStream inputStream) throws IOException {
		if (inputStream == null) {
			return null;
		}
		
		StringWriter sw = new StringWriter();
		int c;
		
		while ((c = inputStream.read()) != -1) {
			sw.write(c);
		}
		
		return sw.toString();
	}
	
	private Map<String, String> generateToken(String data) throws IOException {
		Map<String, String> attributes = null;
		
		if (data != null) {
			if (data.split("\r\n|\r|\n").length > 1) {//First line always end with a \r\n
				throw new IOException("OAuth Error: " + System.getProperty("line.separator") + data);
			}
			
			//Do we have callback?
			int questionMarkIndex = data.indexOf('?'); 
			if (questionMarkIndex > -1) {
				data = data.substring(questionMarkIndex + 1);
			}
			
			attributes = new HashMap<String, String>();
			
			for (String t : data.split("&")) {
				int equalIndex = t.indexOf('=');
				
				String name = t.substring(0, equalIndex);
				String value = t.substring(equalIndex + 1);
				attributes.put(name, value);
			}
		}
		
		return attributes;
	}
}
