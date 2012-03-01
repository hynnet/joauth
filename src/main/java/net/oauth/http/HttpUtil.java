/**
 *  Copyright 2010-2012 Buhake Sindi

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
package net.oauth.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Buhake Sindi
 * @since 01 March 2012
 *
 */
public class HttpUtil {

	public static Map<String, String[]> parseQueryString(String queryString) {
		if (queryString == null) {
			return null;
		}
		
		Map<String, String[]> httpParameters = new LinkedHashMap<String, String[]>();
		String[] queryParameters = queryString.split("&");
		for (String parameter : queryParameters) {
			String[] kvs = parameter.split("=", 2);
			String key = kvs[0];
			String value = kvs[1];
			
			if (!httpParameters.containsKey(key)) {
				httpParameters.put(key, new String[] {value});
			} else {
				List<String> list = new ArrayList<String>(Arrays.asList(httpParameters.get(key)));
				if (!list.contains(value)) {
					list.add(value);
				}
				
				//Remove
				httpParameters.remove(key);
				//Add
				httpParameters.put(key, list.toArray(new String[list.size()]));
			}
		}
		
		return Collections.unmodifiableMap(httpParameters);
	}
	
	public static String toQueryString(Map<String, String[]> httpParameters) {
		if (httpParameters == null) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String[]> entry : httpParameters.entrySet()) {
			for (String value : entry.getValue()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				
				sb.append(entry.getKey()).append("\"=").append(value).append("\"");
			}
		}
		
		return sb.toString();
	}
}