/**
 * 
 */
package com.neurologic.oauth.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.exception.OAuthException;

/**
 * @author Bienfait Sindi
 * @since 27 November 2010
 *
 */
public interface OAuthService<T> {

	public void setOAuthConsumer(T consumer);
	public void execute(HttpServletRequest request, HttpServletResponse response) throws OAuthException;
}
