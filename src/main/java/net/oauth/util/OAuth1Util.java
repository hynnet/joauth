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
package net.oauth.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import net.oauth.encoding.Encoding;
import net.oauth.encoding.impl.PercentEncoding;
import net.oauth.http.HttpUtil;
import net.oauth.parameters.KeyValuePair;
import net.oauth.parameters.OAuth1Parameters;


/**
 * @author Bienfait Sindi
 * @since 29 December 2009
 * 
 */
public class OAuth1Util {
	
	public static long getTimestamp() {
		return System.currentTimeMillis() / 1000;
	}
	
	public static String getNONCE() {
		return Long.toString(System.nanoTime());
	}
	
	/**
	 * Generate a Signature Base String, as specified on RFC 5849, paragraph 3.4.1.
	 * 
	 * @param requestMethod HTTP request method
	 * @param requestUrl
	 * @param oauthParameters OAuth Parameters
	 * @param requestParameters HTTP request parameters
	 * @return an OAuth base string that will be used for signature
	 * @throws URISyntaxException 
	 */
	public static String generateSignatureBaseString(String requestMethod, String requestUrl, OAuth1Parameters oauthParameters, Map<String, String[]> requestParameters) throws URISyntaxException {
		Map<String, String[]> parameters = new LinkedHashMap<String, String[]>();
		
		int parameterPos = requestUrl.indexOf('?');
		int fragmentPos = requestUrl.indexOf('#', parameterPos + 1);
		if (parameterPos > -1) {
			String queryString = fragmentPos > -1 ? requestUrl.substring(parameterPos + 1, fragmentPos) : requestUrl.substring(parameterPos + 1);
			Map<String, String[]> httpParameters= HttpUtil.parseQueryString(queryString);
			if (httpParameters != null && !httpParameters.isEmpty()) {
				parameters.putAll(httpParameters);
			}
		}
		
		//Adding oauth parameters
		for (Entry<String, String> entry : oauthParameters.getOAuthParameters().entrySet()) {
			if (!OAuth1Parameters.OAUTH_SIGNATURE.equals(entry.getKey())) {
				parameters.put(entry.getKey(), new String[] {entry.getValue()});
			}
		}
		
		if (requestParameters != null && !requestParameters.isEmpty()) {
			parameters.putAll(requestParameters);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(OAuth1Util.encode(requestMethod.toUpperCase())).append("&")
		  .append(OAuth1Util.encode(normalizeUrl(requestUrl))).append("&")
		  .append(OAuth1Util.encode(normalizeParameters(parameters)));
		
		return sb.toString();
	}
	
	/**
	 * Normalizes URL according to RFC 5849, paragraph 3.4.1.2.
	 * 
	 * @param url
	 * @return
	 * @throws URISyntaxException 
	 */
	public static String normalizeUrl(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String scheme = uri.getScheme().toLowerCase();
		String authority = uri.getAuthority().toLowerCase();
		int port = uri.getPort();
		
		StringBuilder sb = new StringBuilder();
		sb.append(scheme)
		  .append("://")
		  .append(authority);
		
		//Port
		if (port != -1 &&
			(("http".equals(scheme) && 80 != port) ||
			 ("https".equals(scheme) && 443 != port))) {
			sb.append(":").append(port);
		}
		
		//Path
		sb.append(uri.getPath());
		return sb.toString();
	}
	
	/**
	 * Normalize parameters according to RFC 5849, paragraph 3.4.1.3.2.
	 * 
	 * @param parameters
	 * @return
	 */
	public static String normalizeParameters(Map<String, String[]> parameters) {
		TreeMap<String, TreeSet<String>> sortedTreeMap = new TreeMap<String, TreeSet<String>>();
		
		if (parameters != null) {
			for (Entry<String, String[]> entry : parameters.entrySet()) {
				TreeSet<String> valueSet = new TreeSet<String>();
				
				for (String value : entry.getValue()) {
					valueSet.add(OAuth1Util.encode(value));
				}
				
				sortedTreeMap.put(OAuth1Util.encode(entry.getKey()), valueSet);
			}
		}
		
		Map<String, String[]> sortedParameters = new LinkedHashMap<String, String[]>();
		for (Entry<String, TreeSet<String>> entry : sortedTreeMap.entrySet()) {
			TreeSet<String> treeSet = entry.getValue();
			String[] sortedArray = treeSet.toArray(new String[treeSet.size()]);
			sortedParameters.put(entry.getKey(), sortedArray);
		}
		
		return HttpUtil.toQueryString(sortedParameters);
	}
	
	public static String toQueryString(String realm, OAuth1Parameters parameters, KeyValuePair kvp) {
		StringBuilder sb = new StringBuilder();
		if (realm != null && realm.isEmpty()) {
			sb.append("realm=\"")
			  .append(realm)
			  .append("\"")
			  .append(",");
		}
		
		for (Entry<String, String> entry : parameters.getOAuthParameters().entrySet()) {
			kvp.add(entry.getKey(), entry.getValue());
		}
		
		sb.append(kvp.toString());
		return sb.toString();
	}
	
	public static String encode(String s) {
		Encoding encoding = new PercentEncoding();
		return encoding.encode(s);
	}
	
	public static String decode(String s) {
		Encoding encoding = new PercentEncoding();
		return encoding.decode(s);
	}
}
