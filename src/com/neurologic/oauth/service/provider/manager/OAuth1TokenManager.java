/**
 * 
 */
package com.neurologic.oauth.service.provider.manager;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuthParameters;
import net.oauth.provider.OAuth1ServiceProvider;
import net.oauth.signature.OAuthSignature;
import net.oauth.signature.OAuthSignatureMethod;
import net.oauth.token.oauth1.AccessToken;
import net.oauth.token.oauth1.AuthorizedToken;
import net.oauth.token.oauth1.RequestToken;
import net.oauth.util.OAuth1Util;

import org.apache.log4j.Logger;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.exception.OAuthRejectedException;
import com.neurologic.exception.StoreException;
import com.neurologic.exception.UsedNonceException;
import com.neurologic.exception.UsedTokenException;
import com.neurologic.oauth.builder.OAuthSignatureBuilder;
import com.neurologic.oauth.service.provider.generator.TokenStringGenerator;
import com.neurologic.oauth.service.provider.manager.store.DataStore;
import com.neurologic.oauth.service.provider.manager.store.data.ConsumerKeyStoreData;
import com.neurologic.oauth.service.provider.manager.store.data.oauth1.AccessTokenStoreData;
import com.neurologic.oauth.service.provider.manager.store.data.oauth1.RequestTokenStoreData;
import com.neurologic.oauth.service.provider.manager.store.data.oauth1.UsedNonceStoreData;
import com.neurologic.oauth.service.provider.manager.store.oauth1.AbstractUsedNonceDataStore;
import com.neurologic.oauth.util.ExceptionUtil;

/**
 * @author Buhake Sindi
 * @since 29 August 2011
 *
 */
public class OAuth1TokenManager extends AbstractOAuthTokenManager {
	
	private static final String NO_OAUTH_PARAMETERS = "No oauth parameters provided.";
	private static final Logger logger = Logger.getLogger(OAuth1TokenManager.class);
	private final Object lock = new Object();
	private TokenStringGenerator requestTokenGenerator;
	private TokenStringGenerator authVerifierGenerator;
	private TokenStringGenerator tokenSecretGenerator;
	private DataStore<RequestTokenStoreData> requestTokenStore;
	private AbstractUsedNonceDataStore usedNonceStore;
	private DataStore<AccessTokenStoreData> accessTokenStore;
	private long requestTokenValidity = 30 * 60 * 1000;	//30 minutes;
	private long usedNonceValidity = 15 * 60 * 1000;	//15 minutes
	private int tokenSecretLength = 32;
	
	/**
	 * @return the requestTokenGenerator
	 */
	public TokenStringGenerator getRequestTokenGenerator() {
		return requestTokenGenerator;
	}

	/**
	 * @param requestTokenGenerator the requestTokenGenerator to set
	 */
	public void setRequestTokenGenerator(TokenStringGenerator requestTokenGenerator) {
		this.requestTokenGenerator = requestTokenGenerator;
	}

	/**
	 * @return the authVerifierGenerator
	 */
	public TokenStringGenerator getAuthVerifierGenerator() {
		return authVerifierGenerator;
	}

	/**
	 * @param authVerifierGenerator the authVerifierGenerator to set
	 */
	public void setAuthVerifierGenerator(TokenStringGenerator authVerifierGenerator) {
		this.authVerifierGenerator = authVerifierGenerator;
	}

	/**
	 * @return the tokenSecretGenerator
	 */
	public TokenStringGenerator getTokenSecretGenerator() {
		return tokenSecretGenerator;
	}

	/**
	 * @param tokenSecretGenerator the tokenSecretGenerator to set
	 */
	public void setTokenSecretGenerator(TokenStringGenerator tokenSecretGenerator) {
		this.tokenSecretGenerator = tokenSecretGenerator;
	}

	/**
	 * @return the requestTokenStore
	 */
	public DataStore<RequestTokenStoreData> getRequestTokenStore() {
		return requestTokenStore;
	}

	/**
	 * @param requestTokenStore the requestTokenStore to set
	 */
	public void setRequestTokenStore(DataStore<RequestTokenStoreData> requestTokenStore) {
		this.requestTokenStore = requestTokenStore;
	}

	/**
	 * @return the usedNonceStore
	 */
	public AbstractUsedNonceDataStore getUsedNonceStore() {
		return usedNonceStore;
	}

	/**
	 * @param usedNonceStore the usedNonceStore to set
	 */
	public void setUsedNonceStore(AbstractUsedNonceDataStore usedNonceStore) {
		this.usedNonceStore = usedNonceStore;
	}

	/**
	 * @return the accessTokenStore
	 */
	public DataStore<AccessTokenStoreData> getAccessTokenStore() {
		return accessTokenStore;
	}

	/**
	 * @param accessTokenStore the accessTokenStore to set
	 */
	public void setAccessTokenStore(DataStore<AccessTokenStoreData> accessTokenStore) {
		this.accessTokenStore = accessTokenStore;
	}

	/**
	 * @return the requestTokenValidity
	 */
	public long getRequestTokenValidity() {
		return requestTokenValidity;
	}

	/**
	 * @param requestTokenValidity the requestTokenValidity to set
	 */
	public void setRequestTokenValidity(long requestTokenValidity) {
		this.requestTokenValidity = requestTokenValidity;
	}

	/**
	 * @return the usedNonceValidity
	 */
	public long getUsedNonceValidity() {
		return usedNonceValidity;
	}

	/**
	 * @param usedNonceValidity the usedNonceValidity to set
	 */
	public void setUsedNonceValidity(long usedNonceValidity) {
		this.usedNonceValidity = usedNonceValidity;
	}

	/**
	 * @return the tokenSecretLength
	 */
	public int getTokenSecretLength() {
		return tokenSecretLength;
	}

	/**
	 * @param tokenSecretLength the tokenSecretLength to set
	 */
	public void setTokenSecretLength(int tokenSecretLength) {
		this.tokenSecretLength = tokenSecretLength;
	}

	/**
	 * Create a {@link RequestToken} based on the oauth parameters received.
	 * 
	 * @param requestMethod an HTTP request method (must be <code>POST</code>).
	 * @param requestTokenUrl The service provider request token url endpoint.
	 * @param oauthParameters the OAuth Authorization parameter received from HTTP Authorization header.
	 * @return a request token, if all checks passed, null otherwise.
	 * @throws OAuthException
	 */
	public RequestToken createRequestToken(String requestMethod, String requestTokenUrl, OAuthParameters oauthParameters) throws OAuthException {
		RequestToken requestToken = null;
		
		try {
			synchronized (lock) {
				ExceptionUtil.throwIfNull(oauthParameters, NO_OAUTH_PARAMETERS);
				ExceptionUtil.throwIfNull(oauthParameters.getValue(OAuthParameters.OAUTH_CALLBACK), "oauth_callback is required.");
				
				String token = oauthParameters.getValue(OAuthParameters.OAUTH_TOKEN);
				if (token != null && !token.isEmpty()) {
					logger.error("Received oauth_toke\"" + token +"\" on request token generation call.");
					throw new OAuthRejectedException("Invalid oauth header. 'oauth_token' value is set.");
				}
				
//				if (validateOAuthHeaderParameters(requestMethod, requestTokenUrl, oauthParameters)) {
					RequestTokenStoreData requestTokenData = new RequestTokenStoreData();
					long timestamp = System.currentTimeMillis();
					requestTokenData.setConsumerKey(oauthParameters.getValue(OAuthParameters.OAUTH_CONSUMER_KEY));
					requestTokenData.setToken(requestTokenGenerator.generateToken());
					String tokenSecret = null;
					if (tokenSecretLength > 1) {
						tokenSecret = tokenSecretGenerator.generateToken(new byte[tokenSecretLength]);
					} else {
						tokenSecret = tokenSecretGenerator.generateToken();
					}
					requestTokenData.setTokenSecret(tokenSecret);
					requestTokenData.setCallbackUrl(oauthParameters.getValue(OAuthParameters.OAUTH_CALLBACK));
					requestTokenData.setMaximumValidity(requestTokenValidity);
					requestTokenData.setCreationTime(timestamp);
					
					//Save
					requestTokenStore.save(requestTokenData);
					
					//No exception thrown, let's create a token.
					requestToken = new RequestToken(requestTokenData.getToken(), requestTokenData.getTokenSecret(), true);
//				}
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			throw new OAuthException("NPE: " + e.getLocalizedMessage(), e);
		} catch (StoreException e) {
			// TODO Auto-generated catch block
			throw new OAuthException("Store Exception: " + e.getLocalizedMessage(), e);
		}
		
		return requestToken;
	}
	
	public AuthorizedToken authorizeRequestToken(String requestToken) throws OAuthException {
		AuthorizedToken authorizedToken = null;
		
		try {
			RequestTokenStoreData requestTokenData = requestTokenStore.find(requestToken);
			ExceptionUtil.throwIfNull(requestTokenData, "No request token found.");
			
			if (requestTokenData.isAuthorized()) {
				logger.error("Request token '" +  requestTokenData.getToken() + "' is already authorized.");
				throw new UsedTokenException();
			}
			
			String verifier = authVerifierGenerator.generateToken();
			long timestamp = System.currentTimeMillis();
			requestTokenData.setAuthorized(true);
			requestTokenData.setVerifier(verifier);
			//update timestamp
			requestTokenData.setCreationTime(timestamp);
			
			requestTokenStore.delete(requestToken);
			requestTokenStore.save(requestTokenData);
			
			//All successful, let's create an authorized token
			authorizedToken = new AuthorizedToken(requestToken, verifier);
		} catch (StoreException e) {
			// TODO Auto-generated catch block
			throw new OAuthException("Store Exception: " + e.getLocalizedMessage(), e);
		}
		
		return authorizedToken;		
	}
	
	public AccessToken createAccessToken(String requestMethod, String accessTokenUrl, OAuthParameters oauthParameters) throws OAuthException {
		AccessToken accessToken = null;
		String authorizedToken = oauthParameters.getValue(OAuthParameters.OAUTH_TOKEN);
		String verifier = oauthParameters.getValue(OAuthParameters.OAUTH_VERIFIER);
		try {
			ExceptionUtil.throwIfNull(oauthParameters, NO_OAUTH_PARAMETERS);
			ExceptionUtil.throwIfNull(authorizedToken, OAuthParameters.OAUTH_TOKEN + " is required.");
			ExceptionUtil.throwIfNull(verifier, OAuthParameters.OAUTH_VERIFIER + " is required.");
			
//			if (validateOAuthHeaderParameters(requestMethod, accessTokenUrl, oauthParameters)) {
				
				RequestTokenStoreData requestTokenStoreData = requestTokenStore.find(authorizedToken);
				if (requestTokenStoreData == null) {
					logger.error("Couldn't find authorized token '" + authorizedToken + "'.");
					throw new OAuthRejectedException("Cannot validate token.");
				}
				
				if (!requestTokenStoreData.getConsumerKey().equals(oauthParameters.getValue(OAuthParameters.OAUTH_CONSUMER_KEY))) {
					throw new OAuthRejectedException("Consumer key invalid.");
				}
				
				if (!requestTokenStoreData.isAuthorized()) {
					logger.error("Request token '" + authorizedToken + "' is not authorized yet.");
					throw new OAuthRejectedException("Token unauthorized.");
				}
				
				if (!requestTokenStoreData.getVerifier().equals(verifier)) {
					logger.error("stored oauth_verifier=\"" + requestTokenStoreData.getVerifier() + "\" is not equal to header oauth_verifier=\"" + verifier + "\".");
					throw new OAuthRejectedException("Invalid verification.");
				}
				
				AccessTokenStoreData accessTokenStoreData = new AccessTokenStoreData();
				long timestamp = System.currentTimeMillis();
				accessTokenStoreData.setConsumerKey(oauthParameters.getValue(OAuthParameters.OAUTH_CONSUMER_KEY));
				accessTokenStoreData.setCreationTime(timestamp);
				accessTokenStoreData.setMaximumValidity(getAccessTokenValidity());
				accessTokenStoreData.setToken(getAccessTokenGenerator().generateToken());
				String tokenSecret = null;
				if (tokenSecretLength > 1) {
					tokenSecret = tokenSecretGenerator.generateToken(new byte[tokenSecretLength]);
				} else {
					tokenSecret = tokenSecretGenerator.generateToken();
				}
				accessTokenStoreData.setTokenSecret(tokenSecret);
				
				//save
				getAccessTokenStore().save(accessTokenStoreData);
				
				//Successful save
				accessToken = new AccessToken(accessTokenStoreData.getToken(), accessTokenStoreData.getTokenSecret(), new HashMap<String, String>());
//			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e.getLocalizedMessage(), e);
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e.getLocalizedMessage(), e);
		} catch (StoreException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e.getLocalizedMessage(), e);
		}
		
		return accessToken;
	}
	
	public boolean validateOAuthHeaderParameters(String requestMethod, String tokenUrl, OAuthParameters oauthParameters) throws OAuthException {
		// TODO Auto-generated method stub
		if (requestMethod == null || requestMethod.isEmpty()) {
			throw new OAuthException("No HTTP request method provided.");
		}
		
		if (tokenUrl == null || tokenUrl.isEmpty()) {
			throw new OAuthException("No token URL provided.");
		}
		
		String requestSignature = oauthParameters.getValue(OAuthParameters.OAUTH_SIGNATURE);
		String signatureMethod = oauthParameters.getValue(OAuthParameters.OAUTH_SIGNATURE_METHOD);
		logger.debug("oauth_signature: " + requestSignature + ", oauth_signature_method: " + signatureMethod);
		
		//Follow the spec by seeing if we are NOT PLAINTEXT and we have nonce and timestamp
		String oauthNonce = oauthParameters.getValue(OAuthParameters.OAUTH_NONCE);
		String oauthTimestamp = oauthParameters.getValue(OAuthParameters.OAUTH_TIMESTAMP);
		
		if (!OAuthSignatureMethod.SIGNATURE_METHOD_PLAINTEXT.equals(signatureMethod)) {
			if (oauthNonce == null || oauthTimestamp == null) {
				throw new OAuthException("oauth_nonce and oauth_timestamp MUST be provided for oauth_signature_method=\"" + signatureMethod + "\".");
			}
		}
		
		//Begin....	
		try {
			String oauthToken = oauthParameters.getValue(OAuthParameters.OAUTH_TOKEN);
			long now = System.currentTimeMillis();
			ConsumerKeyStoreData consumerKeyData = getConsumerKeyStore().find(oauthParameters.getValue(OAuthParameters.OAUTH_CONSUMER_KEY));
			if (consumerKeyData == null) {
				throw new OAuthAuthorizationException("Invalid " + OAuthParameters.OAUTH_CONSUMER_KEY + ".");
			}
			
			//Let's prevent Replay-Attack
			UsedNonceStoreData alreadyUsedNonceStoreData = usedNonceStore.findByTimestamp(consumerKeyData.getConsumerKey(), Long.parseLong(oauthTimestamp));
			if (alreadyUsedNonceStoreData == null) {
				alreadyUsedNonceStoreData = usedNonceStore.find(consumerKeyData.getConsumerKey(), oauthNonce);
			}
			
			if (alreadyUsedNonceStoreData != null) {
				throw new UsedNonceException();
			}
			
			//Whether the oauth parameters is valid or not, we store the nonce.
			if (oauthNonce != null && oauthTimestamp != null) {
				alreadyUsedNonceStoreData = new UsedNonceStoreData();
				alreadyUsedNonceStoreData.setConsumerKey(consumerKeyData.getConsumerKey());
				alreadyUsedNonceStoreData.setCreationTime(now);
				alreadyUsedNonceStoreData.setMaximumValidity(usedNonceValidity);
				alreadyUsedNonceStoreData.setTimestamp(Long.parseLong(oauthTimestamp));
				alreadyUsedNonceStoreData.setNonce(oauthNonce);
				if (oauthToken != null) {
					alreadyUsedNonceStoreData.setToken(oauthToken);
				}
				
				usedNonceStore.save(alreadyUsedNonceStoreData);
			}
			
			//Validate oauth_version
			String oauthVersion = oauthParameters.getValue(OAuthParameters.OAUTH_VERSION);
			if (oauthVersion != null && !OAuth1ServiceProvider.PROTOCOL_VERSION.equals(oauthVersion)) {
				throw new OAuthException("oauth_version MUST be set to \"" + OAuth1ServiceProvider.PROTOCOL_VERSION + "\".");
			}
			
			//First, get a signature method
			OAuthSignatureBuilder oauthSignatureBuilder = new OAuthSignatureBuilder();
			oauthSignatureBuilder.setConsumerSecret(consumerKeyData.getConsumerSecret());
			
			//Get oauth_token_secret if oauth_token exists in the oauth parameters.
			if (oauthToken != null) {
				RequestTokenStoreData requestTokenData = requestTokenStore.find(oauthToken);
				if (requestTokenData == null) {
					throw new OAuthException("Invalid oauth_token.");
				}
				
				oauthSignatureBuilder.setTokenSecret(requestTokenData.getTokenSecret());
			}
			
			OAuthSignature signature = oauthSignatureBuilder.build(signatureMethod);
			if (signature == null) {
				throw new OAuthException("No Signature method object found (nor implemented) for signature method '" + signatureMethod + "'.");
			}

			//Now, validate
			Map<String, String> oauthMap = oauthParameters.getOAuthParameters();
			oauthMap.remove(OAuthParameters.OAUTH_REALM);
			String baseString = OAuth1Util.getSignatureBaseString(requestMethod, tokenUrl, oauthMap);
			String derivedSignature = signature.sign(baseString);
			
			return requestSignature.equals(derivedSignature);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			throw new OAuthAuthorizationException("Security Exception: " + e.getLocalizedMessage(), e);
		} catch (StoreException e) {
			// TODO Auto-generated catch block
			throw new OAuthAuthorizationException("Store Exception: " + e.getLocalizedMessage(), e);
		}
	}
	
//	public String getOAuthCallback(String requestToken) throws OAuthException {
//		try {
//			RequestTokenStoreData rtsd = requestTokenStore.find(requestToken);
//			if (rtsd != null) {
//				if (logger.isInfoEnabled()) {
//					logger.info("Request token '" + requestToken + "' oauth_callback=\"" + rtsd.getCallbackUrl() + "\".");
//				}
//				
//				return rtsd.getCallbackUrl();
//			}
//		} catch (StoreException e) {
//			// TODO Auto-generated catch block
//			throw new OAuthAuthorizationException("Store Exception: " + e.getLocalizedMessage(), e);
//		}
//		
//		return null;
//	}
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.TokenManager#garbageCollect()
	 */
	@Override
	public void garbageCollect() {
		// TODO Auto-generated method stub
	}
}
