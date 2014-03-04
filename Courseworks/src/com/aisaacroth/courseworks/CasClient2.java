package com.aisaacroth.courseworks;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;

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
	
	// 1. Grab the Ticket Granting Ticket.

	public String login(String serviceUrl,
			UsernamePasswordCredentials credentials) throws IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
		String algorithm = TrustManagerFactory.getDefaultAlgorithm();
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm);
		tmf.init(store);

		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, tmf.getTrustManagers(), null);

		URL url = new URL(casBaseUrl + CAS_LOGIN_URL_PART + "?service="
					+ serviceUrl);
		HttpsURLConnection safeConn = (HttpsURLConnection)url.openConnection();
		
		String serviceTicket = null;
		return null;
	}
}
