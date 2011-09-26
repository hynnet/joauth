/**
 * 
 */
package net.oauth.enums;

/**
 * These token types exists only from OAuth 2 Draft 16 onwards.
 * 
 * @author Buhake Sindi
 * @since 21 September 2011
 *
 */
public enum TokenType {
	BEARER("Bearer")
	,MAC("MAC")
	;
	private String type;

	/**
	 * @param type
	 */
	private TokenType(String type) {
		this.type = type;
	}
	
	public static TokenType of(String type) {
		if (BEARER.type.equals(type)) {
			return BEARER;
		}
		
		if (MAC.type.equals(type)) {
			return MAC;
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return type;
	}
}
