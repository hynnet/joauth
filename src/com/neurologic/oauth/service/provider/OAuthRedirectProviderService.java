/**
 * 
 */
package com.neurologic.oauth.service.provider;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.exception.OAuthException;

import com.neurologic.oauth.service.provider.manager.OAuthTokenManager;
import com.neurologic.oauth.service.provider.response.RedirectResult;

/**
 * @author Buhake Sindi
 * @since 17 September 2011
 *
 */
public abstract class OAuthRedirectProviderService<TM extends OAuthTokenManager> extends AbstractOAuthProviderService<TM> {
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.OAuthService#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws OAuthException {
		// TODO Auto-generated method stub
		try {
			RedirectResult result = execute(request);
			if (result != null) {
				result.sendRedirect(request, response);
			} else {
				if (logger.isInfoEnabled()) {
					logger.info(this.getClass().getName() + ": Nothing to redirect.");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error redirecting...", e);
		}
	}
	
	protected abstract RedirectResult execute(HttpServletRequest request) throws OAuthException;
	protected abstract void onException(Exception e, HttpServletRequest request);
}
