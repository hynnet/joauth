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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.oauth.browser.AbstractBrowser;

/**
 * @author Buhake Sindi
 * @since 15 July 2011
 *
 */
public class MacBasedBrowser extends AbstractBrowser {

	/* (non-Javadoc)
	 * @see net.oauth.browser.Browser#browse(java.lang.String)
	 */
	@Override
	public void browse(String url) throws IOException {
		// TODO Auto-generated method stub
		try {
			Class<?> fileManagerClass = Class.forName("com.apple.eio.FileManager");
			if (fileManagerClass == null) {
				throw new IOException("No com.apple.eio.FileManager class found.");
			}
			
			Method openURLMethod = fileManagerClass.getDeclaredMethod("openURL", new Class<?>[] {String.class});
			openURLMethod.invoke(null, new Object[] {url});
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new IOException(e.getLocalizedMessage(), e);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			throw new IOException(e.getLocalizedMessage(), e);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			throw new IOException(e.getLocalizedMessage(), e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			throw new IOException(e.getLocalizedMessage(), e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new IOException(e.getLocalizedMessage(), e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new IOException(e.getLocalizedMessage(), e);
		}
	}
}
