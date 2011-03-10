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
