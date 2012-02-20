/**
 * 
 */
package com.neurologic.oauth.service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Buhake Sindi
 * @since 08 October 2011
 *
 */
public interface Service {

	public void setOAuthName(String oauthName);
	public void execute(ServletRequest request, ServletResponse response) throws Exception;
}
