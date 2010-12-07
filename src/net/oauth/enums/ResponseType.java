/**
 * 
 */
package net.oauth.enums;

/**
 * @author Bienfait Sindi
 * @since 24 July 2010
 *
 */
public enum ResponseType {

	CODE("code"),
	TOKEN("token"),
	CODE_AND_TOKEN("code_and_token")
	;
	private final String responseType;
	
	/**
	 * 
	 * @param responseType
	 */
	private ResponseType(final String responseType) {
		// TODO Auto-generated constructor stub
		this.responseType = responseType;
	}

//	/**
//	 * @return the responseType
//	 */
//	public String getResponseType() {
//		return responseType;
//	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public final String toString() {
		// TODO Auto-generated method stub
		return responseType;
	}

}
