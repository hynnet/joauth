/**
 * 
 */
package com.neurologic.oauth.service.provider.manager;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth1Parameters;
import net.oauth.provider.OAuth1ServiceProvider;
import net.oauth.signature.OAuthSignature;
import net.oauth.signature.OAuthSignatureMethod;
import net.oauth.token.oauth1.AccessToken;
import net.oauth.token.oauth1.AuthorizedToken;
import net.oauth.token.oauth1.RequestToken;
import net.oauth.util.OAuth1Util;

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
	
	private final Object lock = new Object();
	private TokenStringGenerator requestTokenGenerator;
	private TokenStringGenerator authVerifierGenerator;
	private TokenStringGenerator tokenSecretGenerator;
	private DataStore<RequestTokenStoreData> requestTokenStore;
	private AbstractUsedNonceDataStore usedNonceStore;
	private DataStore<AccessTokenStoreData> accessTokenStore;
	private long requestTokenValidity = 30 * MINUTE;	//30 minutes;
	private long usedNonceValidity = 15 * MINUTE;	//15 minutes
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
	 * Creates a {@link RequestToken} based on the consumer key and oauth callback.
	 * <br />We assumed that the oauth parameters verification has been done.
	 * 
	 * @param consumerKey
	 * @param oauthCallback
	 * @return
	 * @throws OAuthException
	 */
	public RequestToken createRequestToken(String consumerKey, String oauthCallback) throws OAuthException {
		RequestToken requestToken = null;
		
		try {
			synchronized (lock) {
				ExceptionUtil.throwIfNull(consumerKey, OAuth1Parameters.OAUTH_CONSUMER_KEY + " is required");
				ExceptionUtil.throwIfNull(oauthCallback, OAuth1Parameters.OAUTH_CALLBACK + " is required.");
				
				RequestTokenStoreData requestTokenData = new RequestTokenStoreData();
				long timestamp = System.currentTimeMillis();
				requestTokenData.setConsumerKey(consumerKey);
				requestTokenData.setToken(requestTokenGenerator.generateToken());
				String tokenSecret = null;
				if (tokenSecretLength > 1) {
					tokenSecret = tokenSecretGenerator.generateToken(new byte[tokenSecretLength]);
				} else {
					tokenSecret = tokenSecretGenerator.generateToken();
				}
				requestTokenData.setTokenSecret(tokenSecret);
				requestTokenData.setCallbackUrl(oauthCallback);
				requestTokenData.setMaximumValidity(requestTokenValidity);
				requestTokenData.setCreationTime(timestamp);
				
				//Save
				requestTokenStore.save(requestTokenData);
				
				//No exception thrown, let's create a token.
				requestToken = new RequestToken(requestTokenData.getToken(), requestTokenData.getTokenSecret(), true);
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
	
	/**
	 * Authorizes a request token (if it exists and is unauthorized).
	 * 
	 * @param requestToken
	 * @param userId
	 * @return
	 * @throws OAuthException
	 */
	public AuthorizedToken authorizeRequestToken(String requestToken, String userId) throws OAuthException {
		AuthorizedToken authorizedToken = null;
		
		try {
			synchronized (lock) {
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
				requestTokenData.setUserId(userId);
				//update timestamp
				requestTokenData.setCreationTime(timestamp);
				
				requestTokenStore.delete(requestToken);
				requestTokenStore.save(requestTokenData);
				
				//All successful, let's create an authorized token
				authorizedToken = new AuthorizedToken(requestToken, verifier);
			}
		} catch (StoreException e) {
			// TODO Auto-generated catch block
			throw new OAuthException("Store Exception: " + e.getLocalizedMessage(), e);
		}
		
		return authorizedToken;		
	}
	
	/**
	 * Create an access token.
	 * @param authorizedToken
	 * @param verifier
	 * @param consumerKey
	 * @return
	 * @throws OAuthException
	 */
	public AccessToken createAccessToken(String authorizedToken, String verifier, String consumerKey) throws OAuthException {
		AccessToken accessToken = null;
		
		try {
			synchronized (lock) {
				ExceptionUtil.throwIfNull(consumerKey, OAuth1Parameters.OAUTH_CONSUMER_KEY + " is required.");
				ExceptionUtil.throwIfNull(authorizedToken, OAuth1Parameters.OAUTH_TOKEN + " is required.");
				ExceptionUtil.throwIfNull(verifier, OAuth1Parameters.OAUTH_VERIFIER + " is required.");
				
				RequestTokenStoreData requestTokenStoreData = requestTokenStore.find(authorizedToken);
				if (requestTokenStoreData == null) {
					logger.error("Couldn't find authorized token '" + authorizedToken + "'.");
					throw new OAuthRejectedException("Cannot validate token.");
				}
				
				if (!requestTokenStoreData.getConsumerKey().equals(consumerKey)) {
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
				accessTokenStoreData.setUserId(requestTokenStoreData.getUserId());
				accessTokenStoreData.setConsumerKey(consumerKey);
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
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e.getLocalizedMessage(), e);
		} catch (StoreException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e.getLocalizedMessage(), e);
		}
		
		return accessToken;
	}
	
	/**
	 * Validates the OAuth parameters received from the HTTP Authorization header.
	 * 
	 * @param requestMethod the HTTP method ("GET"/"POST", etc.)
	 * @param tokenUrl
	 * @param oauthParameters
	 * @return
	 * @throws OAuthException
	 */
	public boolean validateOAuthHeaderParameters(String requestMethod, String tokenUrl, OAuth1Parameters oauthParameters) throws OAuthException {
		// TODO Auto-generated method stub
		if (requestMethod == null || requestMethod.isEmpty()) {
			throw new OAuthException("No HTTP request method provided.");
		}
		
		if (tokenUrl == null || tokenUrl.isEmpty()) {
			throw new OAuthException("No token URL provided.");
		}
		
		String requestSignature = oauthParameters.getOAuthSignature();
		String signatureMethod = oauthParameters.getOAuthSignatureMethod();
		logger.debug("oauth_signature: " + requestSignature + ", oauth_signature_method: " + signatureMethod);
		
		//Follow the spec by seeing if we are NOT PLAINTEXT and we have nonce and timestamp
		String oauthNonce = oauthParameters.getOAuthNonce();
		String oauthTimestamp = oauthParameters.getOAuthTimestamp();
		
		if (!OAuthSignatureMethod.SIGNATURE_METHOD_PLAINTEXT.equals(signatureMethod)) {
			if (oauthNonce == null || oauthTimestamp == null) {
				throw new OAuthException("oauth_nonce and oauth_timestamp MUST be provided for oauth_signature_method=\"" + signatureMethod + "\".");
			}
		}
		
		//Validate oauth_version
		String oauthVersion = oauthParameters.getOAuthVersion();
		if (oauthVersion != null && !OAuth1ServiceProvider.PROTOCOL_VERSION.equals(oauthVersion)) {
			throw new OAuthException("oauth_version MUST be set to \"" + OAuth1ServiceProvider.PROTOCOL_VERSION + "\".");
		}
		
		//Begin....	
		try {
			String oauthToken = oauthParameters.getOAuthToken();
			long now = System.currentTimeMillis();
			ConsumerKeyStoreData consumerKeyData = getConsumerKeyStore().find(oauthParameters.getOAuthConsumerKey());
			if (consumerKeyData == null) {
				throw new OAuthAuthorizationException("Invalid " + OAuth1Parameters.OAUTH_CONSUMER_KEY + ".");
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
			oauthMap.remove(OAuth1Parameters.OAUTH_REALM);
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
	
	/**
	 * Verify if the access token provided is indeed related to the one we have in the store.
	 * @param consumerKey
	 * @param accessToken
	 * @return <b>true</b> if valid, <b>false</b> otherwise.
	 * @throws OAuthException
	 */
	public boolean canAccessProtectedResources(String consumerKey, String accessToken) throws OAuthException {
		
		try {
			ExceptionUtil.throwIfNull(consumerKey, OAuth1Parameters.OAUTH_CONSUMER_KEY + " is required.");
			ExceptionUtil.throwIfNull(accessToken, OAuth1Parameters.OAUTH_TOKEN + " is required.");
			
			AccessTokenStoreData accessTokenSD = getAccessTokenStore().find(accessToken);
			if (accessTokenSD == null) {
				throw new OAuthRejectedException("No access Token found.");
			}
			
			if (!accessTokenSD.getConsumerKey().equals(consumerKey)) {
				throw new OAuthRejectedException("Invalid access token for Consumer Key.");
			}
		} catch (StoreException e) {
			// TODO Auto-generated catch block
			throw new OAuthAuthorizationException("Store Exception: " + e.getLocalizedMessage(), e);
		}
		
		return true;
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
//			throw new OAuthAuthorizationException("StoreException: " + e.getLocalizedMessage(), e);
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
