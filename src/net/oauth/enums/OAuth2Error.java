/**
 * 
 */
package net.oauth.enums;

/**
 * @author Buhake Sindi
 * @since 01 October 2011
 *
 */
public enum OAuth2Error {
	INVALID_REQUEST("invalid_request")
	,INVALID_CLIENT("invalid_client")
	,UNAUTHORIZED_CLIENT("unauthorized_client")
	,ACCESS_DENIED("access_denied")
	,UNSUPPORTED_RESPONSE_TYPE("unsupported_response_type")
	,INVALID_GRANT("invalid_grant")
	,UNSUPPORTED_GRANT_TYPE("unsupported_grant_type")
	,INVALID_SCOPE("invalid_scope")
	,SERVER_ERROR("server_error")
	,TEMPORARILY_UNAVAILABLE("temporarily_unavailable")
	;
	private String type;
	
	/**
	 * @param type
	 */
	private OAuth2Error(String type) {
		this.type = type;
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
