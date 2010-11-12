/**
 * 
 */
package net.oauth.enums;

/**
 * @author Bienfait Sindi
 * @since 24 July 2010
 *
 */
public enum GrantType {
	AUTHORIZATION_CODE("authorization_code"),
	PASSWORD("password"),
	ASSERTION("assertion"),
	REFRESH_TOKEN("refresh_token"),
	NONE("none")
	;
	private final String grantType;
	
	/**
	 * 
	 * @param grantType
	 */
	private GrantType(final String grantType) {
		// TODO Auto-generated constructor stub
		this.grantType = grantType;
	}

//	/**
//	 * @return the grantType
//	 */
//	public String getGrantType() {
//		return grantType;
//	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public final String toString() {
		// TODO Auto-generated method stub
		return grantType;
	}
}
