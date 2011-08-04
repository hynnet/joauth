/**
 *  Copyright 2010-2011 Buhake Sindi

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package com.neurologic.oauth.service.provider.v1;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuthParameters;
import net.oauth.provider.OAuth1ServiceProvider;
import net.oauth.signature.ConsumerSecretBasedOAuthSignature;
import net.oauth.signature.OAuthSignature;
import net.oauth.signature.OAuthSignatureMethod;
import net.oauth.signature.impl.OAuthHmacSha1Signature;
import net.oauth.signature.impl.OAuthPlainTextSignature;
import net.oauth.signature.impl.OAuthRsaSha1Signature;
import net.oauth.util.OAuth1Util;

import org.apache.log4j.Logger;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.oauth.service.OAuthProviderService;

/**
 * @author Buhake Sindi
 * @since 11 July 2011
 *
 */
public abstract class OAuth1ProviderService implements OAuthProviderService<OAuth1ServiceProvider> {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private static final String OAUTH_AUTHORIZATION_HEADER_START = "OAuth ";
	
	protected OAuth1ServiceProvider serviceProvider;
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.OAuthProviderService#setOAuthServiceProvider(net.oauth.provider.OAuthServiceProvider)
	 */
	@Override
	public void setOAuthServiceProvider(OAuth1ServiceProvider serviceProvider) {
		// TODO Auto-generated method stub
		this.serviceProvider = serviceProvider;
		
	}

	protected OAuthParameters getOAuthAuthorizationParameters(HttpServletRequest request) throws OAuthAuthorizationException {
		if (request == null) {
			return null;
		}
		
		String header = request.getHeader(HTTP_HEADER_AUTHORIZATION);
		
		if (header == null || header.isEmpty()) {
			throw new OAuthAuthorizationException("Cannot find HTTP Header '" + HTTP_HEADER_AUTHORIZATION + "'.");
		}
		
		if (!header.startsWith(OAUTH_AUTHORIZATION_HEADER_START)) {
			throw new OAuthAuthorizationException("HTTP Authorization header is invalid.");
		}
		
		if (logger.isInfoEnabled()) {
			logger.info(HTTP_HEADER_AUTHORIZATION + ": " + header);
		}
		
		//TODO: This is ugly, but what can I do?
		Map<String, String> oauthHeaderParameters = new HashMap<String, String>();
		String[] headerValues = header.substring(OAUTH_AUTHORIZATION_HEADER_START.length()).split(",");
		for (String headerValue : headerValues) {
			String[] headerTokens = headerValue.split("=");
			String key = headerTokens[0];
			if (!OAuthParameters.OAUTH_REALM.equals(key) && ! key.startsWith("oauth_")) {
				throw new OAuthAuthorizationException("OAuth Authorization has unrecognizable key '" + key + "' (String: " + headerValue + ".");
			}
			
			String hv = headerTokens[1];
			if (!hv.startsWith("\"") && !hv.endsWith("\"")) {
				throw new OAuthAuthorizationException("OAuth Authorization has an incorrect value (String: " + headerValue + ".");
			}
			oauthHeaderParameters.put(key, hv.substring(1, hv.length() - 1));
		}
		
		return new OAuthParameters(oauthHeaderParameters);
	}
	
	/**
	 * This method returns the {@link OAuthSignature} based on the oauth signature method.
	 * <br /><b>Note:</b>&nbsp;This method <b>never</b> returns a {@link OAuthRsaSha1Signature} for oauth signature "RSA-SHA1".
	 * Please override this method, should you wish to include RSA-SHA1 signature method.
	 * 
	 * @param oauthSignature the oauth signature method (currently supports <i>PLAINTEXT</i>, <i>HMAC-SHA1</i>).
	 * @return an {@link OAuthSignature} object, if found, null otherwise.
	 */
	protected OAuthSignature getOAuthSignature(String oauthSignature) {
		if (OAuthSignatureMethod.SIGNATURE_METHOD_PLAINTEXT.equals(oauthSignature)) {
			return new OAuthPlainTextSignature();
		}
		
		if (OAuthSignatureMethod.SIGNATURE_METHOD_HMAC_SHA1.equals(oauthSignature)) {
			return new OAuthHmacSha1Signature();
		}
		
		return null;
	}
	
	protected String getOAuthConsumerSecret(String oauthConsumerKey) {
		return "";
	}
	
	protected boolean verifyOAuthAuthorizationHeader(HttpServletRequest request) throws OAuthException {
		// TODO Auto-generated method stub		
		OAuthParameters oauthParameters = getOAuthAuthorizationParameters(request);
		String requestSignature = oauthParameters.getOAuthParameterValue(OAuthParameters.OAUTH_SIGNATURE);
		String signatureMethod = oauthParameters.getOAuthParameterValue(OAuthParameters.OAUTH_SIGNATURE_METHOD);
		logger.debug("oauth_signature: " + requestSignature + ", oauth_signature_method: " + signatureMethod);
		
		//First, get a signature method
		OAuthSignature signature = getOAuthSignature(signatureMethod);
		if (signature == null) {
			throw new OAuthException("No Signature method object found (nor implemented) for signature method '" + signatureMethod + "'.");
		}
		
		if (signature instanceof ConsumerSecretBasedOAuthSignature) {
			((ConsumerSecretBasedOAuthSignature)signature).setConsumerSecret(getOAuthConsumerSecret(oauthParameters.getOAuthParameterValue(OAuthParameters.OAUTH_CONSUMER_KEY)));
		}
		
		try {
			String baseString = OAuth1Util.getSignatureBaseString(request.getMethod(), serviceProvider.getRequestTokenUrl(), oauthParameters.getOAuthParameters());
			String derivedSignature = signature.sign(baseString);
			
			return requestSignature.equals(derivedSignature);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			throw new OAuthAuthorizationException("Security Exception: " + e.getLocalizedMessage(), e);
		}
	}
}
