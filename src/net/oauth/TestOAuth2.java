/**
 * 
 */
package net.oauth;

import java.util.Map;

import net.oauth.consumer.OAuth2Consumer;
import net.oauth.enums.GrantType;
import net.oauth.enums.ResponseType;
import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth2Parameters;
import net.oauth.provider.OAuth2ServiceProvider;

/**
 * @author Bienfait Sindi
 * @since 25 July 2010
 *
 */
public class TestOAuth2 {

	public static void main(String[] args) {
		OAuth2ServiceProvider provider = new OAuth2ServiceProvider("https://graph.facebook.com/oauth/authorize", "https://graph.facebook.com/oauth/access_token");
		OAuth2Consumer consumer = new OAuth2Consumer("381571044440", "460f1f0ca32bbf32458a8b8dec6a216e", provider);
		
		//begin
		try {
			System.out.println(consumer.generateRequestAuthorizationUrl(ResponseType.CODE, "http://localhost:8080/Music4Point0/oauth_redirect", null, ",", (String[])null));
			
			OAuth2Parameters parameters = new OAuth2Parameters();
			parameters.setCode("3f61aa47b915215a938d2722-682316653|5OPOkmKew_W8vybb9sccIPoivAg.");
			parameters.setRedirectUri("http://localhost:8080/Music4Point0/oauth_redirect");
			
//			Map<String, String> oauthResponse = consumer.requestAcessToken(GrantType.AUTHORIZATION_CODE, parameters, ",", (String[])null);
//			System.out.println(oauthResponse);
//			System.out.println(oauthResponse.get("access_token"));
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
