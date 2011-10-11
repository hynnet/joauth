/**
 * 
 */
package com.neurologic.oauth.service.response;

import java.io.IOException;

/**
 * @author Buhake Sindi
 * @since 08 October 2011
 *
 */
public interface Result {

	public void execute(ServiceContext context) throws IOException;
}
