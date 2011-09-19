/**
 * 
 */
package com.neurologic.oauth.service.provider.response;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Buhake Sindi
 * @since 19 September 2011
 *
 */
public interface RedirectResult {

	public void sendRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
