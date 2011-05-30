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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bienfait Sindi
 * @since 28 November 2010
 *
 */
public abstract class AbstractHttpClient implements HttpClient {
	
	protected List<MessageHeader> requestHeaders;
	protected List<MessageHeader> responseHeaders;
	protected Map<String, String> parameterMap;

	/**
	 * 
	 */
	protected AbstractHttpClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#addRequestHeader(java.lang.String, java.lang.String)
	 */
	@Override
	public void addRequestHeader(String name, String value) {
		// TODO Auto-generated method stub
		if (requestHeaders == null) {
			requestHeaders = new ArrayList<MessageHeader>();
		}
		
		removeRequestHeader(name);
		requestHeaders.add(new MessageHeader(name, value));
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#getRequestHeaderValue(java.lang.String)
	 */
	@Override
	public String getRequestHeaderValue(String name) {
		// TODO Auto-generated method stub
		if (requestHeaders != null) {
			Iterator<MessageHeader> iter = requestHeaders.iterator();
			synchronized (iter) {
				while (iter.hasNext()) {
					MessageHeader mh = iter.next();
					if (name.equals(mh.getName())) {
						return mh.getValue();
					}
				}
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#removeRequestHeader(java.lang.String)
	 */
	@Override
	public String removeRequestHeader(String name) {
		// TODO Auto-generated method stub
		if (requestHeaders != null) {
			Iterator<MessageHeader> iter = requestHeaders.iterator();
			synchronized (iter) {
				while (iter.hasNext()) {
					MessageHeader mh = iter.next();
					if (name.equals(mh.getName())) {
						iter.remove();
						return mh.getValue();
					}
				}
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#addResponseHeader(java.lang.String, java.lang.String)
	 */
	@Override
	public void addResponseHeader(String name, String value) {
		// TODO Auto-generated method stub
		if (responseHeaders == null) {
			responseHeaders = new ArrayList<MessageHeader>();
		}
		
		removeResponseHeader(name);
		responseHeaders.add(new MessageHeader(name, value));
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#getResponseHeaderValue(java.lang.String)
	 */
	@Override
	public String getResponseHeaderValue(String name) {
		// TODO Auto-generated method stub
		if (responseHeaders != null) {
			Iterator<MessageHeader> iter = responseHeaders.iterator();
			synchronized (iter) {
				while (iter.hasNext()) {
					MessageHeader mh = iter.next();
					if (name.equals(mh.getName())) {
						return mh.getValue();
					}
				}
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#removeResponseHeader(java.lang.String)
	 */
	@Override
	public String removeResponseHeader(String name) {
		// TODO Auto-generated method stub
		if (responseHeaders != null) {
			Iterator<MessageHeader> iter = responseHeaders.iterator();
			synchronized (iter) {
				while (iter.hasNext()) {
					MessageHeader mh = iter.next();
					if (name.equals(mh.getName())) {
						iter.remove();
						return mh.getValue();
					}
				}
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#addParameter(java.lang.String, java.lang.String)
	 */
	@Override
	public void addParameter(String name, String value) {
		// TODO Auto-generated method stub
		if (parameterMap == null) {
			parameterMap = new LinkedHashMap<String, String>();
		}
		
		if (parameterMap.containsKey(name)) {
			removeParameter(name);
		}
		parameterMap.put(name, value);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		if (parameterMap != null) {
			return parameterMap.get(name);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.http.HttpClient#removeParameter(java.lang.String)
	 */
	@Override
	public String removeParameter(String name) {
		// TODO Auto-generated method stub
		if (parameterMap != null) {
			return parameterMap.remove(name);
		}
		
		return null;
	}
}
