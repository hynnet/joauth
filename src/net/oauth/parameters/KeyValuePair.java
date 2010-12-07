/**
 * 
 */
package net.oauth.parameters;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import net.oauth.util.OAuthUtil;

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
		Iterator<String> itr = kvMap.keySet().iterator();
		
		if (itr != null) {
			while (itr.hasNext()) {
				if (!s.isEmpty()) {
					s += pairDelimiter;
				}
				
				String key = itr.next();
				s += OAuthUtil.encode(key) + startDelimiter + OAuthUtil.encode(kvMap.get(key)) + endDelimiter; 
			}
		}
		
		return s;
	}
}
