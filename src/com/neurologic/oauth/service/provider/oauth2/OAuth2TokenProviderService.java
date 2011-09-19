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
package com.neurologic.oauth.service.provider.oauth2;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.oauth.provider.OAuth2ServiceProvider;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.oauth.service.provider.OAuthTokenProviderService;
import com.neurologic.oauth.service.provider.manager.OAuth2TokenManager;

/**
 * @author Buhake Sindi
 * @since 11 July 2011
 *
 */
public abstract class OAuth2TokenProviderService extends OAuthTokenProviderService<OAuth2ServiceProvider, OAuth2TokenManager> {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private static final String OAUTH_AUTHORIZATION_HEADER_START = "Basic ";

	protected String getOAuthAuthorizationParameters(HttpServletRequest request) throws OAuthAuthorizationException {
		if (request == null) {
			return null;
		}
		
		String header = request.getHeader(HTTP_HEADER_AUTHORIZATION);
		
		if (header == null || header.isEmpty()) {
//			throw new OAuthAuthorizationException("Cannot find HTTP Header '" + HTTP_HEADER_AUTHORIZATION + "'.");
			return null;
		}
		
		if (!header.startsWith(OAUTH_AUTHORIZATION_HEADER_START)) {
			throw new OAuthAuthorizationException("HTTP Authorization header is invalid.");
		}
		
		if (logger.isInfoEnabled()) {
			logger.info(HTTP_HEADER_AUTHORIZATION + ": " + header);
		}
		
		try {
			BASE64Decoder b64decoder = new BASE64Decoder();
			String originalString = new String(b64decoder.decodeBuffer(header.substring(OAUTH_AUTHORIZATION_HEADER_START.length())));
			return originalString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new OAuthAuthorizationException(e.getLocalizedMessage(), e);
		}
	}
}
