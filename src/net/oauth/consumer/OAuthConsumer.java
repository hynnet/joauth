/**
 * 
 */
package net.oauth.consumer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.util.Map;

import net.oauth.connection.HttpConnection;
import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuthParameters;
import net.oauth.parameters.QueryKeyValuePair;
import net.oauth.provider.OAuthServiceProvider;
import net.oauth.signature.OAuthSignature;
import net.oauth.token.OAuthToken;
import net.oauth.util.OAuthUtil;

import org.apache.log4j.Logger;

/**
 * @author Bienfait Sindi
 * @since 31 March 2010
 *
 */
public class OAuthConsumer {

	private static final Logger logger = Logger.getLogger(OAuthConsumer.class);
	private static final String ERROR_NO_SERVICE_PROVIDER = "No OAuth Service Provider has been provided. Call \"setServiceProvider\" method to assign an OAuth Service Provider.";
	private static final String ERROR_NO_SIGNATURE = "No OAuth Signature (HMAC-SHA1, RSA-SHA1, PLAINTEXT) method provided.";
	
	private String consumerKey;
	private String consumerSecret;
	private OAuthServiceProvider serviceProvider;
	
	/**
	 * @param consumerKey
	 * @param consumerSecret
	 */
	public OAuthConsumer(String consumerKey, String consumerSecret) {
		this(consumerKey, consumerSecret, null);
	}

	/**
	 * @param consumerKey
	 * @param consumerSecret
	 * @param serviceProvider
	 */
	public OAuthConsumer(String consumerKey, String consumerSecret, OAuthServiceProvider serviceProvider) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.serviceProvider = serviceProvider;
	}

	/**
	 * @param consumerKey the consumerKey to set
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * @param consumerSecret the consumerSecret to set
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	/**
	 * @param serviceProvider the serviceProvider to set
	 */
	public void setServiceProvider(OAuthServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	
	public OAuthToken requestUnauthorizedToken(OAuthParameters oauthParameters, OAuthSignature signature) throws OAuthException {
		if (serviceProvider == null) {
			throw new OAuthException(ERROR_NO_SERVICE_PROVIDER);
		}
		
		if (signature == null) {
			throw new OAuthException(ERROR_NO_SIGNATURE);
		}
		
		if (oauthParameters == null) {
			oauthParameters = new OAuthParameters();
		}
		
		OAuthToken requestToken = null;
		HttpConnection connection = getHttpConnection();
		String requestTokenUrl = serviceProvider.getRequestTokenUrl();
		String httpRequestMethod = "POST";
		
		try {
			long timestamp = OAuthUtil.getTimestamp();
			oauthParameters.setOAuthConsumerKey(consumerKey);
			oauthParameters.setOAuthNonce(OAuthUtil.getNONCE());
			oauthParameters.setOAuthSignatureMethod(signature.getOAuthSignatureMethod());
			oauthParameters.setOAuthTimestamp(Long.toString(timestamp));
			oauthParameters.setOAuthVersion(OAuthServiceProvider.PROTOCOL_VERSION);
			
			Map<String, String> map = oauthParameters.getOAuthParameters();
			map.remove(OAuthParameters.OAUTH_REALM);
			
			String baseString = OAuthUtil.getSignatureBaseString(httpRequestMethod, requestTokenUrl, map);
			oauthParameters.setOAuthSignature(signature.sign(baseString, consumerSecret, null));
			
			connection.setParameters(oauthParameters.getOAuthParameters());
			connection.establishConnection(requestTokenUrl, httpRequestMethod);
			if (connection.getResponseCode() != 200) {
				throw new OAuthException("HTTP/1.0 " + connection.getResponseCode() + " " + connection.getResponseMessage());
			}
			requestToken = generateToken(connection.getInputStream());
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			logger.error("Security Exception: ", e);
			throw new OAuthException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Network Exception: ", e);
			throw new OAuthException(e);
		} finally {
			if (connection != null && connection.connectionEstablished()) {
				connection.disconnect();
				
//				try {
//					connection.getInputStream().close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					logger.error(e.getLocalizedMessage(), e);
//				}
			}
		}
		
		return requestToken;
	}
	
	public String createOAuthUserAuthorizationUrl(String requestToken, OAuthParameters parameters) throws OAuthException {
		if (serviceProvider == null) {
			throw new OAuthException(ERROR_NO_SERVICE_PROVIDER);
		}
		
		if (parameters == null) {
			parameters = new OAuthParameters();
		}
		
		if (requestToken != null && !requestToken.isEmpty()) {
			parameters.setOAuthToken(requestToken);
		}
		
		String oauthAuthorizeUrl = serviceProvider.getUserAuthorizationUrl();	
		return oauthAuthorizeUrl + ((oauthAuthorizeUrl.indexOf('?') > -1) ? "&" : "?") + OAuthUtil.getQueryString(parameters.getOAuthParameters(), new QueryKeyValuePair());
	}
	
	public OAuthToken requestAccessToken(OAuthToken requestToken, OAuthParameters oauthParameters, OAuthSignature signature) throws OAuthException {
		if (serviceProvider == null) {
			throw new OAuthException(ERROR_NO_SERVICE_PROVIDER);
		}
		
		if (signature == null) {
			throw new OAuthException(ERROR_NO_SIGNATURE);
		}
		
		if (oauthParameters == null) {
			oauthParameters = new OAuthParameters();
		}
		
		OAuthToken accessToken = null;
		HttpConnection connection = getHttpConnection();
		String accessTokenUrl = serviceProvider.getAccessTokenUrl();
		String httpRequestMethod = "POST";
		
		try {
			long timestamp = OAuthUtil.getTimestamp();
			oauthParameters.setOAuthConsumerKey(consumerKey);
			oauthParameters.setOAuthNonce(OAuthUtil.getNONCE());
			oauthParameters.setOAuthSignatureMethod(signature.getOAuthSignatureMethod());
			oauthParameters.setOAuthTimestamp(Long.toString(timestamp));
			oauthParameters.setOAuthToken(requestToken.getToken());
			oauthParameters.setOAuthVersion(OAuthServiceProvider.PROTOCOL_VERSION);
			
			Map<String, String> map = oauthParameters.getOAuthParameters();
			map.remove(OAuthParameters.OAUTH_REALM);
			
			String baseString = OAuthUtil.getSignatureBaseString(httpRequestMethod, accessTokenUrl, map);
			oauthParameters.setOAuthSignature(signature.sign(baseString, consumerSecret, requestToken.getTokenSecret()));
			
			connection.setParameters(oauthParameters.getOAuthParameters());
			connection.establishConnection(accessTokenUrl, httpRequestMethod);
			if (connection.getResponseCode() != 200) {
				throw new OAuthException("HTTP/1.0 " + connection.getResponseCode() + " " + connection.getResponseMessage());
			}
			requestToken = generateToken(connection.getInputStream());
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e.getLocalizedMessage(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e.getLocalizedMessage(), e);
		} finally {
			if (connection != null && connection.connectionEstablished()) {
				connection.disconnect();
//				try {
//					connection.getInputStream().close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					logger.error(e.getLocalizedMessage(), e);
//				}
			}
		}
		
		return accessToken;
	}
	
	private HttpConnection getHttpConnection() {
		HttpConnection httpConnnection = new HttpConnection();
		httpConnnection.setEnableLogging(true);
		httpConnnection.setEnableRead(true);
		httpConnnection.setEnableWrite(true);
		
		return httpConnnection;
	}
	
	private OAuthToken generateToken(InputStream inputStream) throws IOException {
		OAuthToken token = null;
		
		if (inputStream != null) {
			StringWriter sw = new StringWriter();
			int c;
			
			while ((c = inputStream.read()) != -1) {
				sw.write(c);
			}

			String data = sw.toString();
			if (data.split("\r\n|\r|\n").length > 1) {//First line always end with a \r\n
				throw new IOException("OAuth Error: \n\n" + data);
			}
			
			//Do we have callback?
			int questionMarkIndex = data.indexOf('?'); 
			if (questionMarkIndex > -1) {
				data = data.substring(questionMarkIndex + 1);
			}
			
			String oauth_token = null;
			String oauth_token_secret = null;
			
			for (String t : data.split("&")) {
				int equalIndex = t.indexOf('=');
				
				String name = t.substring(0, equalIndex);
				String value = t.substring(equalIndex + 1);
				
				if ("oauth_token".equals(name)) {
					oauth_token = value;
				} else if ("oauth_token_secret".equals(name)) {
					oauth_token_secret = value;
				}
			}
			
			token = new OAuthToken(oauth_token, oauth_token_secret);
		}
		
		return token;
	}
}
