/**
 * 
 */
package com.neurologic.http;

import java.io.IOException;
import java.io.InputStream;

import com.neurologic.exception.HttpException;

/**
 * @author Bienfait Sindi
 * @since 28 November 2010
 *
 */
public interface HttpClient {

	public void addRequestHeader(String name, String value);
	public String getRequestHeaderValue(String name);
	public String removeRequestHeader(String name);
	public void addResponseHeader(String name, String value);
	public String getResponseHeaderValue(String name);
	public String removeResponseHeader(String name);
	public void addParameter(String name, String value);
	public String getParameter(String name);
	public String removeParameter(String name);
	public int getStatusCode() throws IOException;
	public String getStatusReason() throws IOException ;
	public InputStream connect(String requestMethod, String url) throws HttpException;
	public void close();
}
