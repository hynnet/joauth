/**
 * 
 */
package com.neurologic.oauth.store;

/**
 * @author Buhake Sindi
 * @since 11 August 2011
 *
 */
public interface EntityStoreManager {

	public void add(TokenSession session);
	public void remove(TokenSession session);
	public TokenSession find(String id);
}
