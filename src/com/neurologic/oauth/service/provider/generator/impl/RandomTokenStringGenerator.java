/**
 * 
 */
package com.neurologic.oauth.service.provider.generator.impl;

import java.security.SecureRandom;

import sun.misc.BASE64Encoder;

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
		synchronized (this) {
			return Long.toHexString(randomizer.nextLong());
		}
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.generator.TokenStringGenerator#generateToken(byte[])
	 */
	@Override
	public String generateToken(byte[] data) {
		// TODO Auto-generated method stub
		synchronized (this) {
			randomizer.nextBytes(data);
			return new BASE64Encoder().encode(data);
		}
	}
}
