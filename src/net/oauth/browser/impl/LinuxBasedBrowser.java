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
 * @since 14 July 2011
 *
 */
public class LinuxBasedBrowser extends AbstractBrowser {

	private static final String[] BROWSERS = {"xdg-open", "sensible-browser", "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape"};
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.browser.Browser#browse(java.lang.String)
	 */
	@Override
	public void browse(String url) throws IOException {
		// TODO Auto-generated method stub
		String browser = null;
		
		try {
			for (int i = 0; i < BROWSERS.length && browser == null; i++) {
				if (Runtime.getRuntime().exec(new String[] {"which", BROWSERS[i]}).waitFor() == 0) {
					browser = BROWSERS[i];
				}
			}
			
			if (browser == null) {
				throw new IOException("No Unix/Linux based browser found.");
			}
			
			Runtime.getRuntime().exec(new String[] {browser, url}).waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			throw new IOException(e.getLocalizedMessage(), e);
		}
	}
}
