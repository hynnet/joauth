/**
 * 
 */
package net.oauth.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.oauth.http.HttpClient;
import net.oauth.http.impl.ApacheHttpClient;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * @author Buhake Sindi
 * @since 11 October 2011
 *
 */
public class HttpClientUtil {

	private HttpClientUtil() {}
	
	public static HttpClient bypassAllSSL(org.apache.http.client.HttpClient base) throws KeyManagementException, NoSuchAlgorithmException {
		SSLContext context = SSLContext.getInstance("TLS");
		X509TrustManager trustManager = new X509TrustManager() {
			
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				// TODO Auto-generated method stub
				
			}
		};
		
		context.init(null, new TrustManager[] {trustManager}, null);
		SSLSocketFactory sslSocketFactory = new SSLSocketFactory(context, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		ClientConnectionManager clientConnectionManager = base.getConnectionManager();
		SchemeRegistry schemeRegistry = clientConnectionManager.getSchemeRegistry();
		schemeRegistry.register(new Scheme("https", 443, sslSocketFactory));
		return new ApacheHttpClient(new DefaultHttpClient(clientConnectionManager, base.getParams()));
	}
}
