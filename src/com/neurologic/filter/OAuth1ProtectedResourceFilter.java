/**
 * 
 */
package com.neurologic.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth1Parameters;

import com.neurologic.exception.OAuthAuthorizationException;
import com.neurologic.exception.OAuthRejectedException;
import com.neurologic.oauth.service.provider.manager.OAuth1TokenManager;
import com.neurologic.oauth.service.provider.manager.OAuthTokenManagerRepository;
import com.neurologic.oauth.service.request.authentication.HttpAuthorizationChallenger;
import com.neurologic.oauth.service.request.authentication.OAuth1HttpAuthorizationChallenger;
import com.neurologic.oauth.service.response.authenticate.OAuth1WWWAuthenticateResponse;
import com.neurologic.oauth.service.response.authenticate.WWWAuthenticateResponse;
import com.neurologic.oauth.service.response.authenticate.parameters.OAuth1WWWAuthenticateParameters;

/**
 * @author Buhake Sindi
 * @since 15 August 2011
 *
 */
public class OAuth1ProtectedResourceFilter extends OAuthProtectedResourceFilter {

	/* (non-Javadoc)
	 * @see com.neurologic.filter.OAuthProtectedResourceFilter#doFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		if (logger.isInfoEnabled()) {
			logger.info("Getting oauth token manager from oauth '" + getOauthName() + "'.");
		}
		
		OAuth1TokenManager tokenManager = OAuthTokenManagerRepository.getInstance().get(getOauthName());
		if (tokenManager == null) {
			logger.error("No token manager found. Strange!");
			throw new ServletException("No oauth authorization done.");
		}
		
		try {
			String authorizationString = request.getHeader(HTTP_HEADER_AUTHORIZATION);
			if (authorizationString == null || authorizationString.isEmpty()) {
				throw new OAuthAuthorizationException(HTTP_HEADER_AUTHORIZATION + " not found.");
			}
			
			HttpAuthorizationChallenger<OAuth1Parameters> challenger = new OAuth1HttpAuthorizationChallenger();
			OAuth1Parameters oauthParameters = challenger.processChallenge(authorizationString);	
			
			if (!tokenManager.canAccessProtectedResources(oauthParameters.getOAuthConsumerKey(), oauthParameters.getOAuthToken())) {
				throw new OAuthRejectedException("Access Denied.");
			}
			
			//We verified you, enjoy!
			filterChain.doFilter(request, response);
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			logger.error("OAuth Authorization error" , e);
			handleException(response, e.getLocalizedMessage());
		}
	}
	
	private void handleException(HttpServletResponse response, String error) {
		//Since this is OAuth 1
		WWWAuthenticateResponse authenticateResponse = new OAuth1WWWAuthenticateResponse(new OAuth1WWWAuthenticateParameters(getRealm()));
		response.setHeader(WWWAuthenticateResponse.HTTP_AUTHENTICATE_HEADER, authenticateResponse.getValue());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		if (error != null) {
			try {
				response.getWriter().write(error);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("IOException: " + e.getLocalizedMessage(), e);
			}
		}
	}
}
