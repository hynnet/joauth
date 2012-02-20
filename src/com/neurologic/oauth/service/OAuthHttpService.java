/**
 * 
 */
package com.neurologic.oauth.service;


/**
 * @author Buhake Sindi
 * @since 18 November 2011
 *
 */
public abstract class OAuthHttpService extends HttpService {

//	/**
//	 * Checks if the Servlet Request was done through a secure channel.
//	 * @param request
//	 * @param forceCheck if <code>true</code>, then checks if https scheme and X509 certificates exists in the 
//	 *        {@link ServletRequest}.
//	 * @return
//	 */
//	protected boolean isSecure(ServletRequest request, boolean forceCheck) {
//		if (!forceCheck) {
//			return request.isSecure();
//		}
//		
//		X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
////		X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.net.ssl.peer_certificates");
//		return ("https".equals(request.getScheme()) && certs != null);
//	}
//	
}
