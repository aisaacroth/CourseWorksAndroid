package com.aisaacroth.courseworks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.auth.UsernamePasswordCredentials;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

/**
 * A second attempt at implementing a CAS client between the CAS Server and the
 * android application.
 * 
 * @author Alexander Roth
 * @data 2014-03-04
 * @version 0.1
 **/
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
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
	private final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
    private List<String> cookies;
	
	/**
	 * Instantiates a new cas client2.
	 * 
	 * @param httpClient
	 *            the http client
	 * @param casBaseURL
	 *            the cas base url
	 **/
	public CasClient2(String casBaseURL) {
		this.casBaseUrl = casBaseURL;
	}

	/**
	 * Login.
	 * 
	 * @param serviceUrl
	 *            the service url
	 * @param credentials
	 *            the credentials
	 **/
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

		String serviceTicket = null;
		return serviceTicket;
	}

	private String getPage(String url) throws IOException {
		URL temp = new URL(url);
		conn = (HttpsURLConnection) temp.openConnection();

		conn.setRequestMethod("GET");
		conn.setUseCaches(false);

		// act like a browser
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
		
		if (cookies != null) {
			for (String cookie : this.cookies) {
				conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
			}
		}
		
		int responseCode = conn.getResponseCode();
		Log.d(TAG, "Sending 'GET' request to URL : " + temp);
		Log.d(TAG, "Response Code: " + responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		// Get the response cookies
		setCookies(conn.getHeaderFields().get("Set-Cookie"));
		
		return response.toString();
	}
	
	public String getFormParams(String html, String username, String password) {
		Log.d(TAG, "Extracting form's data...");
		return null;
	}
	
	public List<String> getCookies(){
		return cookies;
	}
	
	public void setCookies(List<String> cookies) {
		this.cookies = cookies;
	}
}