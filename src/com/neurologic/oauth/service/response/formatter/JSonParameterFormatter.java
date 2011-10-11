/**
 * 
 */
package com.neurologic.oauth.service.response.formatter;

import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import net.oauth.parameters.OAuthParameters;

/**
 * @author Buhake Sindi
 * @since 06 October 2011
 *
 */
public class JSonParameterFormatter extends AbstractParameterFormatter {
	
	private static final Logger logger = Logger.getLogger(JSonParameterFormatter.class);

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
	public String format(OAuthParameters parameters) {
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
