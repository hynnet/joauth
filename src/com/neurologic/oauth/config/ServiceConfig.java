/**
 * 
 */
package com.neurologic.oauth.config;

import java.io.Serializable;

/**
 * @author Bienfait Sindi
 * @since 27 November 2010
 *
 */
public class ServiceConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1172982753650898515L;
	private String path;
	private String serviceClass;
	private String refOAuth;
	private SuccessConfig successConfig;

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the serviceClass
	 */
	public String getServiceClass() {
		return serviceClass;
	}

	/**
	 * @param serviceClass the serviceClass to set
	 */
	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}
	
	/**
	 * @return the refOAuth
	 */
	public String getRefOAuth() {
		return refOAuth;
	}

	/**
	 * @param refOAuth the refOAuth to set
	 */
	public void setRefOAuth(String refOAuth) {
		this.refOAuth = refOAuth;
	}

	/**
	 * @return the successConfig
	 */
	public SuccessConfig getSuccessConfig() {
		return successConfig;
	}

	/**
	 * @param successConfig the successConfig to set
	 */
	public void setSuccessConfig(SuccessConfig successConfig) {
		this.successConfig = successConfig;
	}
}
