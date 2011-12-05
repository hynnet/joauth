/**
 * 
 */
package com.neurologic.oauth.hardjono.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.exception.OAuthException;
import net.oauth.parameters.OAuth2Parameters;
import net.oauth.parameters.OAuthParameters;

import org.json.JSONException;
import org.json.JSONObject;

import com.neurologic.exception.HostMetaDiscoveryException;
import com.neurologic.oauth.hardjono.enums.OAuthDiscoveryError;
import com.neurologic.oauth.hardjono.parameters.OAuthDiscoveryErrorParameters;
import com.neurologic.oauth.service.provider.AbstractOAuthProviderService;
import com.neurologic.oauth.service.provider.manager.OAuth2TokenManager;
import com.neurologic.oauth.service.response.OAuthResult;
import com.neurologic.oauth.service.response.impl.JsonEncodedMessage;
import com.neurologic.oauth.service.response.impl.OAuthMessageResult;

/**
 * @author Buhake Sindi
 * @since 15 November 2011
 *
 */
public abstract class DynamicRegistrationService extends AbstractOAuthProviderService<OAuth2TokenManager> {

	private static final String CONTENT_TYPE_JSON = "application/json";
	private static final String CLIENT_SECRET = OAuth2Parameters.CLIENT_SECRET;
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.AbstractOAuthProviderService#executeGet(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected OAuthResult executeGet(HttpServletRequest request) {
		// TODO Auto-generated method stub
		//Must do Discovery here....
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.AbstractOAuthProviderService#executePost(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected OAuthResult executePost(HttpServletRequest request) {
		// TODO Auto-generated method stub
		OAuthParameters parameters = null;
		OAuthMessageResult result = new OAuthMessageResult();
		int statusCode = 0;
		
		try {
			validateRequest(request, false);
			
			String contentType = request.getHeader("Content-Type");
			if (contentType == null || !contentType.startsWith(CONTENT_TYPE_JSON)) {
				throw new OAuthException("Invalid Content-Type '" + contentType + "' (REQUIRED: " + CONTENT_TYPE_JSON + ").");
			}
			
			JSONObject requestJson = new JSONObject(readContentBody(request));
			String type = requestJson.getString("type");
			String clientUrl = requestJson.getString("client_url");
			
			if ("push".equals(type)) {
				parameters = doPush(requestJson.getString("client_name"), clientUrl, requestJson.getString("client_description"), requestJson.getString("client_icon"), requestJson.getString("redirect_url"));
			} else if ("pull".equals(type)) {
				parameters = doPull(clientUrl);
			} else {
				throw new OAuthException("Invalid type '" + type + "'");
			}
			
			statusCode = HttpServletResponse.SC_OK;
		} catch (Exception exception) {
			// TODO Auto-generated catch block
			logger.error(exception.getLocalizedMessage(), exception);
			
			OAuthDiscoveryError error = OAuthDiscoveryError.UNAUTHORIZED_CLIENT;
			statusCode = HttpServletResponse.SC_BAD_REQUEST;
			
			if (exception instanceof HostMetaDiscoveryException) {
				error = OAuthDiscoveryError.HOSTMETA_ERROR;
				statusCode = HttpServletResponse.SC_UNAUTHORIZED;
			}
			
			parameters = handleException(error, exception.getLocalizedMessage(), getErrorUri());
			statusCode = HttpServletResponse.SC_UNAUTHORIZED;
		}
		
		result.setStatusCode(statusCode);
		if (parameters.getOAuthParameters().containsKey(CLIENT_SECRET)) {
			result.addHeader("Cache-Control", "no-store");
			result.addHeader("Pragma", "no-cache");
		}
		
		try {
			result.setMessage(new JsonEncodedMessage(parameters.getOAuthParameters(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("Unsupported charset", e);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			logger.error("JSONException", e);
		}
		
		return result;	
	}
	
	private String readContentBody(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		String line = null;
		
		try {
			while ((line = request.getReader().readLine()) != null) {
				builder.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error reading request content body: ", e);
		} finally {
			line = null;
		}
		
		return builder.toString();
	}
	
	protected OAuthDiscoveryErrorParameters handleException(OAuthDiscoveryError error, String message, String errorUri) {
		if (error == null) {
			return null;
		}
		
		OAuthDiscoveryErrorParameters errorParams = new OAuthDiscoveryErrorParameters(error);
		errorParams.setErrorDescription(message);
		errorParams.setErrorUri(errorUri);
		
		return errorParams;
	}
	
	public abstract OAuthParameters doPush(String clientName, String clientUrl, String clientDescription, String clientIcon, String redirectUrl) throws Exception;
	public abstract OAuthParameters doPull(String clientUrl) throws Exception;
	public abstract String getErrorUri();
}
