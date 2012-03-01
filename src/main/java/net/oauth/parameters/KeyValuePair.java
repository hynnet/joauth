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
package net.oauth.parameters;

import java.util.LinkedHashMap;
import java.util.Map;

import net.oauth.util.OAuth1Util;

/**
 * @author Bienfait Sindi
 * @since 31 March 2010
 *
 */
public abstract class KeyValuePair {

	private Map<String, String> kvMap;
	private String startDelimiter;
	private String endDelimiter;
	private String pairDelimiter;
	
	/**
	 * @param startDelimiter
	 * @param endDelimiter
	 * @param pairDelimiter
	 */
	public KeyValuePair(String startDelimiter, String endDelimiter, String pairDelimiter) {
		this.startDelimiter = startDelimiter;
		this.endDelimiter = endDelimiter;
		this.pairDelimiter = pairDelimiter;
		kvMap = new LinkedHashMap<String, String>();
	}
	
	public void add(String key, String value) {
		kvMap.put(key, value);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = "";	
		
		for (String key : kvMap.keySet()) {
			if (!s.isEmpty()) {
				s += pairDelimiter;
			}
			
			s += OAuth1Util.encode(key) + startDelimiter + OAuth1Util.encode(kvMap.get(key)) + endDelimiter;
		}
		
		return s;
	}
}
