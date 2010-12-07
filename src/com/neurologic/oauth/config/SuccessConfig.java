/**
 * 
 */
package com.neurologic.oauth.config;

import java.io.Serializable;

/**
 * @author Bienfait Sindi
 * @since 06 December 2010 
 *
 */
public class SuccessConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8865712216219334851L;
	private String path;

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
}
