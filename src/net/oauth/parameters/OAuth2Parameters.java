/**
 * 
 */
package net.oauth.parameters;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Bienfait Sindi
 * @since 24 July 2010
 *
 */
public class OAuth2Parameters {

	public static final String ACCESS_TOKEN = "access_token";
	public static final String CLIENT_ID = "client_id";
	public static final String CODE = "code";
	public static final String REDIRECT_URI = "redirect_uri";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String ASSERTION_TYPE = "assertion_type";
	public static final String ASSERTION = "assertion";
	public static final String REFRESH_TOKEN = "refresh_token";
	public static final String SCOPE = "scope";
	
	private Map<String, String> parameterMap;
//	private String code;
//	private String redirectUri;
//	private String userName;
//	private String password;
//	private String assertionType;
//	private String assertion;
//	private String refreshToken;
	
	/**
	 * Default Constructor
	 */
	public OAuth2Parameters() {
		// TODO Auto-generated constructor stub
		parameterMap = new HashMap<String, String>();
	}
	
	public void addParameterValue(String name, String value) {
		if (name != null && !name.isEmpty() && value != null && !value.isEmpty()) {
			parameterMap.put(name, value);
		}
	}
	
	public synchronized String[] getParameterNames() {
		String[] parameterNames = null;
		
		if (parameterMap != null && !parameterMap.isEmpty()) {
			parameterNames = new String[parameterMap.keySet().size()];
			int i = 0;
			                            
			Iterator<String> iter = parameterMap.keySet().iterator();
			while (iter.hasNext()) {
				parameterNames[i++] = iter.next();
			}
		}
			
		return parameterNames;
	}
	
	public void removeParameter(String name) {
		if (name != null && !name.isEmpty()) {
			parameterMap.remove(name);
		}
	}
	
	public boolean isEmpty() {
		return (parameterMap == null || parameterMap.isEmpty());
	}
	
	public String getParameterValue(String name) {
		return parameterMap.get(name);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return getParameterValue(CODE);
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		addParameterValue(CODE, code);
	}

	/**
	 * @return the redirectUri
	 */
	public String getRedirectUri() {
		return getParameterValue(REDIRECT_URI);
	}

	/**
	 * @param redirectUri the redirectUri to set
	 */
	public void setRedirectUri(String redirectUri) {
		addParameterValue(REDIRECT_URI, redirectUri);
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return getParameterValue(USERNAME);
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		addParameterValue(USERNAME, userName);
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return getParameterValue(PASSWORD);
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		addParameterValue(PASSWORD, password);
	}

	/**
	 * @return the assertionType
	 */
	public String getAssertionType() {
		return getParameterValue(ASSERTION_TYPE);
	}

	/**
	 * @param assertionType the assertionType to set
	 */
	public void setAssertionType(String assertionType) {
		addParameterValue(ASSERTION_TYPE, assertionType);
	}

	/**
	 * @return the assertion
	 */
	public String getAssertion() {
		return getParameterValue(ASSERTION);
	}

	/**
	 * @param assertion the assertion to set
	 */
	public void setAssertion(String assertion) {
		addParameterValue(ASSERTION, assertion);
	}

	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return getParameterValue(REFRESH_TOKEN);
	}

	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		addParameterValue(REFRESH_TOKEN, refreshToken);
	}
}
