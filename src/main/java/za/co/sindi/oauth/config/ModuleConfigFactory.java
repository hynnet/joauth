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
package za.co.sindi.oauth.config;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Bienfait Sindi
 * @since 27 November 2010
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
	
	private OAuthConfig createOAuthConfig(Element element) {
		OAuthConfig config = new OAuthConfig();
		config.setName(element.getAttribute("name"));
		config.setVersion(Integer.parseInt(element.getAttribute("version")));
		
		NodeList consumerList = element.getElementsByTagName("consumer");
		if (consumerList != null) {
			Element consumer = (Element) consumerList.item(0);
			config.setConsumer(new ConsumerConfig());
			config.getConsumer().setKey(consumer.getAttribute("key"));
			config.getConsumer().setSecret(consumer.getAttribute("secret"));
		}
		
		NodeList providerList = element.getElementsByTagName("provider");
		if (providerList != null) {
			Element provider = (Element) providerList.item(0);
			config.setProvider(new ProviderConfig());
			config.getProvider().setRequestTokenUrl(provider.getAttribute("requestTokenUrl"));
			config.getProvider().setAuthorizationUrl(provider.getAttribute("authorizationUrl"));
			config.getProvider().setAccessTokenUrl(provider.getAttribute("accessTokenUrl"));
		}
		
		return config;
	}
	
	private ServiceConfig createServiceConfig(Element element) {
		ServiceConfig config = new ServiceConfig();
		config.setPath(element.getAttribute("path"));
		config.setServiceClass(element.getAttribute("class"));
		config.setRefOAuth(element.getAttribute("oauth"));
		
		NodeList successList = element.getElementsByTagName("success");
		if (successList != null && successList.getLength() > 0) {
			config.setSuccessConfig(createSuccessConfig((Element) successList.item(0)));
		}
		
		return config;
	}
	
	private SuccessConfig createSuccessConfig(Element element) {
		SuccessConfig config = new SuccessConfig();
		config.setPath(element.getAttribute("path"));
		
		return config;
	}
}
