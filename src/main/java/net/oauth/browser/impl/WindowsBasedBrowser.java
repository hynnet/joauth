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
package net.oauth.browser.impl;

import java.io.IOException;

import net.oauth.browser.AbstractBrowser;

/**
 * @author Buhake Sindi
 * @since 15 July 2011
 *
 */
public class WindowsBasedBrowser extends AbstractBrowser {

	/* (non-Javadoc)
	 * @see net.oauth.browser.Browser#browse(java.lang.String)
	 */
	@Override
	public void browse(String url) throws IOException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("rundll32").append(" ").append("url.dll,FileProtocolHandler").append(" ").append(url);
		Runtime.getRuntime().exec(sb.toString());
	}
}
