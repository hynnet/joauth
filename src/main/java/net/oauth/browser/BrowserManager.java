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
package net.oauth.browser;

import net.oauth.browser.impl.DefaultBasedBrowser;
import net.oauth.browser.impl.LinuxBasedBrowser;
import net.oauth.browser.impl.MacBasedBrowser;
import net.oauth.browser.impl.WindowsBasedBrowser;

/**
 * @author Buhake Sindi
 * @since 15 July 2011
 *
 */
public class BrowserManager {

	private static BrowserManager instance;
	
	/**
	 * 
	 */
	private BrowserManager() {
		//NOOP
	}

	/**
	 * @return the instance
	 */
	public static BrowserManager getInstance() {
		if (instance == null) {
			instance = new BrowserManager();
		}
		
		return instance;
	}
	
	
	public Browser getBrowser() {
		String osName = System.getProperty("os.name");
		
		if (osName.startsWith("Windows")) {
			return new WindowsBasedBrowser();
		}
		
		if (osName.startsWith("Linux")) {
			return new LinuxBasedBrowser();
		}
		
		if (osName.startsWith("Mac OS")) {
			return new MacBasedBrowser();
		}
		
		//return default
		return new DefaultBasedBrowser();
	}
}
