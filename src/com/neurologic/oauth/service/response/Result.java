/**
 * 
 */
package com.neurologic.oauth.service.response;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Buhake Sindi
 * @since 08 October 2011
 *
 */
public interface Result {

	public void execute(ServletRequest request, ServletResponse response) throws IOException;
}
