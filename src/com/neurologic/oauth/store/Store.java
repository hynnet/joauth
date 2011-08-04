/**
 * 
 */
package com.neurologic.oauth.store;

import java.io.IOException;

/**
 * @author Buhake Sindi
 * @since 13 July 2011
 *
 */
public interface Store<T> {

	public int size() throws IOException;
	public String[] keys() throws IOException;
	public Entity<T> get(String id) throws IOException;
	public void remove(String id) throws IOException;
}
