/**
 * 
 */
package com.neurologic.oauth.service.request.authentication;

import java.util.HashMap;
import java.util.Map;

import net.oauth.parameters.OAuth1Parameters;
import net.oauth.util.OAuth1Util;

import com.neurologic.exception.OAuthAuthorizationException;

/**
 * @author Buhake Sindi
 * @since 28 September 2011
 *
 */
public class OAuth1HttpAuthorizationChallenger extends AbstractHttpAuthorizationChallenger<OAuth1Parameters> {

	/**
	 * Default constructor. Sets the HTTP Authorization <code>auth-scheme</code> to <code>OAuth</code> (case insensitive).
	 */
	public OAuth1HttpAuthorizationChallenger() {
		super("OAuth", false);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.request.extractor.AbstractOAuthAuthorizationExtractor#parseAndGenerateData(java.lang.String)
	 */
	@Override
	protected OAuth1Parameters parseAndGenerateData(String authorizationString) throws OAuthAuthorizationException {
		//TODO: This is ugly, but what can I do?
		Map<String, String> oauthHeaderParameters = new HashMap<String, String>();
		String[] headerValues = authorizationString.split(",");
		for (String headerValue : headerValues) {
			String[] headerTokens = headerValue.split("=");
			String key = headerTokens[0];
			if (!OAuth1Parameters.OAUTH_REALM.equals(key) && !key.startsWith("oauth_")) {
				throw new OAuthAuthorizationException("OAuth Authorization has unrecognizable key '" + key + "' (String: " + headerValue + ").");
			}
			
			String hv = headerTokens[1];
			if (!hv.startsWith("\"") && !hv.endsWith("\"")) {
				throw new OAuthAuthorizationException("OAuth Authorization has an incorrect value (String: " + headerValue + ").");
			}
			oauthHeaderParameters.put(key, OAuth1Util.decode(hv.substring(1, hv.length() - 1)));
		}
		
		return new OAuth1Parameters(oauthHeaderParameters);
	}
}
