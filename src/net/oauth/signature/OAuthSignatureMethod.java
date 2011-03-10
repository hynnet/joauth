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
package net.oauth.signature;

/**
 * @author Bienfait Sindi
 * @since 08 December 2009
 */
public interface OAuthSignatureMethod {

	public static final String SIGNATURE_METHOD_HMAC_SHA1 = "HMAC-SHA1";
	public static final String SIGNATURE_METHOD_RSA_SHA1 = "RSA-SHA1";
	public static final String SIGNATURE_METHOD_PLAINTEXT = "PLAINTEXT";
}
