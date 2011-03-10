/**
 * 
 */
package com.neurologic.oauth.service.impl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.consumer.OAuth2Consumer;
import net.oauth.enums.ResponseType;
import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth2Parameters;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.neurologic.oauth.service.OAuthService;
import com.neurologic.oauth.util.Globals;

/**
 * @author Bienfait Sindi
 * @since 30 November 2010
 *
 */
public abstract class OAuth2Service implements OAuthService<OAuth2Consumer> {

	protected final Logger logger = Logger.getLogger(this.getClass());
	private OAuth2Consumer consumer;
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.OAuthService#setOAuthConsumer(java.lang.Object)
	 */
	@Override
	public void setOAuthConsumer(OAuth2Consumer consumer) {
		// TODO Auto-generated method stub
		this.consumer = consumer;
	}

	/**
	 * @return the consumer
	 */
	protected OAuth2Consumer getConsumer() {
		return consumer;
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.OAuthService#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public final void execute(HttpServletRequest request, HttpServletResponse response) throws OAuthException {
		// TODO Auto-generated method stub
		Map<String, String> parameterMap = extractParameterMap(request.getParameterMap());
			
		if (parameterMap.containsKey("error")) {
			throwOAuthErrorException(parameterMap);
		}
		
		//Process when we received "code" from request parameter.
		if (parameterMap.containsKey(ResponseType.CODE.toString())) {
			if (logger.isDebugEnabled()) {
				logger.debug("\"" + ResponseType.CODE.toString() + "\" received.");
			}
			
			String code = parameterMap.remove(ResponseType.CODE.toString());			
			String accessTokenUrl = processReceivedAuthorization(request, code, parameterMap);
			try {
				if (accessTokenUrl != null && !accessTokenUrl.isEmpty()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Redirecting to \"" + accessTokenUrl + "\".");
					}
					response.sendRedirect(accessTokenUrl);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new OAuthException(e);
			}
		}
		
		//Process when we received "access_token" from request parameter.
		if (parameterMap.containsKey(OAuth2Parameters.ACCESS_TOKEN)) {
			if (logger.isDebugEnabled()) {
				logger.debug("\"" + OAuth2Parameters.ACCESS_TOKEN + "\" received.");
			}
			String accessToken = parameterMap.remove(OAuth2Parameters.ACCESS_TOKEN);
			request.getSession().setAttribute(Globals.SESSION_OAUTH2_ACCESS_TOKEN, accessToken);
			processAdditionalReceivedAccessTokenParameters(request, parameterMap);
			
			if (logger.isInfoEnabled()) {
				logger.info("Access Token stored under \"" + Globals.SESSION_OAUTH2_ACCESS_TOKEN + "\" key.");
			}
		}
	}
	
	protected void throwOAuthErrorException(Map<String, String> parameterMap) throws OAuthException {
		JSONObject errorJson = new JSONObject();
		for (String parameterName : parameterMap.keySet()) {
			if (parameterName.startsWith("error")) {
				try {
					errorJson.put(parameterName, parameterMap.get(parameterName));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					//This should never happen?
					logger.error("JSONException? ", e);
				}
			}
		}
		
		throw new OAuthException("OAuth response returned the following error:\r\n" + errorJson.toString());
	}
	
	private Map<String, String> extractParameterMap(Map<String, String[]> requestParameterMap) {
		Map<String, String> resultMap = new LinkedHashMap<String, String>();
		for (String parameterName : requestParameterMap.keySet()) {
			String[] requestValue = requestParameterMap.get(parameterName);
			
			if (requestValue != null) {
				resultMap.put(parameterName, requestValue[0]);
			}
		}
		
		return resultMap;
	}
	
	protected abstract String processReceivedAuthorization(HttpServletRequest request, String code, Map<String, String> additionalParameters) throws OAuthException;
	protected abstract void processAdditionalReceivedAccessTokenParameters(HttpServletRequest request, Map<String, String> additionalParameters) throws OAuthException;
}
