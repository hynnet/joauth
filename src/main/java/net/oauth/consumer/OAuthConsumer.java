/**
 * 
 */
package net.oauth.consumer;

import org.apache.log4j.Logger;

import net.oauth.http.HttpClient;
import net.oauth.http.impl.DefaultHttpClient;
import net.oauth.provider.OAuthServiceProvider;


/**
 * @author Buhake Sindi
 * @since 11 October 2011
 *
 */
public abstract class OAuthConsumer<T extends OAuthServiceProvider> {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private HttpClient client;
	private T serviceProvider;
	
	/**
	 * @param serviceProvider
	 */
	protected OAuthConsumer(T serviceProvider) {
		this(new DefaultHttpClient(), serviceProvider);
	}

	/**
	 * @param client
	 * @param serviceProvider
	 */
	protected OAuthConsumer(HttpClient client, T serviceProvider) {
		super();
		setClient(client);
		setServiceProvider(serviceProvider);
	}

	/**
	 * @return the client
	 */
	public HttpClient getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(HttpClient client) {
		this.client = client;
	}

	/**
	 * @return the serviceProvider
	 */
	public T getServiceProvider() {
		return serviceProvider;
	}

	/**
	 * @param serviceProvider the serviceProvider to set
	 */
	public void setServiceProvider(T serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
}
