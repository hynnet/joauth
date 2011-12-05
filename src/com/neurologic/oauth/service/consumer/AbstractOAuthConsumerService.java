/**
 * 
 */
package com.neurologic.oauth.service.consumer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.neurologic.oauth.service.AbstractOAuthHttpService;
import com.neurologic.oauth.service.OAuthConsumerService;
import com.neurologic.oauth.service.response.OAuthResult;

/**
 * @author Buhake Sindi
 * @since 24 September 2011
 *
 */
public abstract class AbstractOAuthConsumerService<C, T> extends AbstractOAuthHttpService implements OAuthConsumerService<C, T>  {

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
	 * @see com.neurologic.oauth.service.AbstractOAuthHttpService#executePost(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected OAuthResult executePost(HttpServletRequest request) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("\"" + request.getMethod() + "\" method not support for Consumer Service.");
	}
}
