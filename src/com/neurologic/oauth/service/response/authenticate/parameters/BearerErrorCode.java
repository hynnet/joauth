/**
 * 
 */
package com.neurologic.oauth.service.response.authenticate.parameters;

/**
 * For OAuth 2 Bearer token type <b>only</b>.
 * 
 * @author Buhake Sindi
 * @since 26 September 2011
 *
 */
public enum BearerErrorCode {
	INVALID_REQUEST(400, "invalid_request")
	,INVALID_TOKEN(401, "invalid_token")
	,INSUFFICIENT_SCOPE(403, "insufficient_scope")
	;
	private int statusCode;
	private String errorCode;
	
	/**
	 * @param statusCode
	 * @param errorCode
	 */
	private BearerErrorCode(int statusCode, String errorCode) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
	}
	
	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}
	
	public static BearerErrorCode of(String errorCode) {
		if (INVALID_REQUEST.errorCode.equals(errorCode)) {
			return INVALID_REQUEST;
		}
		
		if (INVALID_TOKEN.errorCode.equals(errorCode)) {
			return INVALID_TOKEN;
		}
		
		if (INSUFFICIENT_SCOPE.errorCode.equals(errorCode)) {
			return INSUFFICIENT_SCOPE;
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return errorCode;
	}
}
