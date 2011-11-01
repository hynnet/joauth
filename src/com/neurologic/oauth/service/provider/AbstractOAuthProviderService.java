/**
 * 
 */
package com.neurologic.oauth.service.provider;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.neurologic.oauth.service.AbstractOAuthService;
import com.neurologic.oauth.service.OAuthProviderService;
import com.neurologic.oauth.service.provider.manager.OAuthTokenManager;
import com.neurologic.oauth.service.provider.manager.OAuthTokenManagerRepository;
import com.neurologic.oauth.service.response.Result;

/**
 * @author Buhake Sindi
 * @since 01 September 2011
 *
 */
public abstract class AbstractOAuthProviderService<TM extends OAuthTokenManager> extends AbstractOAuthService implements OAuthProviderService {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private TM oauthTokenManager;
	
	/**
	 * @return the oauthTokenManager
	 */
	protected TM getOauthTokenManager() {
		if (oauthTokenManager == null) {
			oauthTokenManager = OAuthTokenManagerRepository.getInstance().get(getOauthName());
		}
		
		return oauthTokenManager;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.AbstractOAuthService#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Result result = execute(request);
		if (result == null) {
			throw new Exception("No result returned from '" + this.getClass().getName() + "'. Aborting...");
		}
		
		try {
			result.execute(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new Exception("Error executing result '" + result.getClass().getName() + "'. Aborting...");
		}
	}
	
	/**
	 * This method must <b>never</b> return an {@link Exception} (or its subclasses). 
	 * The developer must do exception handling and return the exception within {@link Result}.
	 * @param request
	 * @return
	 */
	protected abstract Result execute(HttpServletRequest request);
}
