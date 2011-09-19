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
package com.neurologic.oauth.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.neurologic.oauth.config.ModuleConfig;
import com.neurologic.oauth.config.ModuleConfigFactory;
import com.neurologic.oauth.processor.DefaultOAuthProcessor;
import com.neurologic.oauth.processor.OAuthProcessor;
import com.neurologic.oauth.util.Globals;

/**
 * @author Bienfait Sindi
 * @since 23 November 2010
 *
 */
public class OAuthServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(OAuthServlet.class);
	private String oauthConfigFile = "/WEB-INF/oauth-config.xml"; 

	/**
	 * 
	 */
	private static final long serialVersionUID = 8893566942080699436L;

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		try {
			if (logger.isInfoEnabled()) {
				logger.info("Initializing...");
			}
			
			String config = getServletConfig().getInitParameter("config");
			if (config != null && !config.isEmpty()) {
				oauthConfigFile = config;
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("OAuth configuration file: " + oauthConfigFile + ".");
			}
			
			initializeModuleConfig(oauthConfigFile);
			if (logger.isInfoEnabled()) {
				logger.info("initialization complete...");
			}
		} catch (Throwable t) {
			// TODO Auto-generated catch block
			logger.error("Error initializing servlet." , t);
			throw new UnavailableException(t.getLocalizedMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		if (logger.isInfoEnabled()) {
			logger.info("Destroying...");
		}

		getServletContext().removeAttribute(Globals.MODULE_KEY);
		OAuthProcessor processor = getOAuthProcessor();
		if (processor != null) {
			processor.destroy();
			
			//Remove from Servlet Context
			getServletContext().removeAttribute(Globals.PROCESSOR_KEY);
			processor = null;
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(req, resp);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (logger.isInfoEnabled()) {
			logger.info("process() via the " + request.getMethod() + " method.");
		}
		
		try {	
			OAuthProcessor processor = getOAuthProcessor();
			if (processor == null) {
				ModuleConfig moduleConfig = getModuleConfig();
				if (moduleConfig == null) {
					moduleConfig = initializeModuleConfig(oauthConfigFile);
				}
				
				processor = initializeProcessor(moduleConfig);
			}
			
			//Now, we process
			processor.process(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String message = e.getLocalizedMessage();
			logger.error(message, e);
			throw new ServletException(e.getLocalizedMessage(), e);
		}
	}
	
	private ModuleConfig initializeModuleConfig(String oauthConfigFile) throws Exception {
		ModuleConfig moduleConfig = getModuleConfig();
		if (moduleConfig == null) {
			ModuleConfigFactory factory = new ModuleConfigFactory();
			moduleConfig = factory.createModuleConfig(getServletContext().getResourceAsStream(oauthConfigFile));
			getServletContext().setAttribute(Globals.MODULE_KEY, moduleConfig);
		}
		
		return moduleConfig;
	}
	
	private ModuleConfig getModuleConfig() {
		return (ModuleConfig) getServletContext().getAttribute(Globals.MODULE_KEY);
	}
	
	private OAuthProcessor initializeProcessor(ModuleConfig moduleConfig) throws ServletException {
		OAuthProcessor processor = getOAuthProcessor();
		if (processor == null) {
			processor = new DefaultOAuthProcessor();
			
			processor.init(moduleConfig);
			getServletContext().setAttribute(Globals.PROCESSOR_KEY, processor);
		}
		
		return processor;
	}
	
	private OAuthProcessor getOAuthProcessor() {
		return (OAuthProcessor) getServletContext().getAttribute(Globals.PROCESSOR_KEY);
	}
}
