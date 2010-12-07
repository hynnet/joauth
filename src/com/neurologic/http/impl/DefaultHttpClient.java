/**
 * 
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import net.oauth.parameters.QueryKeyValuePair;
import net.oauth.util.OAuthUtil;

import org.apache.log4j.Logger;

import com.neurologic.exception.HttpException;
import com.neurologic.http.AbstractHttpClient;
import com.neurologic.http.MessageHeader;

/**
 * @author Bienfait Sindi
 * @since 28 November 2010
 *
 */
public class DefaultHttpClient extends AbstractHttpClient {

	private static final Logger logger = Logger.getLogger(DefaultHttpClient.class);
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
			Map<String, String> retrievedParameters = OAuthUtil.parseQueryString(url.substring(questionMarkPos + 1));
			if (parameterMap == null) {
				parameterMap = new LinkedHashMap<String, String>();
			}
			parameterMap.putAll(retrievedParameters);
			url = url.substring(0, questionMarkPos);
		}
		
		String queryString = OAuthUtil.getQueryString(parameterMap, new QueryKeyValuePair());
		OutputStream output = null;
		
		try {
			if ("GET".equals(requestMethod)) {
				urlConnection = new URL(url + ((questionMarkPos > 0) ? "&" : "?") + queryString).openConnection();
			} else if ("POST".equals(requestMethod)) {
				urlConnection = new URL(url).openConnection();
				if (getRequestHeaderValue("Content-Type") == null) {
					addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				}
				addRequestHeader("Content-Length", Integer.toString(queryString.getBytes("UTF-8").length));
			}
			urlConnection.setDoInput(true);
			populateRequestProperty();
			urlConnection.addRequestProperty("Method", requestMethod);
			
			if ("POST".equals(requestMethod)) {
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
			Iterator<MessageHeader> iter = requestHeaders.iterator();
			synchronized (iter) {
				while (iter.hasNext()) {
					MessageHeader mh = iter.next();
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
			
			Iterator<String> iter = urlConnection.getHeaderFields().keySet().iterator();
			synchronized (iter) {
				while (iter.hasNext()) {
					String key = iter.next();
					responseHeaders.add(new MessageHeader(key, urlConnection.getHeaderField(key)));
				}
			}
		}
	}
}
