package com.aisaacroth.courseworks;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.BasicHttpContext;


public class CasClient2 {
	private static final String TAG = "CASCLIENT";
	private static final String CAS_LOGIN_URL_PART = "login";
	private static final String CAS_LOGOUT_URL_PART = "logout";
	private static final String CAS_SERVICE_VALIDATE_URL_PART = "serviceValidate";
	private static final String CAS_TICKET_BEGIN = "ticket=";
	private static final String CAS_LT_BEGIN = "name=\"lt\" value=\"";
	private static final String CAS_USER_BEGIN = "<cas:user>";
	private static final String CAS_USER_END = "</cas:user>";

	private HttpClient httpClient;
	private String casBaseUrl;

	public CasClient2(HttpClient httpClient, String casBaseURL) {
		this.httpClient = httpClient;
		this.casBaseUrl = casBaseURL;
	}

	public String login(String serviceUrl,
			UsernamePasswordCredentials credentials) {
		return null;
	}
}
