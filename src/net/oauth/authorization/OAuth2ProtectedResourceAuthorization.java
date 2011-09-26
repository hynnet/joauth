/**
 * 
 */
package net.oauth.authorization;

import net.oauth.token.oauth2.AccessToken;
import net.oauth.token.oauth2.type.TokenType;

import com.neurologic.exception.OAuthAuthorizationException;

/**
 * @author Buhake Sindi
 * @since 22 September 2011
 *
 */
public class OAuth2ProtectedResourceAuthorization implements OAuthProtectedResourceAuthorization {

	private AccessToken accessToken;
	private TokenType tokenType;
	
	/**
	 * This constructor is allowed for those who still uses OAuth 2 Draft 10
	 * @param accessToken
	 */
	public OAuth2ProtectedResourceAuthorization(AccessToken accessToken) {
		this(accessToken, null);
	}

	/**
	 * @param accessToken
	 * @param tokenType
	 */
	public OAuth2ProtectedResourceAuthorization(AccessToken accessToken, TokenType tokenType) {
		super();
		this.accessToken = accessToken;
		this.tokenType = tokenType;
	}

	/* (non-Javadoc)
	 * @see net.oauth.authorization.OAuthProtectedResourceAuthorization#generateAuthorizationString()
	 */
	@Override
	public String generateAuthorizationString() throws OAuthAuthorizationException {
		// TODO Auto-generated method stub
		if (accessToken == null || accessToken.getAccessToken() == null || accessToken.getAccessToken().isEmpty()) {
			throw new OAuthAuthorizationException("No access token provided.");
		}
		
		//For OAuth 2 Draft 10
		if (tokenType == null) {
			return "OAuth " + accessToken.getAccessToken();
		}
		
		return tokenType.generateValue(accessToken);
	}
}
