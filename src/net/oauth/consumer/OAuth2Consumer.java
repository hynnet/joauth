/**
 * 
 */
package net.oauth.consumer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.oauth.connection.HttpConnection;
import net.oauth.enums.GrantType;
import net.oauth.enums.ResponseType;
import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth2Parameters;
import net.oauth.provider.OAuth2ServiceProvider;
import net.oauth.util.OAuthUtil;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Bienfait Sindi
 * @since 24 July 2010
 *
 */
public class OAuth2Consumer {

	private static final Logger logger = Logger.getLogger(OAuth2Consumer.class);
	private static final String URL_ENCODING = "UTF-8";
	private static final String ERROR_NO_SERVICE_PROVIDER = "No OAuth Service Provider has been provided. Call \"setServiceProvider\" method to assign an OAuth Service Provider.";
	private static final String ERROR_NO_REDIRECT_URI = "Required: \"redirect_uri\".";
	
	private String clientID;
	private String clientSecret;
	private OAuth2ServiceProvider serviceProvider;
	
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
		this.clientID = clientID;
		this.clientSecret = clientSecret;
		this.serviceProvider = serviceProvider;
	}

	/**
	 * @param serviceProvider the serviceProvider to set
	 */
	public void setServiceProvider(OAuth2ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public String generateRequestAuthorizationUrl(ResponseType responseType, String redirectUri, String state, String... scope) throws OAuthException {
		if (serviceProvider == null) {
			throw new OAuthException(ERROR_NO_SERVICE_PROVIDER);
		}
		
		if (responseType == null) {
			throw new OAuthException("REQUIRED - Expected one of these response_type:  \"token\", \"code\" OR \"code_and_token\". See net.oauth.enums.ResponseType");
		}
		
		if (redirectUri == null || redirectUri.isEmpty()) {
			throw new OAuthException(ERROR_NO_REDIRECT_URI);
		}
		
		StringBuffer sb = new StringBuffer();

		try {
			sb.append(serviceProvider.getAuthorizationUrl());
			if (serviceProvider.getAuthorizationUrl().indexOf('?') > -1) {
				sb.append('&');
			} else {
				sb.append('?');
			}
			sb.append("response_type=" + URLEncoder.encode(responseType.toString(), URL_ENCODING));
			
			sb.append('&');
			sb.append(OAuth2Parameters.CLIENT_ID + "=" + URLEncoder.encode(clientID, URL_ENCODING));
			
			sb.append('&');
			sb.append(OAuth2Parameters.REDIRECT_URI + "=" + URLEncoder.encode(redirectUri, URL_ENCODING));
			
			if (scope != null && scope.length > 0) {
				sb.append('&');
				sb.append(OAuth2Parameters.SCOPE + "=" + encodeScope(scope));
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
	
	public Map<String, String> requestAcessToken(GrantType grantType, OAuth2Parameters parameters, String... scope) throws OAuthException {
		if (serviceProvider == null) {
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
				throw new OAuthException("REQUIRED: BOTH \"" + OAuth2Parameters.USERNAME + "\" and \"" +  OAuth2Parameters.PASSWORD + "\".");
			}
		} else if (grantType == GrantType.ASSERTION) {
			if (parameters.getAssertionType() == null || parameters.getAssertion() == null) {
				throw new OAuthException("REQUIRED: BOTH \"" + OAuth2Parameters.ASSERTION_TYPE + "\" and \"" +  OAuth2Parameters.ASSERTION + "\".");
			}
		} else if (grantType == GrantType.REFRESH_TOKEN) {
			if (parameters.getRefreshToken() == null) {
				throw new OAuthException("REQUIRED: \"" + OAuth2Parameters.REFRESH_TOKEN + "\".");
			}
		}
		
		parameters.addParameterValue("grant_type", grantType.toString());
		parameters.addParameterValue(OAuth2Parameters.CLIENT_ID, clientID);
		parameters.addParameterValue("client_secret", clientSecret);
		
		HttpConnection connection = getHttpConnection();
		try {
			StringBuffer sb = new StringBuffer();		
			for (String parameter: parameters.getParameterNames()) {
				if (sb.length() > 0)
					sb.append('&');
				
				sb.append(URLEncoder.encode(parameter, URL_ENCODING) + "=" + URLEncoder.encode(parameters.getParameterValue(parameter), URL_ENCODING));
			}
			
			//Scope
			if (scope != null && scope.length > 0) {
				sb.append('&');
				sb.append(OAuth2Parameters.SCOPE + "=" + encodeScope(scope));
			}
			
			String serverUrl  = serviceProvider.getAccessTokenUrl();
			if (serviceProvider.getAuthorizationUrl().indexOf('?') > -1) {
				serverUrl += "&";
			} else {
				serverUrl += "?";
			}
			
			serverUrl += sb.toString();
			
			//Connect
			connection.establishConnection(serverUrl, "POST");
			
			String type = connection.getHeaderValue("Content-Type");
			String contentType = "";
			String charset = "";
			int semicolonPos = type.indexOf(';');
			
			if (semicolonPos > -1) {
				contentType = type.substring(0, semicolonPos);
				String _charset = type.substring(semicolonPos + 1).trim();
				if (_charset.startsWith("charset")) {
					charset = _charset.substring(_charset.indexOf('=') + 1);
				}
			}
			
			String response = streamToString(connection.getInputStream(), charset);
			if ("text/plain".equals(contentType)) {
				return OAuthUtil.parseQueryString(response);
			}
			
			if ("application/json".equals(response)) {
				parseJSONObject(new JSONObject(response));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(e.getLocalizedMessage(), e);
			throw new OAuthException("Encoding Exception:", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e);
		} finally {
			if (connection != null && connection.connectionEstablished()) {
				connection.disconnect();
				connection = null;
			}
		}
		
		return null;
	}
	
	private HttpConnection getHttpConnection() {
		HttpConnection httpConnnection = new HttpConnection();
		httpConnnection.setEnableLogging(true);
		httpConnnection.setEnableRead(true);
		httpConnnection.setEnableWrite(true);
		
		return httpConnnection;
	}
	
	private String encodeScope(String[] scope) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		
		if (scope != null && scope.length > 0) {
			for (String _scope : scope) {
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
			parameters = new HashMap<String, String>();
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
