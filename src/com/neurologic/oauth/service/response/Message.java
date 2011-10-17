/**
 * 
 */
package com.neurologic.oauth.service.response;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Buhake Sindi
 * @since 14 October 2011
 *
 */
public interface Message {

	public String getContentType();
	public int getContentLength();
	public byte[] getContent();
	public void writeTo(OutputStream output) throws IOException;
}
