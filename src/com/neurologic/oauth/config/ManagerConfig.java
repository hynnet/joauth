/**
 * 
 */
package com.neurologic.oauth.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Buhake Sindi
 * @since 05 September 2011
 *
 */
public class ManagerConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7362878424713319369L;
	private String className;
	private String consumerKeyStoreName;
	private String requestTokenStoreName;
	private String accessTokenStoreName;
	private String usedNonceStoreName;
	private String requestTokenGeneratorClass;
	private String authVerifierGeneratorClass;
	private String accessTokenGeneratorClass;
	private String tokenSecretGeneratorClass;
	private long requestTokenValidity;
	private long authorizedTokenValidity;
	private long accessTokenValidity;
	private long usedNonceTokenValidity;
	private int tokenSecretLength;
	
	private List<StoreConfig> storeConfigList;
	
	public void addStoreConfig(StoreConfig storeConfig) {
		if (storeConfigList == null) {
			storeConfigList = new ArrayList<StoreConfig>();
		}
		
		if (storeConfig != null) {
			storeConfigList.add(storeConfig);
		}
	}
	
	public StoreConfig getStoreConfig(String storeConfigName) {
		if (storeConfigList != null) {
			for (StoreConfig storeConfig : storeConfigList) {
				if (storeConfig.getName().equals(storeConfigName)) {
					return storeConfig;
				}
			}
		}
		
		return null;
	}
	
	public boolean storeConfigExists(String storeConfigName) {
		return getStoreConfig(storeConfigName) != null;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the consumerKeyStoreName
	 */
	public String getConsumerKeyStoreName() {
		return consumerKeyStoreName;
	}

	/**
	 * @param consumerKeyStoreName the consumerKeyStoreName to set
	 */
	public void setConsumerKeyStoreName(String consumerKeyStoreName) {
		this.consumerKeyStoreName = consumerKeyStoreName;
	}

	/**
	 * @return the requestTokenStoreName
	 */
	public String getRequestTokenStoreName() {
		return requestTokenStoreName;
	}

	/**
	 * @param requestTokenStoreName the requestTokenStoreName to set
	 */
	public void setRequestTokenStoreName(String requestTokenStoreName) {
		this.requestTokenStoreName = requestTokenStoreName;
	}

	/**
	 * @return the accessTokenStoreName
	 */
	public String getAccessTokenStoreName() {
		return accessTokenStoreName;
	}

	/**
	 * @param accessTokenStoreName the accessTokenStoreName to set
	 */
	public void setAccessTokenStoreName(String accessTokenStoreName) {
		this.accessTokenStoreName = accessTokenStoreName;
	}

	/**
	 * @return the usedNonceStoreName
	 */
	public String getUsedNonceStoreName() {
		return usedNonceStoreName;
	}

	/**
	 * @param usedNonceStoreName the usedNonceStoreName to set
	 */
	public void setUsedNonceStoreName(String usedNonceStoreName) {
		this.usedNonceStoreName = usedNonceStoreName;
	}

	/**
	 * @return the requestTokenGeneratorClass
	 */
	public String getRequestTokenGeneratorClass() {
		return requestTokenGeneratorClass;
	}

	/**
	 * @param requestTokenGeneratorClass the requestTokenGeneratorClass to set
	 */
	public void setRequestTokenGeneratorClass(String requestTokenGeneratorClass) {
		this.requestTokenGeneratorClass = requestTokenGeneratorClass;
	}

	/**
	 * @return the authVerifierGeneratorClass
	 */
	public String getAuthVerifierGeneratorClass() {
		return authVerifierGeneratorClass;
	}

	/**
	 * @param authVerifierGeneratorClass the authVerifierGeneratorClass to set
	 */
	public void setAuthVerifierGeneratorClass(String authVerifierGeneratorClass) {
		this.authVerifierGeneratorClass = authVerifierGeneratorClass;
	}

	/**
	 * @return the accessTokenGeneratorClass
	 */
	public String getAccessTokenGeneratorClass() {
		return accessTokenGeneratorClass;
	}

	/**
	 * @param accessTokenGeneratorClass the accessTokenGeneratorClass to set
	 */
	public void setAccessTokenGeneratorClass(String accessTokenGeneratorClass) {
		this.accessTokenGeneratorClass = accessTokenGeneratorClass;
	}

	/**
	 * @return the tokenSecretGeneratorClass
	 */
	public String getTokenSecretGeneratorClass() {
		return tokenSecretGeneratorClass;
	}

	/**
	 * @param tokenSecretGeneratorClass the tokenSecretGeneratorClass to set
	 */
	public void setTokenSecretGeneratorClass(String tokenSecretGeneratorClass) {
		this.tokenSecretGeneratorClass = tokenSecretGeneratorClass;
	}

	/**
	 * @return the requestTokenValidity
	 */
	public long getRequestTokenValidity() {
		return requestTokenValidity;
	}

	/**
	 * @param requestTokenValidity the requestTokenValidity to set
	 */
	public void setRequestTokenValidity(long requestTokenValidity) {
		this.requestTokenValidity = requestTokenValidity;
	}

	/**
	 * @return the authorizedTokenValidity
	 */
	public long getAuthorizedTokenValidity() {
		return authorizedTokenValidity;
	}

	/**
	 * @param authorizedTokenValidity the authorizedTokenValidity to set
	 */
	public void setAuthorizedTokenValidity(long authorizedTokenValidity) {
		this.authorizedTokenValidity = authorizedTokenValidity;
	}

	/**
	 * @return the accessTokenValidity
	 */
	public long getAccessTokenValidity() {
		return accessTokenValidity;
	}

	/**
	 * @param accessTokenValidity the accessTokenValidity to set
	 */
	public void setAccessTokenValidity(long accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	/**
	 * @return the usedNonceTokenValidity
	 */
	public long getUsedNonceTokenValidity() {
		return usedNonceTokenValidity;
	}

	/**
	 * @param usedNonceTokenValidity the usedNonceTokenValidity to set
	 */
	public void setUsedNonceTokenValidity(long usedNonceTokenValidity) {
		this.usedNonceTokenValidity = usedNonceTokenValidity;
	}

	/**
	 * @return the tokenSecretLength
	 */
	public int getTokenSecretLength() {
		return tokenSecretLength;
	}

	/**
	 * @param tokenSecretLength the tokenSecretLength to set
	 */
	public void setTokenSecretLength(int tokenSecretLength) {
		this.tokenSecretLength = tokenSecretLength;
	}

	/**
	 * @return the storeConfigList
	 */
	public List<StoreConfig> getStoreConfigList() {
		return storeConfigList;
	}

	/**
	 * @param storeConfigList the storeConfigList to set
	 */
	public void setStoreConfigList(List<StoreConfig> storeConfigList) {
		this.storeConfigList = storeConfigList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((accessTokenGeneratorClass == null) ? 0
						: accessTokenGeneratorClass.hashCode());
		result = prime
				* result
				+ ((accessTokenStoreName == null) ? 0 : accessTokenStoreName
						.hashCode());
		result = prime * result
				+ (int) (accessTokenValidity ^ (accessTokenValidity >>> 32));
		result = prime
				* result
				+ ((authVerifierGeneratorClass == null) ? 0
						: authVerifierGeneratorClass.hashCode());
		result = prime
				* result
				+ (int) (authorizedTokenValidity ^ (authorizedTokenValidity >>> 32));
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime
				* result
				+ ((consumerKeyStoreName == null) ? 0 : consumerKeyStoreName
						.hashCode());
		result = prime
				* result
				+ ((requestTokenGeneratorClass == null) ? 0
						: requestTokenGeneratorClass.hashCode());
		result = prime
				* result
				+ ((requestTokenStoreName == null) ? 0 : requestTokenStoreName
						.hashCode());
		result = prime * result
				+ (int) (requestTokenValidity ^ (requestTokenValidity >>> 32));
		result = prime * result
				+ ((storeConfigList == null) ? 0 : storeConfigList.hashCode());
		result = prime
				* result
				+ ((tokenSecretGeneratorClass == null) ? 0
						: tokenSecretGeneratorClass.hashCode());
		result = prime * result + tokenSecretLength;
		result = prime
				* result
				+ ((usedNonceStoreName == null) ? 0 : usedNonceStoreName
						.hashCode());
		result = prime
				* result
				+ (int) (usedNonceTokenValidity ^ (usedNonceTokenValidity >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManagerConfig other = (ManagerConfig) obj;
		if (accessTokenGeneratorClass == null) {
			if (other.accessTokenGeneratorClass != null)
				return false;
		} else if (!accessTokenGeneratorClass
				.equals(other.accessTokenGeneratorClass))
			return false;
		if (accessTokenStoreName == null) {
			if (other.accessTokenStoreName != null)
				return false;
		} else if (!accessTokenStoreName.equals(other.accessTokenStoreName))
			return false;
		if (accessTokenValidity != other.accessTokenValidity)
			return false;
		if (authVerifierGeneratorClass == null) {
			if (other.authVerifierGeneratorClass != null)
				return false;
		} else if (!authVerifierGeneratorClass
				.equals(other.authVerifierGeneratorClass))
			return false;
		if (authorizedTokenValidity != other.authorizedTokenValidity)
			return false;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (consumerKeyStoreName == null) {
			if (other.consumerKeyStoreName != null)
				return false;
		} else if (!consumerKeyStoreName.equals(other.consumerKeyStoreName))
			return false;
		if (requestTokenGeneratorClass == null) {
			if (other.requestTokenGeneratorClass != null)
				return false;
		} else if (!requestTokenGeneratorClass
				.equals(other.requestTokenGeneratorClass))
			return false;
		if (requestTokenStoreName == null) {
			if (other.requestTokenStoreName != null)
				return false;
		} else if (!requestTokenStoreName.equals(other.requestTokenStoreName))
			return false;
		if (requestTokenValidity != other.requestTokenValidity)
			return false;
		if (storeConfigList == null) {
			if (other.storeConfigList != null)
				return false;
		} else if (!storeConfigList.equals(other.storeConfigList))
			return false;
		if (tokenSecretGeneratorClass == null) {
			if (other.tokenSecretGeneratorClass != null)
				return false;
		} else if (!tokenSecretGeneratorClass
				.equals(other.tokenSecretGeneratorClass))
			return false;
		if (tokenSecretLength != other.tokenSecretLength)
			return false;
		if (usedNonceStoreName == null) {
			if (other.usedNonceStoreName != null)
				return false;
		} else if (!usedNonceStoreName.equals(other.usedNonceStoreName))
			return false;
		if (usedNonceTokenValidity != other.usedNonceTokenValidity)
			return false;
		return true;
	}
}
