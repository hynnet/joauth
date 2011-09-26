/**
 * 
 */
package net.oauth.authorization;

import java.security.GeneralSecurityException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.oauth.authorization.builder.OAuthAuthorizationBuilder;
import net.oauth.parameters.HeaderKeyValuePair;
import net.oauth.parameters.OAuthParameters;
import net.oauth.provider.OAuth1ServiceProvider;
import net.oauth.signature.ConsumerSecretBasedOAuthSignature;
import net.oauth.signature.OAuthSignature;
import net.oauth.token.oauth1.AccessToken;
import net.oauth.util.OAuth1Util;

import com.neurologic.exception.OAuthAuthorizationException;

/**
 * @author Buhake Sindi
 * @since 21 September 2011
 *
 */
public class OAuth1ProtectedResourceAuthorization implements OAuthProtectedResourceAuthorization {

	private Builder builder;
	
	/**
	 * 
	 */
	private OAuth1ProtectedResourceAuthorization(Builder builder) {
		super();
		// TODO Auto-generated constructor stub
		this.builder = builder;
	}

	/* (non-Javadoc)
	 * @see net.oauth.authorization.OAuthAuthorization#generateAuthorizationString()
	 */
	@Override
	public String generateAuthorizationString() throws OAuthAuthorizationException {
		// TODO Auto-generated method stub
		if (builder.signature instanceof ConsumerSecretBasedOAuthSignature) {
			((ConsumerSecretBasedOAuthSignature)builder.signature).setConsumerSecret(builder.consumerSecret);
			((ConsumerSecretBasedOAuthSignature)builder.signature).setTokenSecret(builder.accessToken.getTokenSecret());
		}
		
		try {
			long now = OAuth1Util.getTimestamp();
			OAuthParameters parameters = new OAuthParameters();
			parameters.setOAuthConsumerKey(builder.consumerKey);
			parameters.setOAuthToken(builder.accessToken.getToken());
			parameters.setOAuthSignatureMethod(builder.signature.getOAuthSignatureMethod());
			parameters.setOAuthTimestamp(Long.toString(now));
			parameters.setOAuthNonce(OAuth1Util.getNONCE());
			parameters.setOAuthVersion(OAuth1ServiceProvider.PROTOCOL_VERSION);
			
			int questionMarkPos = builder.protectedResourceUrl.indexOf('?');
			Map<String, String> parameterMap = new LinkedHashMap<String, String>(parameters.getOAuthParameters());
			parameterMap.putAll(OAuth1Util.parseQueryString(builder.protectedResourceUrl.substring(questionMarkPos + 1)));
			
			
			String baseString = OAuth1Util.getSignatureBaseString(builder.requestMethod, builder.protectedResourceUrl.substring(0, questionMarkPos), parameterMap);
			parameters.setOAuthSignature(builder.signature.sign(baseString));
			
			//Add realm (if provided)
			if (builder.realm != null && !builder.realm.isEmpty()) {
				parameters.setOAuthRealm(builder.realm);
			}
			
			return OAuth1Util.getQueryString(parameterMap, new HeaderKeyValuePair());
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			throw new OAuthAuthorizationException(e);
		}
	}
	
	public static class Builder implements OAuthAuthorizationBuilder<OAuth1ProtectedResourceAuthorization> {

		private String realm;
		private boolean verifyRealm;
		private String consumerKey;
		private String consumerSecret;
		private String requestMethod;
		private String protectedResourceUrl;
		private OAuthSignature signature;
		private AccessToken accessToken;
		
		/**
		 * 
		 */
		public Builder() {
			this(null);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param realm
		 */
		public Builder(String realm) {
			this(realm, false);
		}

		/**
		 * @param realm
		 */
		public Builder(String realm, boolean verifyRealm) {
			super();
			this.realm = realm;
			this.verifyRealm = verifyRealm;
		}

		/**
		 * @param realm the realm to set
		 */
		public Builder setRealm(String realm) {
			this.realm = realm;
			return this;
		}

		/**
		 * @param consumerKey the consumerKey to set
		 */
		public Builder setConsumerKey(String consumerKey) {
			this.consumerKey = consumerKey;
			return this;
		}

		/**
		 * @param consumerSecret the consumerSecret to set
		 */
		public Builder setConsumerSecret(String consumerSecret) {
			this.consumerSecret = consumerSecret;
			return this;
		}

		/**
		 * @param requestMethod the requestMethod to set
		 */
		public Builder setRequestMethod(String requestMethod) {
			this.requestMethod = requestMethod;
			return this;
		}

		/**
		 * @param protectedResourceUrl the protectedResourceUrl to set
		 */
		public Builder setProtectedResourceUrl(String protectedResourceUrl) {
			this.protectedResourceUrl = protectedResourceUrl;
			return this;
		}

		/**
		 * @param signature the signature to set
		 */
		public Builder setSignature(OAuthSignature signature) {
			this.signature = signature;
			return this;
		}

		/**
		 * @param accessToken the accessToken to set
		 */
		public Builder setAccessToken(AccessToken accessToken) {
			this.accessToken = accessToken;
			return this;
		}

		/* (non-Javadoc)
		 * @see net.oauth.authorization.builder.OAuthAuthorizationBuilder#build()
		 */
		@Override
		public OAuth1ProtectedResourceAuthorization build() throws OAuthAuthorizationException {
			verifyParameter(consumerKey, "Consumer key not provided.");
			verifyParameter(consumerSecret, "Consumer secret not provided.");
			verifyParameter(requestMethod, "HTTP method not provided.");
			verifyParameter(signature, "OAuth Signature not provided.");
			verifyParameter(protectedResourceUrl, "Protected resource URL not provided.");
			verifyParameter(accessToken, "Access token not provided.");
			verifyParameter(accessToken.getToken(), "Access token value is empty.");
			
			if (verifyRealm) {
				verifyParameter(realm, "Realm not provided.");
			}
			
			// TODO Auto-generated method stub
			return new OAuth1ProtectedResourceAuthorization(this);
		}
		
		private void verifyParameter(Object parameter, String exceptionMessage) throws OAuthAuthorizationException {
			if (parameter != null) {
				if (parameter instanceof String && !((String)parameter).isEmpty()) {
					return ;
				}
			} else {
				throw new OAuthAuthorizationException(exceptionMessage);
			}
		}
	}
}
