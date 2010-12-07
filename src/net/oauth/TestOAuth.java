package net.oauth;

/**
 * 
 */

import net.oauth.consumer.OAuth1Consumer;
import net.oauth.exception.OAuthException;
import net.oauth.provider.OAuth1ServiceProvider;
import net.oauth.signature.impl.OAuthHmacSha1Signature;
import net.oauth.token.OAuthToken;
import net.oauth.token.RequestToken;

/**
 * @author Bienfait Sindi
 *
 */
public class TestOAuth {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		try {
			String consumerKey = "e0ddoJdGMLfPj3DSLODA";
			String consumerSecret = "3CUTY50IcHYp1c0VV9zTGwv1DsnRL1GdXntsXtyRM";
			
			OAuth1ServiceProvider provider = new OAuth1ServiceProvider("https://api.twitter.com/oauth/request_token", "https://api.twitter.com/oauth/authorize", "https://api.twitter.com/oauth/access_token");
			OAuth1Consumer consumer = new OAuth1Consumer(consumerKey, consumerSecret, provider);
			RequestToken requestToken = consumer.requestUnauthorizedToken(null, "http://localhost:8080/Music4Point0/oauth/oauth_token_ready", null, new OAuthHmacSha1Signature());
			System.out.println(requestToken);
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
