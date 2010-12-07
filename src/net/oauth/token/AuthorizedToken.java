/**
 * 
 */
package net.oauth.token;

/**
 * @author Bienfait Sindi
 * @since 20 November 2010
 *
 */
public class AuthorizedToken extends OAuthToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2383224891942308204L;
	private String verifier;
	
	/**
	 * @param token
	 * @param verifier
	 */
	public AuthorizedToken(String token, String verifier) {
		super(token);
		// TODO Auto-generated constructor stub
		this.verifier = verifier;
	}

	/**
	 * @return the verifier
	 */
	public String getVerifier() {
		return verifier;
	}
}
