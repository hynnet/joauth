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
import java.util.LinkedHashMap;
import java.util.Map;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.HeaderKeyValuePair;
import net.oauth.parameters.OAuthParameters;
import net.oauth.parameters.QueryKeyValuePair;
import net.oauth.provider.OAuth1ServiceProvider;
import net.oauth.signature.ConsumerSecretBasedOAuthSignature;
import net.oauth.signature.OAuthSignature;
import net.oauth.token.AccessToken;
import net.oauth.token.AuthorizedToken;
import net.oauth.token.RequestToken;
import net.oauth.util.OAuthUtil;

import org.apache.log4j.Logger;

import com.neurologic.exception.HttpException;
import com.neurologic.http.HttpClient;
import com.neurologic.http.impl.ApacheHttpClient;

/**
 * @author Bienfait Sindi
 * @since 31 March 2010
 *
 */
public class OAuth1Consumer {

	private static final Logger logger = Logger.getLogger(OAuth1Consumer.class);
	private static final String ERROR_NO_SERVICE_PROVIDER = "No OAuth Service Provider has been provided. Call \"setServiceProvider()\" method to assign an OAuth Service Provider.";
	private static final String ERROR_NO_SIGNATURE = "No OAuth Signature (HMAC-SHA1, RSA-SHA1, PLAINTEXT) method provided.";
	private static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
	
	private String consumerKey;
	private String consumerSecret;
	private OAuth1ServiceProvider serviceProvider;
	
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
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		setServiceProvider(serviceProvider);
	}

	/**
	 * @param serviceProvider the serviceProvider to set
	 */
	public void setServiceProvider(OAuth1ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	
	public RequestToken requestUnauthorizedToken(String realm, String callbackUrl, Map<String, String> additionalParameters, OAuthSignature signature) throws OAuthException {
		if (serviceProvider == null) {
			throw new OAuthException(ERROR_NO_SERVICE_PROVIDER);
		}
		
		if (signature == null) {
			throw new OAuthException(ERROR_NO_SIGNATURE);
		}
		
		if (signature instanceof ConsumerSecretBasedOAuthSignature) {
			((ConsumerSecretBasedOAuthSignature)signature).setConsumerSecret(consumerSecret);
		}
		
		RequestToken requestToken = null;
		String requestTokenUrl = serviceProvider.getRequestTokenUrl();
		String httpRequestMethod = "POST";
		HttpClient client = new ApacheHttpClient();
		
		try {
			long timestamp = OAuthUtil.getTimestamp();
			OAuthParameters oauthParameters = new OAuthParameters();
			oauthParameters.setOAuthConsumerKey(consumerKey);
			oauthParameters.setOAuthNonce(OAuthUtil.getNONCE());
			oauthParameters.setOAuthSignatureMethod(signature.getOAuthSignatureMethod());
			oauthParameters.setOAuthTimestamp(Long.toString(timestamp));
			oauthParameters.setOAuthVersion(OAuth1ServiceProvider.PROTOCOL_VERSION);
			if (callbackUrl != null) {
				oauthParameters.setOAuthCallback(callbackUrl);
			}
			
			if (oauthParameters.getOAuthParameterValue(OAuthParameters.OAUTH_TOKEN_SECRET) == null) {
				oauthParameters.setOAuthTokenSecret("");
			}
			
			//We are cloning here...
			Map<String, String> map = new LinkedHashMap<String, String>(oauthParameters.getOAuthParameters());
			if (additionalParameters != null) {
				map.putAll(additionalParameters);
			}
			String baseString = OAuthUtil.getSignatureBaseString(httpRequestMethod, requestTokenUrl, map);
			oauthParameters.setOAuthSignature(signature.sign(baseString));
			
			//Add realm (if provided)
			if (realm != null && !realm.isEmpty()) {
				oauthParameters.setOAuthRealm(realm);
			}
			
			client.addRequestHeader(HTTP_HEADER_AUTHORIZATION, "OAuth " + OAuthUtil.getQueryString(oauthParameters.getOAuthParameters(), new HeaderKeyValuePair()));
			String data = streamToString(client.connect(httpRequestMethod, requestTokenUrl));
			if (client.getStatusCode() != 200) {
				throw new OAuthException("HTTP/1.0 " + client.getStatusCode() + " " + client.getStatusReason() + "\n" + data);
			}

			Map<String, String> responseMap = generateToken(data);
			if (responseMap != null) {
				requestToken = new RequestToken(responseMap.get(OAuthParameters.OAUTH_TOKEN), responseMap.get(OAuthParameters.OAUTH_TOKEN_SECRET), Boolean.valueOf(responseMap.get(OAuthParameters.OAUTH_CALLBACK_CONFIRMED)));
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
		} finally {
			if (client != null) {
				client.close();
			}
		}
		
		return requestToken;
	}
	
	public String createOAuthUserAuthorizationUrl(RequestToken requestToken, Map<String, String> additionalParameters) throws OAuthException {
		if (requestToken == null) {
			throw new OAuthException("No Request Token provided.");
		}
		
		return createOAuthUserAuthorizationUrl(requestToken.getToken(), additionalParameters);
	}
	
	public String createOAuthUserAuthorizationUrl(String requestToken, Map<String, String> additionalParameters) throws OAuthException {
		if (serviceProvider == null) {
			throw new OAuthException(ERROR_NO_SERVICE_PROVIDER);
		}
		
		if (additionalParameters == null) {
			additionalParameters = new HashMap<String, String>();
		}
		
		if (requestToken != null && !requestToken.isEmpty()) {
			additionalParameters.put(OAuthParameters.OAUTH_TOKEN, requestToken);
		}
		
		String oauthAuthorizeUrl = serviceProvider.getUserAuthorizationUrl();	
		return oauthAuthorizeUrl + ((oauthAuthorizeUrl.indexOf('?') > -1) ? "&" : "?") + OAuthUtil.getQueryString(additionalParameters, new QueryKeyValuePair());
	}
	
	public AccessToken requestAccessToken(String realm, AuthorizedToken authorizedToken, String requestTokenSecret, OAuthSignature signature) throws OAuthException {
		if (serviceProvider == null) {
			throw new OAuthException(ERROR_NO_SERVICE_PROVIDER);
		}
		
		if (requestTokenSecret == null || requestTokenSecret.isEmpty()) {
			throw new OAuthException("Request '" + OAuthParameters.OAUTH_TOKEN_SECRET + "' is null or empty.");
		}
		
		if (signature == null) {
			throw new OAuthException(ERROR_NO_SIGNATURE);
		}
		
		if (signature instanceof ConsumerSecretBasedOAuthSignature) {
			((ConsumerSecretBasedOAuthSignature)signature).setConsumerSecret(consumerSecret);
			((ConsumerSecretBasedOAuthSignature)signature).setTokenSecret(requestTokenSecret);
		}
		
		AccessToken accessToken = null;
		HttpClient client = new ApacheHttpClient();
		String accessTokenUrl = serviceProvider.getAccessTokenUrl();
		String httpRequestMethod = "POST";
		
		try {
			long timestamp = OAuthUtil.getTimestamp();
			OAuthParameters oauthParameters = new OAuthParameters();
			oauthParameters.setOAuthConsumerKey(consumerKey);
			oauthParameters.setOAuthNonce(OAuthUtil.getNONCE());
			oauthParameters.setOAuthSignatureMethod(signature.getOAuthSignatureMethod());
			oauthParameters.setOAuthTimestamp(Long.toString(timestamp));
			oauthParameters.setOAuthToken(authorizedToken.getToken());
			oauthParameters.setOAuthVersion(OAuth1ServiceProvider.PROTOCOL_VERSION);
			oauthParameters.setOAuthVerifier(authorizedToken.getVerifier());
			
			String baseString = OAuthUtil.getSignatureBaseString(httpRequestMethod, accessTokenUrl, oauthParameters.getOAuthParameters());
			oauthParameters.setOAuthSignature(signature.sign(baseString));
			
			//Add realm (if provided)
			if (realm != null && !realm.isEmpty()) {
				oauthParameters.setOAuthRealm(realm);
			}
			
			client.addRequestHeader(HTTP_HEADER_AUTHORIZATION, "OAuth " + OAuthUtil.getQueryString(oauthParameters.getOAuthParameters(), new HeaderKeyValuePair()));
			String data = streamToString(client.connect(httpRequestMethod, accessTokenUrl));
			if (client.getStatusCode() != 200) {
				throw new OAuthException("HTTP/1.0 " + client.getStatusCode() + " " + client.getStatusReason() + "\n" + data);
			}

			Map<String, String> responseMap = generateToken(data);
			if (responseMap != null) {
				accessToken = new AccessToken(responseMap.remove(OAuthParameters.OAUTH_TOKEN), responseMap.remove(OAuthParameters.OAUTH_TOKEN_SECRET), responseMap);
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
		} finally {
			if (client != null) {
				client.close();
			}
		}
		
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
				throw new IOException("OAuth Error: \n\n" + data);
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
