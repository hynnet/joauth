/**
 * 
 */
package net.oauth.signature;


/**
 * @author Bienfait Sindi
 * @since 23 November 2010
 *
 */
public abstract class ConsumerSecretBasedOAuthSignature implements OAuthSignature {

	private String consumerSecret;
	private String tokenSecret;
	
	/**
	 * 
	 */
	protected ConsumerSecretBasedOAuthSignature() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param consumerSecret the consumerSecret to set
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}
	
	/**
	 * @param tokenSecret the tokenSecret to set
	 */
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	
	/**
	 * @return the consumerSecret
	 */
	protected String getConsumerSecret() {
		return consumerSecret;
	}
	
	/**
	 * @return the tokenSecret
	 */
	protected String getTokenSecret() {
		return tokenSecret;
	}
}
