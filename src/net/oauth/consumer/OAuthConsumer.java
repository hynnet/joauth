/**
 * 
 */
package net.oauth.consumer;

import net.oauth.provider.OAuthServiceProvider;

import com.neurologic.http.HttpClient;

/**
 * @author Buhake Sindi
 * @since 11 October 2011
 *
 */
public abstract class OAuthConsumer<T extends OAuthServiceProvider> {

	private HttpClient client;
	private T serviceProvider;
	
	/**
	 * @param serviceProvider
	 */
	protected OAuthConsumer(T serviceProvider) {
		this(null, serviceProvider);
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
