/**
 * 
 */
package net.oauth.token.oauth2.type;

import net.oauth.exception.OAuthAuthorizationException;
import net.oauth.token.oauth2.AccessToken;


/**
 * @author Buhake Sindi
 * @since 21 September 2011
 *
 */
public class BearerTokenType implements TokenType {

	private static final String BEARER_START_TOKEN = "Bearer ";
	
	/* (non-Javadoc)
	 * @see net.oauth.token.oauth2.type.TokenType#getType()
	 */
	@Override
	public net.oauth.enums.TokenType getType() {
		// TODO Auto-generated method stub
		return net.oauth.enums.TokenType.BEARER;
	}

	/* (non-Javadoc)
	 * @see net.oauth.token.oauth2.type.TokenType#generateValue(net.oauth.token.oauth2.AccessToken)
	 */
	@Override
	public String generateValue(AccessToken accessToken) throws OAuthAuthorizationException {
		// TODO Auto-generated method stub
		if (accessToken == null || accessToken.getAccessToken() == null || accessToken.getAccessToken().isEmpty()) {
			throw new OAuthAuthorizationException("Access token is not provided.");
		}
		
		return BEARER_START_TOKEN + accessToken.getAccessToken();
	}
}
