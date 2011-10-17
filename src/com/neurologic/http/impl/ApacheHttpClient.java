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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.oauth.parameters.QueryKeyValuePair;
import net.oauth.util.OAuth1Util;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.neurologic.exception.HttpException;
import com.neurologic.http.AbstractHttpClient;
import com.neurologic.http.MessageHeader;

/**
 * @author Bienfait Sindi
 * @since 28 November 2010
 *
 */
public class ApacheHttpClient extends AbstractHttpClient {

	private HttpClient httpClient;
	private HttpResponse httpResponse;
	
	/**
	 * 
	 */
	public ApacheHttpClient() {
		// TODO Auto-generated constructor stub
		this(new DefaultHttpClient());
	}

	/**
	 * @param httpClient
	 */
	public ApacheHttpClient(HttpClient httpClient) {
		super();
		this.httpClient = httpClient;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#getStatusCode()
	 */
	@Override
	public int getStatusCode() throws IOException {
		// TODO Auto-generated method stub
		if (httpResponse != null) {
			return httpResponse.getStatusLine().getStatusCode();
		}
		
		return -1;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#getStatusReason()
	 */
	@Override
	public String getStatusReason() throws IOException {
		// TODO Auto-generated method stub
		if (httpResponse != null) {
			return httpResponse.getStatusLine().getReasonPhrase();
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
		
		try {
			HttpRequestBase request = null;
			if ("GET".equals(requestMethod)) {
				request = createHttpGet(url);
			} else if ("POST".equals(requestMethod)) {
				request = createHttpPost(url);
			}
			
			if (request == null) {
				throw new HttpException("Couldn't translate Http request method '" + requestMethod + "'.");
			}
			
			transferHttpHeaders(request);
			httpResponse = httpClient.execute(request);
			populateResponseHeaders();
			return httpResponse.getEntity().getContent();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new HttpException(e);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			throw new HttpException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new HttpException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	private HttpGet createHttpGet(String url) {
		String queryString = OAuth1Util.getQueryString(parameterMap, new QueryKeyValuePair());
		return new HttpGet(url + "?" + queryString);
	}
	
	private HttpPost createHttpPost(String url) throws UnsupportedEncodingException {
		HttpPost post = new HttpPost(url);
		
		if (parameterMap != null && !parameterMap.isEmpty()) {
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			synchronized (parameterMap) {
				for (Entry<String, String> entry : parameterMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}

			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		}
		
		return post;
	}
	
	private void transferHttpHeaders(HttpRequestBase baseRequest) {
		if (requestHeaders != null && baseRequest != null) {
			synchronized (requestHeaders) {
				for (MessageHeader mh : requestHeaders) {
					baseRequest.setHeader(mh.getName(), mh.getValue());
				}
			}
		}
	}
	
	private void populateResponseHeaders() {
		if (httpResponse != null && httpResponse.getAllHeaders() != null) {
			if (responseHeaders == null) {
				responseHeaders = new ArrayList<MessageHeader>();
			} else {
				responseHeaders.clear();
			}
			
			for (Header header : httpResponse.getAllHeaders()) {
				responseHeaders.add(new MessageHeader(header.getName(), header.getValue()));
			}
		}
	}
}
