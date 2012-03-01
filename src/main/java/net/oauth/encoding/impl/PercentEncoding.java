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
package net.oauth.encoding.impl;

import net.oauth.encoding.Encoding;

/**
 * @author Bienfait Sindi
 *
 */
public class PercentEncoding implements Encoding {
	
	private static final char[] HEXADECIMAL_DIGITS = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
	};
	
	private static final char PERCENT_CHARACTER = '%';


	/* (non-Javadoc)
	 * @see net.oauth.encoding.OAuthEncoding#decode(java.lang.String)
	 */
	@Override
	public String decode(String data) {
		// TODO Auto-generated method stub
		if (data == null || data.isEmpty()) {
			return data;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length(); i++) {
			char c = data.charAt(i);
			int index = i;
			if(c == PERCENT_CHARACTER) {
				sb.append(toCharacter(data.charAt(++i), data.charAt(++i), index));
			} else {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
	
	private char toCharacter(char char1, char char2, int index) {
		int digit1 = Character.digit(char1, 16);
		int digit2 = Character.digit(char2, 16);
		
		if (digit1 == -1) {
			throw new RuntimeException("Invalid character '" + char1 + "' on index " + (index + 1));
		}
		
		if (digit2 == -1) {
			throw new RuntimeException("Invalid character '" + char2 + "' on index " + (index + 2));
		}
		
		return (char) (((digit1 << 4) | digit2) & 0xFF);
	}

	/* (non-Javadoc)
	 * @see net.oauth.encoding.OAuthEncoding#encode(java.lang.String)
	 */
	@Override
	public String encode(String data) {
		// TODO Auto-generated method stub
		if (data == null || data.isEmpty()) {
			return data;
		}
		

		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < data.length(); i++) {
			char c = data.charAt(i);
			if((c >= 0x30 && c <= 0x39) //DIGITS [0..9] 
			 ||(c >= 0x41 && c <= 0x5A) //CHARACTERS ['A'..'Z']
			 ||(c >= 0x61 && c <= 0x7A) //CHARACTERS ['a'..'z']
			 /*||(c == 0x20)*/ //SPACE MUST BE ENCODED
			 ||(c == 0x2D) //HYPHEN
			 ||(c == 0x2E) //PERIOD
			 ||(c == 0x5F) //UNDERSCORE
			 ||(c == 0x7E) //TILDE
			) {
				sb.append(c);
			} else {
				sb.append(PERCENT_CHARACTER);
				sb.append(toHexDigits(c));
			}
		}
	
		return sb.toString();
	}
	
	private char[] toHexDigits(char c) {
		char[] hexDigit = new char[2];
		hexDigit[0] = HEXADECIMAL_DIGITS[(c & 0xF0) >>> 4];
		hexDigit[1] = HEXADECIMAL_DIGITS[(c & 0x0F)];
		
		return hexDigit;
	}
}
