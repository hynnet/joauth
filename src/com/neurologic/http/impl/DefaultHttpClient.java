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
package com.neurologic.http.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import net.oauth.parameters.QueryKeyValuePair;
import net.oauth.util.OAuth1Util;

import com.neurologic.exception.HttpException;
import com.neurologic.http.AbstractHttpClient;
import com.neurologic.http.MessageHeader;

/**
 * @author Bienfait Sindi
 * @since 28 November 2010
 *
 */
public class DefaultHttpClient extends AbstractHttpClient {

	private URLConnection urlConnection;
	
	/**
	 * 
	 */
	public DefaultHttpClient() {
		// TODO Auto-generated constructor stub
		super();
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#getStatusCode()
	 */
	@Override
	public int getStatusCode() throws IOException {
		// TODO Auto-generated method stub
		if (urlConnection != null && urlConnection instanceof HttpURLConnection) {
			return ((HttpURLConnection)urlConnection).getResponseCode();
		}
		
		return -1;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#getStatusReason()
	 */
	@Override
	public String getStatusReason() throws IOException {
		// TODO Auto-generated method stub
		if (urlConnection instanceof HttpURLConnection) {
			return ((HttpURLConnection)urlConnection).getResponseMessage();
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#connect(java.lang.String, java.lang.String)
	 */
	@Override
	public synchronized InputStream connect(String requestMethod, String url) throws HttpException {
		// TODO Auto-generated method stub
		int questionMarkPos = url.indexOf('?'); 
		if (questionMarkPos > 0) {
			Map<String, String> retrievedParameters = OAuth1Util.parseQueryString(url.substring(questionMarkPos + 1));
			if (parameterMap == null) {
				parameterMap = new LinkedHashMap<String, String>();
			}
			parameterMap.putAll(retrievedParameters);
			url = url.substring(0, questionMarkPos);
		}
		
		String queryString = OAuth1Util.getQueryString(parameterMap, new QueryKeyValuePair());
		OutputStream output = null;
		
		try {
			String s = url;
			if ("GET".equals(requestMethod)) {
				s += ((questionMarkPos > 0) ? "&" : "?") + queryString;
			} else if ("POST".equals(requestMethod)) {
				if (getRequestHeaderValue("Content-Type") == null) {
					addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				}
				addRequestHeader("Content-Length", Integer.toString(queryString.getBytes("UTF-8").length));
			}
			
			urlConnection = new URL(s).openConnection();
			urlConnection.setDoInput(true);
			populateRequestProperty();
			
			if ("POST".equals(requestMethod)) {
				if (urlConnection instanceof HttpURLConnection) {
					((HttpURLConnection)urlConnection).setRequestMethod(requestMethod);
				}
				urlConnection.setDoOutput(true);
				output = urlConnection.getOutputStream();
				output.write(queryString.getBytes("UTF-8"));
				output.flush();
			}
			
			InputStream input = urlConnection.getInputStream();
			populateResponseHeaders();
			if (getStatusCode() != 200 && urlConnection instanceof HttpURLConnection) {
				input = ((HttpURLConnection)urlConnection).getErrorStream();
			}
			
			return input;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw new HttpException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new HttpException(e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(e.getLocalizedMessage(), e);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		if (urlConnection != null && urlConnection instanceof HttpURLConnection) {
			((HttpURLConnection)urlConnection).disconnect();
			urlConnection = null;
		}
	}
	
	private void populateRequestProperty() {
		if (urlConnection != null) {
			synchronized (requestHeaders) {
				for (MessageHeader mh: requestHeaders) {
					urlConnection.addRequestProperty(mh.getName(), mh.getValue());
				}
			}
		}
	}
	
	private void populateResponseHeaders() {
		if (urlConnection != null && urlConnection.getHeaderFields() != null) {
			if (responseHeaders == null) {
				responseHeaders = new ArrayList<MessageHeader>();
			} else {
				responseHeaders.clear();
			}
			
			synchronized (urlConnection.getHeaderFields().keySet()) {
				for (String key : urlConnection.getHeaderFields().keySet()) {
					responseHeaders.add(new MessageHeader(key, urlConnection.getHeaderField(key)));
				}
			}
		}
	}
}
