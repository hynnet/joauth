/**
 * 
 */
package com.neurologic.oauth.processor;

/**
 * @author Buhake Sindi
 * @since 18 January 2012
 *
 */
public class OAuthProcessorFactory {

	public static OAuthProcessor createOAuthProcessor(String protocol) {
		OAuthProcessor processor = null;
		
		if (protocol != null) {
			protocol = protocol.toLowerCase(); //Just in case
			
			if ("http".equals(protocol) || "https".equals(protocol)) {
				processor = new HttpRequestOAuthProcessor();
			} else {
				//Other stuff here...
			}
		}
		
		return processor;
	}
}
