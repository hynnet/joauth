/**
 * 
 */
package com.neurologic.oauth.service.provider.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Buhake Sindi
 * @since 19 September 2011
 *
 */
public class OAuthJsonResponseMessage extends AbstractOAuthResponseMessage {

	private JSONObject json = new JSONObject();

	/**
	 * 
	 */
	public OAuthJsonResponseMessage() {
		super();
		// TODO Auto-generated constructor stub
		setCacheControl("no-cache");
		setContentType("application/json");
	}
	
	public void put(String key, String value) throws JSONException {
		json.put(key, value);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.response.AbstractOAuthResponseMessage#getMessage()
	 */
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return json.toString();
	}
}
