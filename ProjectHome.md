

A better OAuth library has now been included. It now has a controller that handles the OAuth communication flow between the client and service provider.

**Example source code for JOAuth 1.2 (Service implementation)**

---


Suppose I have my `oauth-config.xml` as follows:

```
<?xml version="1.0" encoding="UTF-8"?>
<oauth-config>
<!-- Twitter OAuth Config -->
	<oauth name="twitter" version="1">
		<consumer key="TWITTER_KEY" secret="TWITTER_SECRET" />
		<provider requestTokenUrl="https://api.twitter.com/oauth/request_token" authorizationUrl="https://api.twitter.com/oauth/authorize" accessTokenUrl="https://api.twitter.com/oauth/access_token" />
	</oauth>

	<!-- Facebook OAuth -->
	<oauth name="facebook" version="2">
		<consumer key="APP_ID" secret="APP_SECRET" />
		<provider authorizationUrl="https://graph.facebook.com/oauth/authorize" accessTokenUrl="https://graph.facebook.com/oauth/access_token" />
	</oauth>

	<service path="/request_token_ready" class="com.neurologic.example.TwitterOAuthService" oauth="twitter">
		<success path="/start.htm" />
	</service>

	<service path="/oauth_redirect" class="com.neurologic.example.FacebookOAuthService" oauth="facebook">
		<success path="/start.htm" />
	</service>
</oauth-config>
```
The OAuth config file remains the same but the new service is different from JOAuth 1.1 implementation.

Here's the example I used for Facebook:

```
package com.neurologic.example;

import javax.servlet.http.HttpServletRequest;

import net.oauth.token.v2.AccessToken;

import com.neurologic.oauth.service.impl.OAuth2Service;

/**
 * @author Bienfait Sindi
 * @since 31 May 2011
 *
 */
public class FacebookOAuthService extends OAuth2Service {

	public static final String FACEBOOK_ACCESS_TOKEN_SESSION = "FACEBOOK_ACCESS_TOKEN_SESSION";
	private static final String REDIRECT_URL = "http://localhost:8080/my_app/oauth/oauth_redirect";

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.impl.OAuth2Service#getRedirectUri()
	 */
	@Override
	protected String getRedirectUri() {
		// TODO Auto-generated method stub
		return REDIRECT_URL;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.impl.OAuth2Service#getScope()
	 */
	@Override
	protected String[] getScope() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.impl.OAuth2Service#getScopeDelimiter()
	 */
	@Override
	protected String getScopeDelimiter() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.impl.OAuth2Service#getState()
	 */
	@Override
	protected String getState() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.OAuthService#saveAccessToken(javax.servlet.http.HttpServletRequest, java.lang.Object)
	 */
	@Override
	public void saveAccessToken(HttpServletRequest request, AccessToken accessToken) {
		// TODO Auto-generated method stub
		request.getSession().setAttribute(FACEBOOK_ACCESS_TOKEN_SESSION, accessToken);
	}
}
```

and for Twitter (which uses OAuth 1 final)

```
package com.neurologic.example;

import javax.servlet.http.HttpServletRequest;

import net.oauth.signature.OAuthSignature;
import net.oauth.signature.impl.OAuthHmacSha1Signature;
import net.oauth.token.v1.AccessToken;
import net.oauth.token.v1.RequestToken;

import com.neurologic.oauth.service.impl.OAuth1Service;

/**
 * @author Buhake Sindi
 * @since 31 May 2011
 *
 */
public class TwitterOAuthService extends OAuth1Service {

	public static final String TWITTER_REQUEST_TOKEN_SESSION = "TWITTER_REQUEST_TOKEN_SESSION";
	public static final String TWITTER_ACCESS_TOKEN_SESSION = "TWITTER_ACCESS_TOKEN_SESSION";
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.impl.OAuth1Service#getOAuthSignature()
	 */
	@Override
	protected OAuthSignature getOAuthSignature() {
		// TODO Auto-generated method stub
		return new OAuthHmacSha1Signature();
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.impl.OAuth1Service#getRealm()
	 */
	@Override
	protected String getRealm() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.impl.OAuth1Service#getRequestToken(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected RequestToken getRequestToken(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return (RequestToken) request.getSession().getAttribute(TWITTER_REQUEST_TOKEN_SESSION);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.OAuthService#saveAccessToken(javax.servlet.http.HttpServletRequest, java.lang.Object)
	 */
	@Override
	public void saveAccessToken(HttpServletRequest request,	AccessToken accessToken) {
		// TODO Auto-generated method stub
		request.getSession().setAttribute(TWITTER_ACCESS_TOKEN_SESSION, accessToken);
	}
}
```

**Monday, 30 May 2011 - JOAuth 1.2 finally out**

---


I have finally fixed all my changes and I'm proud to release the JOAuth 1.2 jar. The source code can be found on the JOAuth 1.2 Branch.

Changes:
  * No more passing of `client_secret` as a URL parameter. It's now added on the HTTP-Authorization header.
  * Created an `AccessToken` object to store access token received from the OAuth Service Provider. This allows the developer to see all the values returned as an access token.
  * Cleaned up `OAuth2Service` as well as `OAuth2Consumer` and `AbstractHttpHeader` (fixing a bug).
  * The developer can now implement the `saveAccessToken(HttpServletRequest, AccessToken)` method to see and store the received access token from the OAuth Server.

OAuth 1 also took a new direction in terms of the `OAuth1Service`. The following changes were added:

  * The developer has to implement the `getRequestToken(HttpServletRequest)` method to return the request token object if he/she wants the OAuth Process to complete the OAuth authorizaton workflow.
  * As with OAuth 2 service, the `saveAccessToken(HttpServletRequest, AccessToken)` method has been included to allow the developer to store their received access token.

JOAuth 1.2 is not backward compatible with JOAuth 1.1. The main reason is that I wanted the service to fully handle the authorization workflow instead of developers (to minimize bug creeps).

Happy coding! :)

**Wednesday, 25 May 2011 - JOAuth 1.2 BETA**

---


JOAuth 1.2 has taken a new direction. For one, you don't have to implement your own way of retrieving access token. All you have to do implement the `getRedirectUri()` method and let the controller worry about the rest. Also, I don't pass `client_secret` as a parameter field: It is now passed in the HTTP-Authorization header.
Every access token is still stored in the `Globals` constants.

OAuth 1 is unaffected at this point, mainly because OAuth 1 requires request token to request an access token.

**General**

---


Seeing that there were few OAuth 1 libraries and no OAuth 2 library (both in java), I decided to create a library that composes on both OAuth 1 and OAuth 2

**Note** The old OAuth.jar supports OAuth 1 and OAuth 2 (when both were in draft stages). It's now **obsolete** (I wouldn't recommend it).

**Dependencies:**
For OAuth 2, I've used JSON code from http://www.json.org/java/ and [HttpClient 4](http://hc.apache.org/) (highly recommended) and Log4J for logging purposes (which I highly recommend since it shows you in log whether access token were stored in session or not).

**Code example:** This code example works by connecting to Facebook Graph API.

```
package net.oauth;

import java.util.Map;

import net.oauth.consumer.OAuth2Consumer;
import net.oauth.enums.GrantType;
import net.oauth.enums.ResponseType;
import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth2Parameters;
import net.oauth.provider.OAuth2ServiceProvider;

/**
 * @author Buhake Sindi
 * @since 25 July 2010
 *
 */
public class TestOAuth2 {

	public static void main(String[] args) {
                String client_id = "";
                String client_secret = "";
		OAuth2ServiceProvider provider = new OAuth2ServiceProvider("https://graph.facebook.com/oauth/authorize", "https://graph.facebook.com/oauth/access_token");
		OAuth2Consumer consumer = new OAuth2Consumer(client_id, client_secret, provider);
		
		//begin
		try {
			System.out.println(consumer.generateRequestAuthorizationUrl(ResponseType.CODE, "http://localhost:8080/my_app/oauth_redirect", null, (String[])null));
			
			OAuth2Parameters parameters = new OAuth2Parameters();
			parameters.setCode("3f61aa47b915215a938d2722-682316653|5OPOkmKew_W8vybb9sccIPoivAg.");
			parameters.setRedirectUri("http://localhost:8080/my_app/oauth_redirect");
			
			Map<String, String> oauthResponse = consumer.requestAcessToken(GrantType.AUTHORIZATION_CODE, parameters, (String[])null);
			System.out.println(oauthResponse);
			System.out.println(oauthResponse.get("access_token"));
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
```

There's a major change on how to retrieve access token on JOAuth 1.2, the following code shows the change.

```
	String client_id = "ENTER YOUR OWN HERE";
        String client_secret = "ENTER YOUR OWN HERE";
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
```

Also, when subclassing the `OAuth2Service`, you will have to just implement the following mandatory methods:
  * `getRedirectUri()`
  * `saveAccessToken(HttpServletRequest request, AccessToken accessToken)`

The other methods are optional:
  * `getState()`
  * `getScope()`
  * `getScopeDelimiter()` (Facebook, e.g., uses comma `,` as a scope delimiter as opposed to the default single space character)

For a **full** explanation on  how to setup `OAuthServlet` (the controller that handles OAuth authorization flow), view the question I posted on [StackOverflow.com](http://stackoverflow.com/questions/4376612/joauth-a-java-based-oauth-1-final-and-oauth-2-draft-10-library-how-do-i-use)

If you find any bugs, faults, etc. please let me know.

Thanks

Happy coding! :)

_PS_: I will love feedback, it gets lonely here on Google Code. If you have any ideas worth contributing, please, let feel free to contact me ;D