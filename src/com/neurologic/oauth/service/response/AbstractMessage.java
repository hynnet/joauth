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
public abstract class AbstractMessage implements Message {

	protected static final String DEFAULT_HTTP_CHARSET = "ISO-8859-1";
	private String contentType;
	private byte[] content;
	
	/**
	 * @param contentType
	 */
	protected AbstractMessage() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.Message#getContentLength()
	 */
	@Override
	public int getContentLength() {
		// TODO Auto-generated method stub
		return (content == null) ? 0 : content.length;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.Message#getContentType()
	 */
	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	protected void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @param content the content to set
	 */
	protected void setContent(byte[] content) {
		this.content = content;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.Message#getContent()
	 */
	@Override
	public byte[] getContent() {
		// TODO Auto-generated method stub
		return content;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.response.Message#writeTo(java.io.OutputStream)
	 */
	@Override
	public void writeTo(OutputStream output) throws IOException {
		// TODO Auto-generated method stub
		if (content != null && output != null) {
			output.write(content);
			output.flush();
		}
		
	}
}
