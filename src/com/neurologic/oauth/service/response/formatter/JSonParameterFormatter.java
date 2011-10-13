/**
 * 
 */
package com.neurologic.oauth.service.response.formatter;

import java.util.Map.Entry;

import net.oauth.parameters.OAuthParameters;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Buhake Sindi
 * @since 06 October 2011
 *
 */
public class JSonParameterFormatter extends AbstractParameterFormatter {

	/**
	 * 
	 */
	public JSonParameterFormatter() {
		super("application/json");
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.formatter.ParameterFormatter#format(net.oauth.parameters.OAuthParameters)
	 */
	@Override
	public String format(OAuthParameters parameters, String charset) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		try {
			for (Entry<String, String> entry : parameters.getOAuthParameters().entrySet()) {
				json.put(entry.getKey(), entry.getValue());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			logger.error("JSON formatting error: " + e.getLocalizedMessage(), e);
		}
		
		return json.toString();
	}
}
