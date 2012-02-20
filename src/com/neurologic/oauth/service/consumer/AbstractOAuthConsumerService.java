/**
 * 
 */
package com.neurologic.oauth.service.consumer;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.neurologic.oauth.service.OAuthConsumerService;
import com.neurologic.oauth.service.OAuthHttpService;
import com.neurologic.oauth.service.response.OAuthResult;
import com.neurologic.oauth.service.response.impl.OAuthMessageResult;
import com.neurologic.oauth.service.response.impl.StringMessage;

/**
 * @author Buhake Sindi
 * @since 24 September 2011
 *
 */
public abstract class AbstractOAuthConsumerService<C, T> extends OAuthHttpService implements OAuthConsumerService<C, T>  {

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
		//throw new UnsupportedOperationException("\"" + request.getMethod() + "\" method not support for Consumer Service.");
		try {
			return new OAuthMessageResult(HttpServletResponse.SC_METHOD_NOT_ALLOWED, new StringMessage(request.getMethod() + " not allowed in this method call.", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return null;
	}
}
