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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.oauth.enums.GrantType;
import net.oauth.enums.ResponseType;
import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth2Parameters;
import net.oauth.provider.OAuth2ServiceProvider;
import net.oauth.token.oauth2.AccessToken;
import net.oauth.util.OAuth1Util;
import net.oauth.util.OAuth2Util;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import sun.misc.BASE64Encoder;

import com.neurologic.exception.HttpException;
import com.neurologic.http.HttpClient;

/**
 * @author Bienfait Sindi
 * @since 24 July 2010
 *
 */
public class OAuth2Consumer extends OAuthConsumer<OAuth2ServiceProvider> {

	private static final Logger logger = Logger.getLogger(OAuth2Consumer.class);
	private static final String URL_ENCODING = "UTF-8";
	private static final String ERROR_NO_SERVICE_PROVIDER = "No OAuth Service Provider has been provided. Call \"setServiceProvider()\" method to assign an OAuth Service Provider.";
	private static final String ERROR_NO_REDIRECT_URI = "Required: \"" + OAuth2Parameters.REDIRECT_URI + "\".";
	
	private String clientID;
	private String clientSecret;
	
	/**
	 * @param clientID
	 * @param clientSecret
	 */
	public OAuth2Consumer(String clientID, String clientSecret) {
		this(clientID, clientSecret, null);
	}
	
	/**
	 * @param clientID
	 * @param clientSecret
	 * @param serviceProvider
	 */
	public OAuth2Consumer(String clientID, String clientSecret, OAuth2ServiceProvider serviceProvider) {
		super(serviceProvider);
		this.clientID = clientID;
		this.clientSecret = clientSecret;
	}
	
	public String generateRequestAuthorizationUrl(ResponseType responseType, String redirectUri) throws OAuthException {
		return generateRequestAuthorizationUrl(responseType, redirectUri,  null);
	}
	
	public String generateRequestAuthorizationUrl(ResponseType responseType, String redirectUri, String state, String... scope) throws OAuthException {
		return generateRequestAuthorizationUrl(responseType, redirectUri, state, " ", scope);
	}

	public String generateRequestAuthorizationUrl(ResponseType responseType, String redirectUri, String state, String scopeDelimiter, String... scope) throws OAuthException {
		if (getServiceProvider() == null) {
			throw new OAuthException(ERROR_NO_SERVICE_PROVIDER);
		}
		
		if (responseType == null) {
			throw new OAuthException("REQUIRED - Expected one of these response_type: \"token\", \"code\" OR \"code_and_token\". See " + ResponseType.class.getName());
		}
		
		if (redirectUri == null || redirectUri.isEmpty()) {
			throw new OAuthException(ERROR_NO_REDIRECT_URI);
		}
		
		StringBuffer sb = new StringBuffer();

		try {
			sb.append(getServiceProvider().getAuthorizationUrl());
			if (getServiceProvider().getAuthorizationUrl().indexOf('?') > -1) {
				sb.append('&');
			} else {
				sb.append('?');
			}
			sb.append(OAuth2Parameters.RESPONSE_TYPE + "=" + URLEncoder.encode(responseType.toString(), URL_ENCODING));
			
			sb.append('&');
			sb.append(OAuth2Parameters.CLIENT_ID + "=" + URLEncoder.encode(clientID, URL_ENCODING));
			
			sb.append('&');
			sb.append(OAuth2Parameters.REDIRECT_URI + "=" + URLEncoder.encode(redirectUri, URL_ENCODING));
			
			if (scope != null && scope.length > 0) {
				sb.append('&');
				sb.append(OAuth2Parameters.SCOPE + "=" + encodeScope(scope, scopeDelimiter));
			}
			
			if (state != null && !state.isEmpty()) {
				sb.append('&');
				sb.append("state=" + URLEncoder.encode(state, URL_ENCODING));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(e.getLocalizedMessage(), e);
			throw new OAuthException("Encoding Exception:", e);
		}
		
		return sb.toString();
	}
	
	public AccessToken requestAcessToken(GrantType grantType, OAuth2Parameters parameters) throws OAuthException {
		return requestAcessToken(grantType, parameters, (String[])null);
	}
	
	public AccessToken requestAcessToken(GrantType grantType, OAuth2Parameters parameters, String... scope) throws OAuthException {
		return requestAcessToken(grantType, parameters, " ", scope);
	}
	
	public AccessToken requestAcessToken(GrantType grantType, OAuth2Parameters parameters, String scopeDelimiter, String... scope) throws OAuthException {
		if (getClient() == null) {
			throw new OAuthException("HttpClient is required.");
		}
		
		if (getServiceProvider() == null) {
			throw new OAuthException(ERROR_NO_SERVICE_PROVIDER);
		}
		
		if (grantType == null) {
			throw new OAuthException("REQUIRED - Expected one of these grant_type: \"authorization_code\", \"password\", \"assertion\", \"refresh_token\" OR \"none\". See net.oauth.enums.GrantType");
		}
		
		if (parameters.isEmpty()) {
			if (grantType != GrantType.NONE) {
				throw new OAuthException("No OAuth 2 required parameters are provided for grant_type '" + grantType.toString() + "' . Cannot continue request.");
			}
		}
		
		if(grantType == GrantType.AUTHORIZATION_CODE) {
			if (parameters.getCode() == null || parameters.getRedirectUri() == null) {
				throw new OAuthException("REQUIRED: BOTH \"" + OAuth2Parameters.CODE + "\" and \"" + OAuth2Parameters.REDIRECT_URI + "\".");
			}
		} else if (grantType == GrantType.PASSWORD) {
			if (parameters.getUserName() == null || parameters.getPassword() == null) {
				throw new OAuthException("REQUIRED: BOTH \"" + OAuth2Parameters.USERNAME + "\" and \"" + OAuth2Parameters.PASSWORD + "\".");
			}
		} else if (grantType == GrantType.ASSERTION) {
			if (parameters.getAssertionType() == null || parameters.getAssertion() == null) {
				throw new OAuthException("REQUIRED: BOTH \"" + OAuth2Parameters.ASSERTION_TYPE + "\" and \"" + OAuth2Parameters.ASSERTION + "\".");
			}
		} else if (grantType == GrantType.REFRESH_TOKEN) {
			if (parameters.getRefreshToken() == null) {
				throw new OAuthException("REQUIRED: \"" + OAuth2Parameters.REFRESH_TOKEN + "\".");
			}
		}
		
		parameters.setGrantType(grantType.toString());
		parameters.setClientId(clientID);
	
		InputStream in = null;
		HttpClient client = getClient();
		
		try {
			client.addRequestHeader(HttpClient.HEADER_AUTHORIZATION, "Basic " + base64Encode(clientID, clientSecret));
			for (Entry<String, String> entry : parameters.getOAuthParameters().entrySet()) {
				client.addParameter(entry.getKey(), entry.getValue());
			}
			
			in = client.connect("POST", getServiceProvider().getAccessTokenUrl());
			String contentType = client.getResponseHeaderValue("Content-Type");
			if (contentType == null) contentType = "";
			
			logger.info("Content-Type: " + contentType);
			
			String charset = "";
			int semicolonPos = contentType.indexOf(';');
			
			if (semicolonPos > 0) {
				String _charset = contentType.substring(semicolonPos + 1).trim();
				if (_charset.startsWith("charset")) {
					charset = _charset.substring(_charset.indexOf('=') + 1);
				}
				contentType = contentType.substring(0, semicolonPos);
			}
			
			Map<String, String> responseAttributes = null;
			String response = streamToString(in, charset);
			if ("application/json".equals(contentType) || (response.startsWith("{") && response.endsWith("}"))) {
				JSONObject jsonResponse = new JSONObject(response);
				if (jsonResponse != null) {
					if (jsonResponse.has("error")) {
						throw new OAuthException("Error getting access token: " + System.getProperty("line.separator") + jsonResponse.toString());
					}
					
					responseAttributes = parseJSONObject(jsonResponse);
				}
			} else if ("text/plain".equals(contentType) || (response.contains("=") && response.contains("&"))) {
				responseAttributes = OAuth1Util.parseQueryString(response);
			}
			
			return OAuth2Util.createAccessToken(responseAttributes);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("Error closing InputStream.", e);
				}
			}
			
			/*if (client != null) {
				client.close();
				client = null;
			}*/
		}
	}
	
	private String base64Encode(String id, String secret) throws UnsupportedEncodingException {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode((id + ":" + secret).getBytes(URL_ENCODING));
	}
	
	private String encodeScope(String[] scope, String delimiter) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		
		if (scope != null && scope.length > 0) {
			for (String _scope : scope) {
				if (_scope.isEmpty()) continue;
				
				if (sb.length() > 0) {
					sb.append(delimiter);
				}
				
				sb.append(URLEncoder.encode(_scope, URL_ENCODING));
			}
		}
		
		return sb.toString();
	}
	
	private String streamToString(InputStream stream, String charset) throws IOException {
		if (stream == null) {
			return null;
		}
		
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		int c;
		
		while ((c = stream.read()) != -1) {
			byteArray.write(c);
		}

		return new String(byteArray.toByteArray(), charset);
	}
	
	private synchronized Map<String, String> parseJSONObject(JSONObject json) throws JSONException {
		Map<String, String> parameters = null;
		
		if (json != null && json.length() > 0) {
			parameters = new LinkedHashMap<String, String>();
			@SuppressWarnings("unchecked")
			Iterator<String> iter = json.keys();
			
			if (iter != null) {
				while (iter.hasNext()) {
					String key = iter.next();
					parameters.put(key, json.getString(key));
				}
			}
		}
		
		return parameters;
	}
}
