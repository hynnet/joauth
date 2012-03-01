/**
 *  Copyright 2010-2011 Buhake Sindi

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package net.oauth.signature.impl;

import java.security.GeneralSecurityException;

import net.oauth.signature.ConsumerSecretBasedOAuthSignature;
import net.oauth.signature.OAuthSignatureMethod;
import net.oauth.util.OAuthSignatureUtil;
import net.oauth.util.OAuth1Util;

/**
 * @author Bienfait Sindi
 *
 */
public class OAuthHmacSha1Signature extends ConsumerSecretBasedOAuthSignature {
	
	/**
	 * 
	 */
	public OAuthHmacSha1Signature() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see net.oauth.signature.OAuthSignature#getOAuthSignatureMethod()
	 */
	@Override
	public String getOAuthSignatureMethod() {
		// TODO Auto-generated method stub
		return OAuthSignatureMethod.SIGNATURE_METHOD_HMAC_SHA1;
	}

	/* (non-Javadoc)
	 * @see net.oauth.signature.OAuthSignature#sign(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String sign(String data) throws GeneralSecurityException {
		// TODO Auto-generated method stub
		String key = OAuth1Util.encode(getConsumerSecret()) + "&" + OAuth1Util.encode((getTokenSecret() == null || getTokenSecret().isEmpty()) ? "" : getTokenSecret());
		return OAuthSignatureUtil.generateHmacSHA1Signature(data, key);
	}
}
