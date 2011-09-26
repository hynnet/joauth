/**
 * 
 */
package net.oauth.token.oauth2.type;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Date;

import net.oauth.parameters.HeaderKeyValuePair;
import net.oauth.parameters.KeyValuePair;
import net.oauth.token.oauth2.AccessToken;
import net.oauth.token.oauth2.type.util.MacTokenTypeUtil;
import net.oauth.util.OAuth1Util;
import net.oauth.util.OAuthSignatureUtil;

import com.neurologic.exception.OAuthAuthorizationException;

/**
 * @author Buhake Sindi
 * @since 21 September 2011
 *
 */
public class MacTokenType implements TokenType {
	
	private static final String MAC_START_TOKEN = "MAC ";
	private Builder builder;

	/**
	 * @param builder
	 */
	private MacTokenType(Builder builder) {
		super();
		this.builder = builder;
	}

	/* (non-Javadoc)
	 * @see net.oauth.token.oauth2.type.TokenType#getType()
	 */
	@Override
	public net.oauth.enums.TokenType getType() {
		// TODO Auto-generated method stub
		return net.oauth.enums.TokenType.MAC;
	}

	/* (non-Javadoc)
	 * @see net.oauth.token.oauth2.type.TokenType#generateValue(net.oauth.token.oauth2.AccessToken)
	 */
	@Override
	public String generateValue(AccessToken accessToken) throws OAuthAuthorizationException {
		// TODO Auto-generated method stub
		if (builder.macKey == null || builder.macKey.isEmpty()) {
			throw new OAuthAuthorizationException("MAC key is not provided.");
		}
		
		if (builder.macAlgorithm == null) {
			throw new OAuthAuthorizationException("MAC algorithm is not provided.");
		}
		
		if (builder.issueTime == null) {
			throw new OAuthAuthorizationException("The MAC issue time is not provided.");
		}
		
		String accessTokenStr = (accessToken == null ? "" : accessToken.getAccessToken());
		if (accessTokenStr == null || accessTokenStr.isEmpty()) {
			throw new OAuthAuthorizationException("MAC key identifier (access token) is not provided.");
		}
		
		if (accessTokenStr.equals(builder.macKey)) {
			throw new OAuthAuthorizationException("MAC key and MAC identifier are equal.");
		}
		
		try {
			//Let's begin
			long age = (System.currentTimeMillis() - builder.issueTime.getTime()) / 1000L;
			String randomString = OAuth1Util.getNONCE();
			String nonce = age + ":" + randomString;
			String baseString = MacTokenTypeUtil.normalizeRequestString(nonce, builder.httpMethod, builder.requestUri, builder.hostName, builder.port, builder.bodyHash, builder.ext);
			
			KeyValuePair kvp = new HeaderKeyValuePair();
			kvp.add("id", accessToken.getAccessToken());
			kvp.add("nonce", nonce);
			if (builder.bodyHash != null) {
				kvp.add("bodyhash", builder.bodyHash);
			}
			
			String mac = null;
			if (builder.macAlgorithm == Algorithm.HMAC_SHA1) {
				mac = OAuthSignatureUtil.generateHmacSHA1Signature(baseString, builder.macKey);
			} else if (builder.macAlgorithm == Algorithm.HMAC_SHA256) {
				mac = OAuthSignatureUtil.generateHmacSHA256Signature(baseString, builder.macKey);
			}
			
			//If done correctly, there should have been an expection thrown for an invalid MAC Algorithm
			//But since we don't trust humans, we have to conditionally check ourselves.
			if (mac == null || mac.isEmpty()) {
				throw new OAuthAuthorizationException("Strange! No MAC value generated.");
			}
			
			kvp.add("mac", mac);
			return MAC_START_TOKEN + kvp.toString();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			throw new OAuthAuthorizationException(e);
		}
	}
	
	public static enum Algorithm {
		HMAC_SHA1("hmac-sha-1")
		,HMAC_SHA256("hmac-sha-256")
		;
		private String algorithm;

		/**
		 * @param algorithm
		 */
		private Algorithm(String algorithm) {
			this.algorithm = algorithm;
		}
		
		public static Algorithm of(String algorithm) {
			if (HMAC_SHA1.algorithm.equals(algorithm)) {
				return HMAC_SHA1;
			}
			
			if (HMAC_SHA256.algorithm.equals(algorithm)) {
				return HMAC_SHA256;
			}
			
			return null;
		}

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return algorithm;
		}
	}
	
	public static class Builder {
		private String macKey;
		private Algorithm macAlgorithm;
		private Date issueTime;
		
		//Other required parameters
		private String httpMethod;
		private String requestUri;
		private String hostName;
		private int port;
		private String bodyHash;
		private String ext;
		
		/**
		 * @param macKey
		 * @param macAlgorithm
		 * @param issueTime
		 */
		public Builder(String macKey, Algorithm macAlgorithm, Date issueTime) {
			super();
			this.macKey = macKey;
			this.macAlgorithm = macAlgorithm;
			this.issueTime = issueTime;
		}

		/**
		 * @param httpMethod the httpMethod to set
		 */
		public Builder setHttpMethod(String httpMethod) {
			this.httpMethod = httpMethod;
			return this;
		}

		/**
		 * @param ext the ext to set
		 */
		public Builder setExt(String ext) {
			this.ext = ext;
			return this;
		}

//		/**
//		 * @param requestUri the requestUri to set
//		 */
//		public Builder setRequestUri(URI requestUri) {
//			this.requestUri = requestUri;
//			return this;
//		}
		
		public Builder setProtectedResourceUrl(String protectedResourceUrl) throws URISyntaxException {
			URI uri = new URI(protectedResourceUrl);
			if (uri != null) {
				this.hostName = uri.getAuthority();
				this.requestUri = uri.getPath();
				this.port = uri.getPort();
				if (this.port == -1) {
					//The URI couldn't find a port, so, we'll use our magic....
					this.port = MacTokenTypeUtil.defaultPort(uri.getScheme());
				}
			}
			
			return this;
		}
		
		/**
		 * This method calculates a body hash based on the HTTP entity body provided.
		 * <br />It is, therefore, imperative that the <code>MAC-Algorithm</code> is set.
		 * 
		 * @param httpEntityBody The HTTP Entity Body. 
		 * @return
		 * @throws GeneralSecurityException
		 */
		public Builder bodyHash(byte[] httpEntityBody) throws GeneralSecurityException {
			if (httpEntityBody == null) {
				//There is no entity body at all, why hash?
				bodyHash = null;
			} else {
				//Bear in mind that an empty string doesn't return a empty hash.
				bodyHash = MacTokenTypeUtil.bodyHash(httpEntityBody, macAlgorithm);
			}
			
			return this;
		}

		public MacTokenType build() throws OAuthAuthorizationException {
			if (macKey == null || macKey.isEmpty()) {
				throw new OAuthAuthorizationException("MAC Key not provided.");
			}
			
			if (macAlgorithm == null) {
				throw new OAuthAuthorizationException("No MAC Algorithm provided.");
			}
			
			if (issueTime == null) {
				throw new OAuthAuthorizationException("No MAC issue time provided.");
			}
			
			if (httpMethod == null || httpMethod.isEmpty()) {
				throw new OAuthAuthorizationException("No HTTP Method provided.");
			}
			
			if (requestUri == null) {
				throw new OAuthAuthorizationException("No request URI provided.");
			}
			
			return new MacTokenType(this);
		}
	}
}
