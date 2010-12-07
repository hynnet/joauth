/**
 * 
 */
package net.oauth.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import net.oauth.encoding.OAuthEncoding;
import net.oauth.encoding.impl.UTF8Encoding;
import net.oauth.parameters.KeyValuePair;
import net.oauth.parameters.OAuthParameters;

/**
 * @author Bienfait Sindi
 * @since 29 December 2009
 * 
 */
public class OAuthUtil {
	
	public static long getTimestamp() {
		return System.currentTimeMillis() / 1000;
	}
	
	public static String getNONCE() {
		return Long.toString(System.nanoTime());
	}
	
	public static String getSignatureBaseString(String httpRequestMethod, String requestUrl, Map<String, String> oauthParameters) {
		//Must remove first, "oauth_signature"
		if (oauthParameters != null && oauthParameters.containsKey(OAuthParameters.OAUTH_REALM)) {
			oauthParameters.remove(OAuthParameters.OAUTH_SIGNATURE);
		}
		
		return encode(httpRequestMethod.toUpperCase()) + "&" + encode(normalizeUrl(requestUrl)) + "&" + encode(normalizeParameters(requestUrl, oauthParameters));
	}
	
	public static String normalizeUrl(String url) {
		int questionMarkIndex = url.indexOf('?');
		if (questionMarkIndex > -1) {
			url = url.substring(0, questionMarkIndex);
		}
		
		int forwardSlashIndex = url.indexOf('/', 8);
		if (forwardSlashIndex > -1) {
			int colonIndex = url.lastIndexOf(':', forwardSlashIndex);
			
			if (colonIndex > -1) {
				String port = url.substring(colonIndex, forwardSlashIndex);
				if ((url.startsWith("http://") && (port.equals(":80") || port.equals(":8080"))) ||
					(url.startsWith("https://") && port.equals(":443"))) {
					url = url.substring(0, colonIndex) +  url.substring(colonIndex + port.length());
				}
			}
		}
		
		return url.toLowerCase();
	}
	
	public static String normalizeParameters(String requestUrl, Map<String, String> parameters) {
		TreeMap<String, String> treeMap = null;
		
		if (parameters instanceof TreeMap<?, ?>) {
			treeMap = (TreeMap<String, String>) parameters;
		} else {
			treeMap = new TreeMap<String, String>(parameters);
		}
		
		if (requestUrl != null && !requestUrl.isEmpty() && requestUrl.indexOf('?') > -1) {
			treeMap.putAll(parseQueryString(requestUrl.substring(requestUrl.indexOf('?') + 1)));
		}
		
		StringBuffer sb = new StringBuffer();
		Iterator<String> itr = treeMap.keySet().iterator();
		synchronized (itr) {
			
			while (itr.hasNext()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				
				String key = itr.next();
				String value = parameters.get(key);
				sb.append(encode(key)).append("=").append(encode(value));
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
				try {
					String[] keyValue = parameter.split("=", 2);
					String name = URLDecoder.decode(keyValue[0], "UTF-8");
					if (name.isEmpty())
						continue;
					String value = !keyValue[1].isEmpty() ? URLDecoder.decode(keyValue[1], "UTF-8") : "";
					map.put(name, value);
				} catch (UnsupportedEncodingException e) {}
			}
		}
		
		return map;
	}
	
	public static String getQueryString(Map<String, String> parameters, KeyValuePair kvp) {
		if (parameters == null || parameters.isEmpty()) {
			return "";
		}
		
		if (parameters.containsKey(OAuthParameters.OAUTH_REALM)) {
			kvp.add(OAuthParameters.OAUTH_REALM, parameters.get(OAuthParameters.OAUTH_REALM));
		}
		
		Iterator<String> itr = parameters.keySet().iterator();
		synchronized (itr) {
			while (itr.hasNext()) {
				String key = itr.next();
				
				if (OAuthParameters.OAUTH_REALM.equals(key)) continue;
				String value = parameters.get(key);
				kvp.add(key, value);
			}
		}
		
		return kvp.toString();
	}
	
	public static String encode(String s) {
		OAuthEncoding encoding = new UTF8Encoding();
		return encoding.encode(s);
	}
}
