/**
 * 
 */
package net.oauth.parameters;

/**
 * @author Buhake Sindi
 * @since 07 October 2011
 *
 */
public class OAuthErrorParameters extends OAuthParameters {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2301975912717748711L;
	public static final String ERROR = "error";

	/**
	 * 
	 */
	public OAuthErrorParameters() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param error
	 */
	public OAuthErrorParameters(String error) {
		super();
		setError(error);
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return get(ERROR);
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		put(ERROR, error);
	}
}
