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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author Bienfait Sindi
 * @since 08 December 2009
 */
public class OAuthSignatureUtil {

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	/**
	 * Computes RFC 2104-compliant HMAC signature.
	 * 
	 * @param data
	 *            - the data to be signed.
	 * @param key
	 *            - the signing key.
	 * @return The Base64-encoded RFC 2104-compliant HMAC signature.
	 * @throws java.security.SignatureException
	 *             when signature generation fails
	 */
	public static String generateHmacSHA1Signature(String data, String key)	throws GeneralSecurityException {
		byte[] hmacData = null;

		try {
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), OAuthSignatureUtil.HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(OAuthSignatureUtil.HMAC_SHA1_ALGORITHM);
			mac.init(secretKey);
			hmacData = mac.doFinal(data.getBytes("UTF-8"));
			return new BASE64Encoder().encode(hmacData);
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
			throw new GeneralSecurityException(e);
		}
	}

	/**
	 * Computes RSA-SHA1 signature.
	 * 
	 * @param data
	 *            - the data to be signed.
	 * @param key
	 *            - the signing key.
	 * @return The Base64-encoded RFC 2104-compliant HMAC signature.
	 * @throws java.security.SignatureException
	 *             when signature generation fails
	 */
	public static String generateRSASHA1Signature(PrivateKey privateKey, String data) throws GeneralSecurityException {
		byte[] rsaSha1Data = null;

		try {
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(privateKey);
			signature.update(data.getBytes("UTF-8"));
			rsaSha1Data = signature.sign();
			return new BASE64Encoder().encode(rsaSha1Data);
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
			throw new GeneralSecurityException(e);
		}
	}

	public static PrivateKey getPrivateKey(String privateKeyFileName) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		File privateKeyFile = new File(privateKeyFileName);
		FileInputStream fis = new FileInputStream(privateKeyFile);
		DataInputStream dis = new DataInputStream(fis);
		byte[] privateKeyBytes = null;
		
		try {
			privateKeyBytes = new byte[(int) privateKeyFile.length()];
			dis.read(privateKeyBytes);
		} finally {
			dis.close();
			fis.close();
		}
		
		String BEGIN = "-----BEGIN PRIVATE KEY-----";
		String END = "-----END PRIVATE KEY-----";
		String str = new String(privateKeyBytes);
		if (str.contains(BEGIN) && str.contains(END)) {
			str = str.substring(BEGIN.length(), str.lastIndexOf(END));
		}

		KeyFactory fac = KeyFactory.getInstance("RSA");
		EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(str));
		return fac.generatePrivate(privKeySpec);
	}
}
