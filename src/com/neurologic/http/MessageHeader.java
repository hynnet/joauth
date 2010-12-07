/**
 * 
 */
package com.neurologic.http;

import java.io.Serializable;

/**
 * @author Bienfait Sindi
 * @since 28 November 2010
 *
 */
public class MessageHeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6673678913990714767L;
	private String name;
	private String value;
	
	/**
	 * @param name
	 * @param value
	 */
	public MessageHeader(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
