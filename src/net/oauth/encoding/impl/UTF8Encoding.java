/**
 * 
 */
package net.oauth.encoding.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import net.oauth.encoding.OAuthEncoding;

import org.apache.log4j.Logger;

/**
 * @author Bienfait Sindi
 *
 */
public class UTF8Encoding implements OAuthEncoding {
	
	private static final Logger logger = Logger.getLogger(UTF8Encoding.class);

	/* (non-Javadoc)
	 * @see net.oauth.encoding.OAuthEncoding#decode(java.lang.String)
	 */
	@Override
	public String decode(String encodedData) {
		// TODO Auto-generated method stub
		String data = null;
		
		try {
			data = URLDecoder.decode(encodedData, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e);
		}
		
		return data;
	}

	/* (non-Javadoc)
	 * @see net.oauth.encoding.OAuthEncoding#encode(java.lang.String)
	 */
	@Override
	public String encode(String data) {
		// TODO Auto-generated method stub
		String encodedData = null;

		try {
			encodedData = URLEncoder.encode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getLocalizedMessage(), e);
		}
		
		StringBuffer buf = new StringBuffer();
        char focus;
        
        for (int i = 0; i < encodedData.length(); i++) {
            focus = encodedData.charAt(i);
            if (focus == '*') {
                buf.append("%2A");
            } else if (focus == '+') {
                buf.append("%20");
            } else if (focus == '%' && (i + 1) < encodedData.length() && encodedData.charAt(i + 1) == '7' && encodedData.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } else {
                buf.append(focus);
            }
        }
        
        return buf.toString();
		
//		return OAuthURLUTF8Encoder.encode(data).replace("+", "%20");
	}
}
