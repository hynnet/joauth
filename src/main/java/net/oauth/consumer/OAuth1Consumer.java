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
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import net.oauth.browser.BrowserManager;
import net.oauth.exception.OAuthException;
import net.oauth.http.HttpUtil;
import net.oauth.http.exception.HttpException;
import net.oauth.parameters.HeaderKeyValuePair;
import net.oauth.parameters.OAuth1Parameters;
import net.oauth.provider.OAuth1ServiceProvider;
import net.oauth.signature.ConsumerSecretBasedOAuthSignature;
import net.oauth.signature.OAuthSignature;
import net.oauth.token.oauth1.AccessToken;
import net.oauth.token.oauth1.RequestToken;
import net.oauth.util.OAuth1Util;


/**
 * @author Bienfait Sindi
 * @since 31 March 2010
 *
 */
public class OAuth1Consumer extends OAuthConsumer<OAuth1ServiceProvider> {

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
		
		if (consumerKey == null || consumerKey.isEmpty()) {
			throw new IllegalArgumentException("No consumer key provided.");
		}
		
		if (consumerSecret == null || consumerSecret.isEmpty()) {
			throw new IllegalArgumentException("No consumer secret provided.");
		}
		
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}
	
	/**
	 * The core engine for OAuth authorization token request. This method creates an RFC 5849 request to the service provider for a token.
	 *  
	 * @param url
	 * @param realm
	 * @param requestToken
	 * @param requestTokenSecret
	 * @param verifier
	 * @param callbackUrl
	 * @param additionalParameters
	 * @param signature
	 * @return
	 * @throws OAuthException
	 */
	private Map<String, String> createOAuthTokenResponse(String url, String realm, String requestToken, String requestTokenSecret, String verifier, String callbackUrl, Map<String, String[]> additionalParameters, OAuthSignature signature) throws OAuthException {
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
			((ConsumerSecretBasedOAuthSignature)signature).setTokenSecret(requestTokenSecret);
		}
		
		Map<String, String> responseMap = null;
		try {
			final String httpRequestMethod = "POST";
			OAuth1Parameters oauthParameters = new OAuth1Parameters();
			oauthParameters.setOAuthConsumerKey(consumerKey);
			oauthParameters.setOAuthNonce(OAuth1Util.getNONCE());
			oauthParameters.setOAuthSignatureMethod(signature.getOAuthSignatureMethod());
			oauthParameters.setOAuthTimestamp(Long.toString(OAuth1Util.getTimestamp()));
			oauthParameters.setOAuthVersion(OAuth1ServiceProvider.PROTOCOL_VERSION);
			if (callbackUrl != null && !callbackUrl.isEmpty()) {
				oauthParameters.setOAuthCallback(callbackUrl);
			}
			
			String baseString = OAuth1Util.generateSignatureBaseString(httpRequestMethod, url, oauthParameters, additionalParameters);
			if (logger.isDebugEnabled()) {
				logger.debug("OAuth Base String: " + baseString);
			}
			
			oauthParameters.setOAuthSignature(signature.sign(baseString));
			getClient().addRequestHeader(HTTP_HEADER_AUTHORIZATION, "OAuth " + OAuth1Util.toQueryString(realm, oauthParameters, new HeaderKeyValuePair()));
			String data = streamToString(getClient().connect(httpRequestMethod, url));
			if (getClient().getResponseHeaderValue("Content-Type").contains("application/x-www-form-urlencoded")) {
				data = URLDecoder.decode(data, "UTF-8");
			}
			
			if (getClient().getStatusCode() != 200) {
				throw new OAuthException("HTTP/1.0 " + getClient().getStatusCode() + " " + getClient().getStatusReason() + "\n" + data);
			}

			responseMap = generateToken(data);
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
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			logger.error("URI Syntax Exception: ", e);
			throw new OAuthException(e);
		}
		
		return responseMap;
		
	}
	
	/**
	 * This method returns an unauthorized {@link RequestToken}.
	 * @param realm
	 * @param callbackUrl
	 * @param additionalParameters
	 * @param signature
	 * @return
	 * @throws OAuthException
	 */
	public RequestToken requestUnauthorizedToken(String realm, String callbackUrl, Map<String, String[]> additionalParameters, OAuthSignature signature) throws OAuthException {
		
		Map<String, String> responseMap = createOAuthTokenResponse(getServiceProvider().getRequestTokenUrl(), realm, null, null, null, callbackUrl, additionalParameters, signature);
		return new RequestToken(responseMap.get(OAuth1Parameters.OAUTH_TOKEN), responseMap.get(OAuth1Parameters.OAUTH_TOKEN_SECRET), Boolean.valueOf(responseMap.get(OAuth1Parameters.OAUTH_CALLBACK_CONFIRMED)));
	}
	
	/**
	 * Generates an OAuth Authorization String (which the client will need to paste to their browser or use {@link BrowserManager} to browser.
	 * 
	 * @param requestToken
	 * @param additionalParameters
	 * @return
	 * @throws OAuthException
	 */
	public String createOAuthUserAuthorizationUrl(RequestToken requestToken, Map<String, String[]> additionalParameters) throws OAuthException {
		if (requestToken == null) {
			throw new OAuthException("No Request Token provided.");
		}
		
		return createOAuthUserAuthorizationUrl(requestToken.getToken(), additionalParameters);
	}
	
	private String createOAuthUserAuthorizationUrl(String requestToken, Map<String, String[]> additionalParameters) throws OAuthException {
		if (getServiceProvider() == null) {
			throw new OAuthException(ERROR_NO_SERVICE_PROVIDER);
		}
		
		if (additionalParameters == null) {
			additionalParameters = new HashMap<String, String[]>();
		}
		
		if (requestToken != null && !requestToken.isEmpty()) {
			additionalParameters.put(OAuth1Parameters.OAUTH_TOKEN, new String[] {requestToken});
		}
		
		String oauthAuthorizeUrl = getServiceProvider().getAuthorizationUrl();	
		return oauthAuthorizeUrl + ((oauthAuthorizeUrl.indexOf('?') > -1) ? "&" : "?") + HttpUtil.toParameterQueryString(additionalParameters);
	}
	
	/**
	 * This method requests for an {@link AccessToken}/
	 * @param realm
	 * @param requestToken
	 * @param verifier
	 * @param signature
	 * @return
	 * @throws OAuthException
	 */
	public AccessToken requestAccessToken(String realm, RequestToken requestToken, String verifier, OAuthSignature signature) throws OAuthException {
		return requestAccessToken(realm, requestToken, verifier, null, signature);
	}
	
	/**
	 * This method requests for an {@link AccessToken} (with an additional HTTP parameters).
	 * @param realm
	 * @param requestToken
	 * @param verifier
	 * @param additionalParameters - HTTP parameters.
	 * @param signature
	 * @return
	 * @throws OAuthException
	 */
	public AccessToken requestAccessToken(String realm, RequestToken requestToken, String verifier, Map<String, String[]> additionalParameters, OAuthSignature signature) throws OAuthException {
		
		if (requestToken == null) {
			throw new OAuthException("No Request Token provided.");
		}
		
		if (verifier == null || verifier.isEmpty()) {
			throw new OAuthException("No " + OAuth1Parameters.OAUTH_VERIFIER + " provided.");
		}
		
		Map<String, String> responseMap = createOAuthTokenResponse(getServiceProvider().getAccessTokenUrl(), realm, requestToken.getToken(), requestToken.getTokenSecret(), verifier, null, additionalParameters, signature);
		return new AccessToken(responseMap.remove(OAuth1Parameters.OAUTH_TOKEN), responseMap.remove(OAuth1Parameters.OAUTH_TOKEN_SECRET), responseMap);
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
