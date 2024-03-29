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
