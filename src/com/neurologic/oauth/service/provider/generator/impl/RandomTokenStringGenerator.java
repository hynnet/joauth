/**
 * 
 */
package com.neurologic.oauth.service.provider.generator.impl;

import java.security.SecureRandom;

import com.neurologic.oauth.service.provider.generator.TokenStringGenerator;

/**
 * @author Buhake Sindi
 * @since 29 July 2011
 *
 */
public class RandomTokenStringGenerator implements TokenStringGenerator {

	private final SecureRandom randomizer = new SecureRandom();
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.generator.TokenStringGenerator#generateToken()
	 */
	@Override
	public String generateToken() {
		// TODO Auto-generated method stub
		return generateToken(null);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.generator.TokenStringGenerator#generateToken(java.lang.String)
	 */
	@Override
	public String generateToken(String data) {
		// TODO Auto-generated method stub
		return Long.toHexString(randomizer.nextLong());
	}
}
