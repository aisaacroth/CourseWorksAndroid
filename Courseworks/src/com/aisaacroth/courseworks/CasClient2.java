package com.aisaacroth.courseworks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.cookie.Cookie;

import android.util.Log;

//import android.util.Log;

/*******************************************************************************
 * A second attempt at implementing a CAS client between the CAS Server and the
 * android application.
 * 
 * @author Alexander Roth
 * @data 2014-03-04
 * @version 0.1
 ******************************************************************************/
public class CasClient2 {

	private static final String TAG = "CASCLIENT2";
	private static final String CAS_LOGIN_URL_PART = "login";
	private static final String CAS_LOGOUT_URL_PART = "logout";
	private static final String CAS_SERVICE_VALIDATE_URL_PART = "serviceValidate";
	private static final String CAS_TICKET_BEGIN = "ticket=";
	private static final String CAS_LT_BEGIN = "name=\"lt\" value=\"";
	private static final String CAS_USER_BEGIN = "<cas:user>";
	private static final String CAS_USER_END = "</cas:user>";
	private String casBaseUrl;
	private HttpsURLConnection conn;

	/***************************************************************************
	 * Instantiates a new cas client2.
	 * 
	 * @param httpClient
	 *            the http client
	 * @param casBaseURL
	 *            the cas base url
	 **************************************************************************/
	public CasClient2(String casBaseURL) {
		this.casBaseUrl = casBaseURL;
	}

	/***************************************************************************
	 * Login.
	 * 
	 * @param serviceUrl
	 *            the service url
	 * @param credentials
	 *            the credentials
	 **************************************************************************/
	public String login(String serviceUrl,
			UsernamePasswordCredentials credentials) throws IOException {

		// 0. Establish a secure connection with the CAS Sever.
		// URL url = new URL(this.casBaseUrl + CAS_LOGIN_URL_PART + "?service="
		// + serviceUrl);
		// conn = (HttpsURLConnection) url.openConnection();
		// Log.d(TAG,
		// "Connection to host :" + conn.getResponseCode() + " "
		// + conn.getResponseMessage());

		// Turn cookies on.
		CookieHandler.setDefault(new CookieManager());

		// 1. Send a "GET" request, so that we can extract form information.
		String page = this.getPage(this.casBaseUrl + CAS_LOGIN_URL_PART
				+ "?service=" + serviceUrl);

		InputStream is = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println("line: " + line);
		}

		String serviceTicket = null;
		return serviceTicket;
	}

	private String getPage(String url) throws MalformedURLException {
		URL temp = new URL(url);
		return null;
	}
}