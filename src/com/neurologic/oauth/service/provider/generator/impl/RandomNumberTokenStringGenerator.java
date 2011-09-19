/**
 * 
 */
package com.neurologic.oauth.service.provider.generator.impl;

import com.neurologic.oauth.service.provider.generator.TokenStringGenerator;

/**
 * @author Buhake Sindi
 * @since 02 September 2011
 *
 */
public class RandomNumberTokenStringGenerator implements TokenStringGenerator {

	private static final int DEFAULT_STRING_LENGTH = 5;
	private int length;
	
	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.generator.TokenStringGenerator#generateToken()
	 */
	@Override
	public String generateToken() {
		// TODO Auto-generated method stub
		if (getLength() < 0) {
			setLength(DEFAULT_STRING_LENGTH);
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getLength(); i++) {
			sb.append(generateRandom(0, 9));
		}
		
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.generator.TokenStringGenerator#generateToken(byte[])
	 */
	@Override
	public String generateToken(byte[] data) {
		// TODO Auto-generated method stub
		return generateToken();
	}
	
	private int generateRandom(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
}
