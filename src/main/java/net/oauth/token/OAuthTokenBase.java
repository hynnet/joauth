/**
 * 
 */
package net.oauth.token;

import java.io.Serializable;

/**
 * @author Buhake Sindi
 * @since 11 August 2011
 *
 */
public abstract class OAuthTokenBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4614275677174967729L;

	public abstract int getOAuthVersion();
}
