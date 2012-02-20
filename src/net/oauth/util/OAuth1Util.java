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
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.oauth.encoding.Encoding;
import net.oauth.encoding.impl.PercentEncoding;
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
	
	public static String getSignatureBaseString(String httpRequestMethod, String requestUrl, Map<String, String> oauthParameters) throws URISyntaxException {
		//Must remove first, "oauth_signature"
		if (oauthParameters != null && oauthParameters.containsKey(OAuth1Parameters.OAUTH_SIGNATURE)) {
			oauthParameters.remove(OAuth1Parameters.OAUTH_SIGNATURE);
		}
		
		return encode(httpRequestMethod.toUpperCase()) + "&" + encode(normalizeUrl(requestUrl)) + "&" + encode(normalizeParameters(requestUrl, oauthParameters));
	}
	
	public static String normalizeUrl(String url) throws URISyntaxException {
//		int questionMarkIndex = url.indexOf('?');
//		if (questionMarkIndex > -1) {
//			url = url.substring(0, questionMarkIndex);
//		}
//		
//		int forwardSlashIndex = url.indexOf('/', 8);
//		if (forwardSlashIndex > -1) {
//			int colonIndex = url.lastIndexOf(':', forwardSlashIndex);
//			
//			if (colonIndex > -1) {
//				String port = url.substring(colonIndex, forwardSlashIndex);
//				if ((url.startsWith("http://") && (port.equals(":80") || port.equals(":8080"))) ||
//					(url.startsWith("https://") && port.equals(":443"))) {
//					url = url.substring(0, colonIndex) +  url.substring(colonIndex + port.length());
//				}
//			}
//		}
//		
//		return url.toLowerCase();
		
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
	
	public static String normalizeParameters(String requestUrl, Map<String, String> parameters) {
		TreeMap<String, String> treeMap = null;
		
		if (parameters instanceof TreeMap) {
			treeMap = (TreeMap<String, String>) parameters;
		} else {
			treeMap = new TreeMap<String, String>(parameters);
		}
		
		if (requestUrl != null && !requestUrl.isEmpty() && requestUrl.indexOf('?') > -1) {
			treeMap.putAll(parseQueryString(requestUrl.substring(requestUrl.indexOf('?') + 1)));
		}
		
		StringBuffer sb = new StringBuffer();
		synchronized (treeMap) {
			
			for (Entry<String, String> entry : treeMap.entrySet()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				
				sb.append(encode(entry.getKey())).append("=").append(encode(entry.getValue()));
			}
		}
		
		return sb.toString();
	}
	
	public static Map<String, String> parseQueryString(String queryString) {
		Map<String, String> map = new HashMap<String, String>();
		if (queryString == null || queryString.isEmpty()) {
			return map;
		}
		
		String[] parameters = queryString.split("&");
		if (parameters != null) {
			for (String parameter : parameters) {
				String[] keyValue = parameter.split("=", 2);
				String name = decode(keyValue[0]);
				if (name != null && !name.isEmpty()) {
					String value = !keyValue[1].isEmpty() ? decode(keyValue[1]) : "";
					map.put(name, value);
				}
			}
		}
		
		return map;
	}
	
	public static String getQueryString(Map<String, String> parameters, KeyValuePair kvp) {
		if (parameters == null || parameters.isEmpty()) {
			return "";
		}
		
		if (parameters.containsKey(OAuth1Parameters.OAUTH_REALM)) {
			kvp.add(OAuth1Parameters.OAUTH_REALM, parameters.get(OAuth1Parameters.OAUTH_REALM));
		}
		
		synchronized (parameters) {
			for (Entry<String, String> entry : parameters.entrySet()) {
				
				if (OAuth1Parameters.OAUTH_REALM.equals(entry.getKey())) continue;
				kvp.add(entry.getKey(), entry.getValue());
			}
		}
		
		return kvp.toString();
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
