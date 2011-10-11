/**
 * 
 */
package com.neurologic.oauth.service.provider.manager;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth2Parameters;
import net.oauth.token.oauth1.AuthorizedToken;
import net.oauth.token.oauth2.AccessToken;
import net.oauth.token.oauth2.AuthorizationToken;
import net.oauth.util.OAuth2Util;

import com.neurologic.exception.OAuthRejectedException;
import com.neurologic.exception.StoreException;
import com.neurologic.oauth.service.provider.generator.TokenStringGenerator;
import com.neurologic.oauth.service.provider.manager.store.DataStore;
import com.neurologic.oauth.service.provider.manager.store.data.ConsumerKeyStoreData;
import com.neurologic.oauth.service.provider.manager.store.data.oauth2.AccessTokenStoreData;
import com.neurologic.oauth.service.provider.manager.store.data.oauth2.AuthorizationTokenStoreData;

/**
 * @author Buhake Sindi
 * @since 15 September 2011
 *
 */
public class OAuth2TokenManager extends AbstractOAuthTokenManager {

	private static final long TEN_MINUTES = 10 * MINUTE;
	private TokenStringGenerator authorizationTokenGenerator;
	private TokenStringGenerator refreshTokenGenerator;
	private DataStore<AuthorizationTokenStoreData> authorizationTokenStore;
	private DataStore<AccessTokenStoreData> accessTokenStore;
	
	/**
	 * According to OAuth 2 Draft 20, authorization codes must be kept in a maximum of 10 minutes.
	 */
	public OAuth2TokenManager() {
		super();
		// TODO Auto-generated constructor stub
		setAuthorizedTokenValidity(TEN_MINUTES); //10 minutes
	}

	/**
	 * @return the authorizationTokenGenerator
	 */
	public TokenStringGenerator getAuthorizationTokenGenerator() {
		return authorizationTokenGenerator;
	}

	/**
	 * @param authorizationTokenGenerator the authorizationTokenGenerator to set
	 */
	public void setAuthorizationTokenGenerator(TokenStringGenerator authorizationTokenGenerator) {
		this.authorizationTokenGenerator = authorizationTokenGenerator;
	}

	/**
	 * @return the refreshTokenGenerator
	 */
	public TokenStringGenerator getRefreshTokenGenerator() {
		return refreshTokenGenerator;
	}

	/**
	 * @param refreshTokenGenerator the refreshTokenGenerator to set
	 */
	public void setRefreshTokenGenerator(TokenStringGenerator refreshTokenGenerator) {
		this.refreshTokenGenerator = refreshTokenGenerator;
	}

	/**
	 * @return the authorizationTokenStore
	 */
	public DataStore<AuthorizationTokenStoreData> getAuthorizationTokenStore() {
		return authorizationTokenStore;
	}

	/**
	 * @param authorizationTokenStore the authorizationTokenStore to set
	 */
	public void setAuthorizationTokenStore(
			DataStore<AuthorizationTokenStoreData> authorizationTokenStore) {
		this.authorizationTokenStore = authorizationTokenStore;
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

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.AbstractOAuthTokenManager#setAuthorizedTokenValidity(long)
	 */
	@Override
	public void setAuthorizedTokenValidity(long authorizedTokenValidity) {
		// TODO Auto-generated method stub
		if (authorizedTokenValidity <= TEN_MINUTES) {
			super.setAuthorizedTokenValidity(authorizedTokenValidity);
		}
	}
	
	public boolean validateCredentials(String consumerKey, String consumerSecret) throws OAuthException {
		ConsumerKeyStoreData consumerKeyData = null;
		try {
			consumerKeyData = getConsumerKeyStore().find(consumerKey);
		} catch (StoreException e) {
			// TODO Auto-generated catch block
			logger.error("StoreException: " + e.getLocalizedMessage(), e);
		}
		
		return (consumerKeyData != null && consumerKeyData.getConsumerSecret().equals(consumerSecret));
	}
	
	public AuthorizationToken createAuthorizationToken(String clientId, String redirectUri, String scope, String state) throws OAuthException {
		AuthorizationToken authorizationToken = null;
		
		try {
			if (getConsumerKeyStore().find(clientId) == null) {
				throw new OAuthException("No consumer key found.");
			}
			
			if (redirectUri != null && !OAuth2Util.isRedirectEndpointUriValid(redirectUri)) {
				throw new OAuthException("OAuth 2 '" + OAuth2Parameters.REDIRECT_URI + "' is not a fully qualified URI (see Section 3.1.2).");
			}
			
			AuthorizationTokenStoreData authTokenStoreData = new AuthorizationTokenStoreData();
			long now = System.currentTimeMillis();
			authTokenStoreData.setCode(authorizationTokenGenerator.generateToken());
			authTokenStoreData.setConsumerKey(clientId);
			authTokenStoreData.setCreationTime(now);
			authTokenStoreData.setMaximumValidity(getAuthorizedTokenValidity());
			authTokenStoreData.setRedirectUri(redirectUri);
			authTokenStoreData.setScope(scope);
			authTokenStoreData.setState(state);
			
			//save
			authorizationTokenStore.save(authTokenStoreData);
			
			//finally
			authorizationToken = new AuthorizationToken();
			authorizationToken.setCode(authTokenStoreData.getCode());
			authorizationToken.setExpiresIn((int)(now / MILLISECOND));
			authorizationToken.setState(state);
		} catch (StoreException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e.getLocalizedMessage(), e);
		}
		
		return authorizationToken;
	}
	
	public AccessToken createAccessToken(String clientId, String code, String redirectUri, String scope, String state) throws OAuthException {
		AccessToken accessToken = null;
		
		try {
			if (redirectUri != null && !OAuth2Util.isRedirectEndpointUriValid(redirectUri)) {
				throw new OAuthException("OAuth 2 '" + OAuth2Parameters.REDIRECT_URI + "' is not a fully qualified URI (see Section 3.1.2).");
			}
			
			AuthorizationTokenStoreData authorizationTokenSD = authorizationTokenStore.find(code);
			if (authorizationTokenSD == null) {
				throw new OAuthRejectedException("Authorization code not found.");
			}
			
			if (!authorizationTokenSD.getConsumerKey().equals(clientId)) {
				throw new OAuthRejectedException("Invalid '" + OAuth2Parameters.CLIENT_ID + "'.");
			}
			
			if (!authorizationTokenSD.getRedirectUri().equals(redirectUri)) {
				throw new OAuthRejectedException("'" + OAuth2Parameters.REDIRECT_URI + "' mismatch.");
			}
			
			AccessTokenStoreData accessTokenSD = new AccessTokenStoreData();
			long now = System.currentTimeMillis();
			accessTokenSD.setConsumerKey(clientId);
			accessTokenSD.setCreationTime(now);
			accessTokenSD.setMaximumValidity(getAccessTokenValidity());
			accessTokenSD.setRefreshToken(refreshTokenGenerator.generateToken());
			accessTokenSD.setToken(getAccessTokenGenerator().generateToken());
			
			//Save
			accessTokenStore.save(accessTokenSD);
			
			//Finally
			accessToken = new AccessToken();
			accessToken.setAccessToken(accessTokenSD.getToken());
			accessToken.setExpiresIn((int)(accessTokenSD.getMaximumValidity() / MILLISECOND));
			accessToken.setRefreshToken(accessTokenSD.getRefreshToken());
			accessToken.setScope(scope);
			accessToken.setState(state);
		} catch (StoreException e) {
			// TODO Auto-generated catch block
			throw new OAuthException(e.getLocalizedMessage(), e);
		}
		
		return accessToken;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.OAuthTokenManager#garbageCollect()
	 */
	@Override
	public void garbageCollect() {
		// TODO Auto-generated method stub

	}

}
