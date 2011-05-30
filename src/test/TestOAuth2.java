/**
 * 
 */
package test;

import net.oauth.consumer.OAuth2Consumer;
import net.oauth.enums.GrantType;
import net.oauth.enums.ResponseType;
import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth2Parameters;
import net.oauth.provider.OAuth2ServiceProvider;
import net.oauth.token.v2.AccessToken;

/**
 * @author Buhake.Sindi
 *
 */
public class TestOAuth2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String client_id = "ENTER ID HERE";
        String client_secret = "ENTER CLIENT SECRET HERE";
        String redirectUri = "http://localhost:8080/my_app/oauth_redirect";
        OAuth2ServiceProvider provider = new OAuth2ServiceProvider("https://graph.facebook.com/oauth/authorize", "https://graph.facebook.com/oauth/access_token");
        OAuth2Consumer consumer = new OAuth2Consumer(client_id, client_secret, provider);
        
        //begin
        try {
                System.out.println(consumer.generateRequestAuthorizationUrl(ResponseType.CODE, redirectUri, null, (String[])null));
                
                OAuth2Parameters parameters = new OAuth2Parameters();
                parameters.setCode("3f61aa47b915215a938d2722-682316653|5OPOkmKew_W8vybb9sccIPoivAg.");
                parameters.setRedirectUri(redirectUri);
                
                AccessToken accessToken = consumer.requestAcessToken(GrantType.AUTHORIZATION_CODE, parameters, (String[])null);
                System.out.println(accessToken.getAccessToken());
        } catch (OAuthException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
	}
}
