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
				logger.info("initializing...");
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
			logger.info("finalizing...");
		}

		getServletContext().removeAttribute(Globals.MODULE_KEY);
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
		if (logger.isTraceEnabled()) {
			logger.trace("process() via the " + request.getMethod() + " method.");
		}
		
		try {	
			OAuthProcessor processor = new OAuthProcessor((ModuleConfig) getServletContext().getAttribute(Globals.MODULE_KEY));
			processor.process(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException(e.getLocalizedMessage(), e);
		}
	}
	
	private ModuleConfig initializeModuleConfig(String oauthConfigFile) throws Exception {
		ModuleConfigFactory factory = new ModuleConfigFactory();
		ModuleConfig module = factory.createModuleConfig(getServletContext().getResourceAsStream(oauthConfigFile));
		getServletContext().setAttribute(Globals.MODULE_KEY, module);
		
		return module;
	}
}
