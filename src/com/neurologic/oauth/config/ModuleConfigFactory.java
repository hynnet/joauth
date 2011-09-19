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
package com.neurologic.oauth.config;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.neurologic.oauth.util.ValueConverterUtil;

/**
 * Uses {@code Builder}
 * @author Bienfait Sindi
 * @since 27 November 2010
 * 
 *
 */
public class ModuleConfigFactory {

	public ModuleConfig createModuleConfig(InputStream input) throws Exception {
		ModuleConfig config = null;
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource iSource = new InputSource(input);
			Element rootElement = builder.parse(iSource).getDocumentElement();
			
			if (!"oauth-config".equals(rootElement.getTagName())) {
				throw new Exception("Unknown tag name '" + rootElement.getTagName() + "'. Expected 'oauth-config'.");
			}
			
			config = new ModuleConfig();
			NodeList oauthList = rootElement.getElementsByTagName("oauth");
			if (oauthList != null) {
				for (int i = 0; i < oauthList.getLength(); i++) {
					OAuthConfig oauthConfig = createOAuthConfig((Element) oauthList.item(i));
					if (config.oauthConfigExists(oauthConfig.getName())) {
						throw new Exception("Duplicate oauth element with name=\"" + oauthConfig.getName() + "\".");
					}
					config.addOAuthConfig(oauthConfig);
				}
			}
			
			NodeList serviceList = rootElement.getElementsByTagName("service");
			if (serviceList != null) {
				for (int i = 0; i < serviceList.getLength(); i++) {
					ServiceConfig sConfig = createServiceConfig((Element) serviceList.item(i));
					if (config.serviceConfigExists(sConfig.getPath())) {
						throw new Exception("Duplicate service element with path=\"" + sConfig.getPath() + "\".");
					}
					config.addServiceConfig(sConfig);
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		return config;
	}
	
	private OAuthConfig createOAuthConfig(Element element) throws Exception {
		OAuthConfig config = new OAuthConfig();
		config.setName(element.getAttribute("name"));
		config.setVersion(Integer.parseInt(element.getAttribute("version")));
		config.setProvider(Boolean.parseBoolean(element.getAttribute("provider")));
		
		NodeList nodeList = element.getElementsByTagName("consumer");
		if (nodeList != null && nodeList.getLength() > 0) {
			Element consumer = (Element) nodeList.item(0);
			config.setConsumerConfig(new ConsumerConfig());
			config.getConsumerConfig().setKey(consumer.getAttribute("key"));
			config.getConsumerConfig().setSecret(consumer.getAttribute("secret"));
		}
		
		nodeList = element.getElementsByTagName("provider");
		if (nodeList != null && nodeList.getLength() > 0) {
			Element provider = (Element) nodeList.item(0);
			config.setProviderConfig(new ProviderConfig());
			config.getProviderConfig().setRequestTokenUrl(provider.getAttribute("requestTokenUrl"));
			config.getProviderConfig().setAuthorizationUrl(provider.getAttribute("authorizationUrl"));
			config.getProviderConfig().setAccessTokenUrl(provider.getAttribute("accessTokenUrl"));
			config.getProviderConfig().setClassName(provider.getAttribute("class"));
		}
		
		nodeList = element.getElementsByTagName("manager");
		if (nodeList != null && nodeList.getLength() > 0) {
			Element manager = (Element) nodeList.item(0);
			config.setManagerConfig(new ManagerConfig());
			config.getManagerConfig().setClassName(manager.getAttribute("class"));
			config.getManagerConfig().setConsumerKeyStoreName(manager.getAttribute("consumerKeyStoreName"));
			config.getManagerConfig().setRequestTokenStoreName(manager.getAttribute("requestTokenStoreName"));
			config.getManagerConfig().setAccessTokenStoreName(manager.getAttribute("accessTokenStoreName"));
			config.getManagerConfig().setUsedNonceStoreName(manager.getAttribute("usedNonceStoreName"));
			config.getManagerConfig().setRequestTokenGeneratorClass(manager.getAttribute("requestTokenGeneratorClass"));
			config.getManagerConfig().setAuthVerifierGeneratorClass(manager.getAttribute("authVerifierGeneratorClass"));
			config.getManagerConfig().setAccessTokenGeneratorClass(manager.getAttribute("accessTokenGeneratorClass"));
			config.getManagerConfig().setTokenSecretGeneratorClass(manager.getAttribute("tokenSecretGeneratorClass"));
			config.getManagerConfig().setRequestTokenValidity(ValueConverterUtil.convert(manager.getAttribute("requestTokenValidity"), -1));
			config.getManagerConfig().setAuthorizedTokenValidity(ValueConverterUtil.convert(manager.getAttribute("authorizedTokenValidity"), -1));
			config.getManagerConfig().setAccessTokenValidity(ValueConverterUtil.convert(manager.getAttribute("accessTokenValidity"), -1));
			config.getManagerConfig().setUsedNonceTokenValidity(ValueConverterUtil.convert(manager.getAttribute("usedNonceTokenValidity"), -1));
			config.getManagerConfig().setTokenSecretLength(ValueConverterUtil.convert(manager.getAttribute("tokenSecretLength"), -1));
			
			nodeList = manager.getElementsByTagName("store");
			if (nodeList != null && nodeList.getLength() > 0) {
				for (int i = 0; i < nodeList.getLength(); i++) {
					Element store = (Element) nodeList.item(i);
					StoreConfig storeConfig = createStoreConfig(store);
					
					if (storeConfig != null) { //Rare....
						if (config.getManagerConfig().storeConfigExists(storeConfig.getName())) {
							throw new Exception("Element 'store' contains name '" + storeConfig.getName() + "'.");
						}
						
						//Add it in.
						config.getManagerConfig().addStoreConfig(storeConfig);
					}
				}
			}
		}
		
		return config;
	}
	
	private StoreConfig createStoreConfig(Element element) {
		StoreConfig config = new StoreConfig();
		config.setName(element.getAttribute("name"));
		config.setClassName(element.getAttribute("class"));
		config.setDriverName(element.getAttribute("driveName"));
		config.setConnectionUrl(element.getAttribute("connectionUrl"));
		config.setUserName(element.getAttribute("userName"));
		config.setPassword(element.getAttribute("password"));
		config.setDataSourceName(element.getAttribute("dataSourceName"));
		config.setDirectory(element.getAttribute("directory"));
		config.setFileName(element.getAttribute("fileName"));
		
		return config;
	}
	
	private ServiceConfig createServiceConfig(Element element) {
		ServiceConfig config = new ServiceConfig();
		config.setPath(element.getAttribute("path"));
		config.setServiceClass(element.getAttribute("class"));
		config.setRefOAuth(element.getAttribute("oauth"));
		
		NodeList nodeList = element.getElementsByTagName("success");
		if (nodeList != null && nodeList.getLength() > 0) {
			config.setSuccessConfig(createSuccessConfig((Element) nodeList.item(0)));
		}
		
		nodeList = element.getElementsByTagName("login-redirect");
		if (nodeList != null && nodeList.getLength() > 0) {
			config.setLoginRedirectConfig(createLoginRedirectConfig((Element) nodeList.item(0)));
		}
		
		nodeList = element.getElementsByTagName("error-redirect");
		if (nodeList != null && nodeList.getLength() > 0) {
			config.setErrorRedirectConfig(createErrorRedirectConfig((Element) nodeList.item(0)));
		}
		
		return config;
	}
	
	private SuccessConfig createSuccessConfig(Element element) {
		SuccessConfig config = new SuccessConfig();
		config.setPath(element.getAttribute("path"));
		
		return config;
	}
	
	private LoginRedirectConfig createLoginRedirectConfig(Element element) {
		LoginRedirectConfig config = new LoginRedirectConfig();
		config.setPath(element.getAttribute("path"));
		
		return config;
	}
	
	private ErrorRedirectConfig createErrorRedirectConfig(Element element) {
		ErrorRedirectConfig config = new ErrorRedirectConfig();
		config.setPath(element.getAttribute("path"));
		
		return config;
	}
}
