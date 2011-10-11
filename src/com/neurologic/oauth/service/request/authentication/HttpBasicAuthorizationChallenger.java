/**
 * 
 */
package com.neurologic.oauth.service.request.authentication;

import java.io.IOException;

import sun.misc.BASE64Decoder;

import com.neurologic.exception.OAuthAuthorizationException;

/**
 * @author Buhake Sindi
 * @since 28 September 2011
 *
 */
public class HttpBasicAuthorizationChallenger extends AbstractHttpAuthorizationChallenger<String[]> {

	/**
	 * A HTTP Basic extraction.
	 */
	public HttpBasicAuthorizationChallenger() {
		super("Basic", false);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.request.extractor.AbstractOAuthAuthorizationExtractor#parseAndGenerateData(java.lang.String)
	 */
	@Override
	protected String[] parseAndGenerateData(String authorizationString) throws OAuthAuthorizationException {
		// TODO Auto-generated method stub
		try {
			BASE64Decoder b64decoder = new BASE64Decoder();
			String originalString = new String(b64decoder.decodeBuffer(authorizationString));
			return originalString.split(":");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new OAuthAuthorizationException(e.getLocalizedMessage(), e);
		}
	}
}
