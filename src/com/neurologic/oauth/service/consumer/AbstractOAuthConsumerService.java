/**
 * 
 */
package com.neurologic.oauth.service.consumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.exception.OAuthException;

import org.apache.log4j.Logger;

import com.neurologic.oauth.service.AbstractOAuthService;
import com.neurologic.oauth.service.OAuthConsumerService;

/**
 * @author Buhake Sindi
 * @since 24 September 2011
 *
 */
public abstract class AbstractOAuthConsumerService<C, T> extends AbstractOAuthService implements OAuthConsumerService<C, T>  {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private C consumer;
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.OAuthService#setOAuthConsumer(java.lang.Object)
	 */
	@Override
	public void setOAuthConsumer(C consumer) {
		// TODO Auto-generated method stub
		this.consumer = consumer;
	}

	/**
	 * @return the consumer
	 */
	protected C getConsumer() {
		return consumer;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.AbstractOAuthService#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		execute(request);
	}
	
	protected abstract void execute(HttpServletRequest request) throws OAuthException;
}
