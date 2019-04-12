/*
* Copyright IBM Corp. 1987, 2019
* 
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
* 
* http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
* 
**/
package client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.ibm.json.java.JSONObject;

/**
 *  The JavaClient class defines an authenticated Java client to be able:
 *     - to call the RES REST API to set some ruleset properties
 *     - to execute a ruleset.
 * 
 */
public class JavaClient {
	protected CloseableHttpClient m_client;
	protected HttpClientContext m_context;
	private String m_host;
	private String m_port;
	
	/**
	 * This constructor defines the preemptive basic HTTP authentication.
	 * @param args 
	 */
	public JavaClient() {
		initProperties();
		HttpHost targetHost = new HttpHost(getServerName(), getServerPortNumber(), "http");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials("resAdmin", "resAdmin"));
		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);
		// Add AuthCache to the execution context
		this.m_context = HttpClientContext.create();
		this.m_context.setCredentialsProvider(credsProvider);
		this.m_context.setAuthCache(authCache);		
		this.m_client = HttpClientBuilder.create().build(); 
	}

	/**
	 * This method executes a ruleset with the provided parameters given as a JSON object.
	 */
	public void executeRuleSet(String ruleAppName, String ruleSetName, JSONObject rulesetParams) {
		executeJSONPostMethod(getExecutionServiceUrl(ruleAppName, ruleSetName), rulesetParams, null);
	}

	/**
	 * This method sets on a given ruleset the properties :
	 *    - to enable the BAI event emitter.
	 *    - to get the input parameters in the BAI event.
	 *    - to get the output parameters in the BAI event.
	 */
	public void setBAIProperties(String ruleAppName, String ruleSetName) {
		String rulesetPropertiesPath = getRestAuthenticatedUrl() + "/ruleapps/" + ruleAppName + "/1.0/" + ruleSetName + "/1.0/properties/";
		executePostMethod(rulesetPropertiesPath + "bai.emitter.enabled", "true");
		executePostMethod(rulesetPropertiesPath + "bai.emitter.input", "true");
		executePostMethod(rulesetPropertiesPath + "bai.emitter.output", "true");
		}

	/** Utilities */
	
	/**
	 * Close HttpClient instance
	 */
	public void closeHttpClient(){
		if(m_client != null){
			try {
				m_client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get methods
	 */
	/*
	 * Builds the URL to the REST API with an existing authentication.
	 */
	protected String getRestAuthenticatedUrl() {
		return "http://" + getServerName() + ":" + getServerPort()
				+ "/res/apiauth";
	}

	protected String getExecutionServiceUrl(String ruleApp, String ruleset) {
		return "http://" + getServerName() + ":" + getServerPort()
				+ "/DecisionService/rest/v1/" + ruleApp + "/1.0/" + ruleset + "/1.0";
	}

	protected String getServerName() {
		return m_host;
	}

	protected String getServerPort() {
		return m_port;
	}

	protected int getServerPortNumber() {
		return new Integer(getServerPort()).intValue();
	}
	/**
	 * Execution of HTTP methods 
	 */
	protected void executeJSONPostMethod(String url, JSONObject jsonObj,
			byte[] byteBody) {
		HttpPost postMethod = new HttpPost(url);
		try {
			if (jsonObj != null){
				StringEntity params = new StringEntity(jsonObj.toString(), "UTF8");
				params.setContentType("application/json");
				postMethod.setEntity(params);
			}
			else{
				ByteArrayEntity param = new ByteArrayEntity(byteBody);
				postMethod.setEntity(param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		executeMethod(url, postMethod, false);
	}

	protected void executePostMethod(String url, String stringBody) {
		HttpPost postMethod = new HttpPost(url);
		try {
				StringEntity params = new StringEntity(stringBody, "UTF8");
				params.setContentType("text/xml");
				postMethod.setEntity(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		executeMethod(url, postMethod, false);
	}

	protected JSONObject executeMethod(String url, HttpUriRequest method,
			boolean json) {
		JSONObject responseObject = null;
		try {
			// Executes the method.
			CloseableHttpResponse response =  m_client.execute(method, m_context);
			try{
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_NO_CONTENT && statusCode != HttpStatus.SC_CREATED ) {
					System.err.println("Method failed: " + response.getStatusLine());
				}
				HttpEntity entity = response.getEntity();
				if(entity != null){
					InputStream stream = entity.getContent();
					if (json) {
						try {
							responseObject = JSONObject.parse(stream);
						} catch (Exception e) {
							// The stream does not contain any JSON object.
							responseObject = null;
						}
					} else{
						displayResponse(url, statusCode,
								getResponse(stream));
					}
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} 
		return responseObject;
	}

	private String getResponse(InputStream stream) {
		int count = 0 ;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream() ;
		byte[] byteArray = new byte[1024];
		try {
			while((count = stream.read(byteArray, 0, byteArray.length)) > 0) {
				outputStream.write(byteArray, 0, count) ;
			}
			return new String(outputStream.toByteArray(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Initialization of properties 
	 */
	private void initProperties() {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("build");
			m_port = rb.getString("server.port");
			m_host = "localhost";
		} catch (MissingResourceException e) {
			System.out.println("No bundle found. Use the default port and host.");
			m_port = "9090";
			m_host = "localhost";
		}
	}

	protected void displayResponse(String url, int statusCode, String response) {
		System.out.println(">> URL " + url);
		System.out.println("<< RESPONSE - Status code " + statusCode + "\n");
		System.out.println(response);
	}




}
