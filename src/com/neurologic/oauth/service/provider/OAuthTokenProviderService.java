/**
 * 
 */
package com.neurologic.oauth.service.provider;

import java.io.Closeable;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.exception.OAuthException;
import net.oauth.provider.OAuthServiceProvider;

import com.neurologic.oauth.service.provider.manager.OAuthTokenManager;
import com.neurologic.oauth.service.provider.response.ExceptionResponseMessage;
import com.neurologic.oauth.service.provider.response.OAuthResponseMessage;

/**
 * @author Buhake Sindi
 * @since 15 August 2011
 *
 */
public abstract class OAuthTokenProviderService<SP extends OAuthServiceProvider, TM extends OAuthTokenManager> extends AbstractOAuthProviderService<TM> {
	
	protected SP serviceProvider;
	
	/**
	 * Set the service provider required.
	 * @param serviceProvider
	 */
	public final void setOAuthServiceProvider(SP serviceProvider) {
		// TODO Auto-generated method stub
		this.serviceProvider = serviceProvider;
	}
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.OAuthService#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public final void execute(HttpServletRequest request, HttpServletResponse response) throws OAuthException {
		// TODO Auto-generated method stub
		OAuthResponseMessage responseMessage = null;
		
		try {
			responseMessage = execute(request);
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			logger.error("OAuthException: " + e.getLocalizedMessage(), e);
			responseMessage = new ExceptionResponseMessage(e);
		}
		
		//Execute response
		if (responseMessage != null) {
			try {
				response.reset();
				if (responseMessage.getCacheControl() != null) {
					response.setHeader("Cache-Control", responseMessage.getCacheControl());
				}
				response.setStatus(responseMessage.getStatusCode());
				response.setContentType(responseMessage.getContentType());
				response.setContentLength(responseMessage.getContentLength());
				response.getWriter().write(responseMessage.getMessage());
				response.getWriter().flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// TODO Auto-generated catch block
				try {
					close(response.getWriter());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("Error closing response writer: " + e.getLocalizedMessage(), e);
				}
			}
		}
	}
	
	private void close(Closeable resource) throws IOException {
		if (resource != null) {
			resource.close();
		}
	}
	
	protected abstract OAuthResponseMessage execute(HttpServletRequest request) throws OAuthException;
}
