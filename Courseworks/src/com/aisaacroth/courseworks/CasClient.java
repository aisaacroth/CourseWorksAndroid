package com.aisaacroth.courseworks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class CasClient {
	private static final String TAG = "CASCLIENT";
	private static final String CAS_LOGIN_URL_PART = "login";
	private static final String CAS_LOGOUT_URL_PART = "logout";
	private static final String CAS_SERVICE_VALIDATE_URL_PART = "serviceValidate";
	private static final String CAS_TICKET_BEGIN = "ticket=";
	private static final String CAS_LT_BEGIN = "name=\"lt\" value=\"";
	private static final String CAS_USER_BEGIN = "<cas:user>";
	private static final String CAS_USER_END = "</cas:user>";

	/*
	 * An HTTP client (browser replacement) that will interact with the CAS
	 * server. Usually provided by the user, as it is this client that will be
	 * "logged in" to the CAS server.
	 */
	private HttpClient httpClient;

	/*
	 * This is the "base url", or the root URL of the CAS server that will be
	 * providing the authentication services.
	 */
	private String casBaseURL;

	/**
	 * Constrcut a new CasClient which uses the specified HttpClient for its
	 * HTTP calls. If the CAS authentication is successful, it is the supplied
	 * HttpClient to which the acquired credentials are attached.
	 * 
	 * @param httpClient
	 *            The HTTP Client ("browser replacement") that will attempt to
	 *            "login" to the CAS server.
	 * @param casBaseUrl
	 *            The base URL of the CAS service to be used.
	 */
	public CasClient(HttpClient httpCLient, String casBaseUrl) {
		this.httpClient = httpCLient;
		this.casBaseURL = casBaseUrl;
	}

	/**
	 * Authenticate the specified user credentials and request a service ticket
	 * for the specified service. If no service is specified, user credentials
	 * are checks but no service ticket is generated (returns null).
	 * 
	 * @param serviceURL
	 *            The service to login for, yielding a service ticket that can
	 *            be presented to the service for validation. May be null, in
	 *            which case the user credentials are validated, but no service
	 *            ticket is returned by this method.
	 * @param username
	 * @param password
	 * @return A valid service ticket, if the specified service URL is not null
	 *         and the (login; password) pair is accepted by the CAS server.
	 * @throws CasAuthenticationException
	 *             if the (login; password) pair is not accepted by the CAS
	 *             server.
	 * @throws CasProtocolException
	 *             if there is an error communicating with the CAS server.
	 */
	public String login(String serviceUrl, String username, String password)
			throws CasAuthenticationException, CasProtocolException {
		String serviceTicket = null;
		String lt = getLTFromLoginForm(serviceUrl);
		if (lt == null) {
			Log.d(TAG,
					"Cannot retrieve LT from CAS. Aborting authentication for '"
							+ username + "'");
			throw new CasProtocolException(
					"Cannot retrieve LT from CAS. Aborting authentication for '"
							+ username + "'");
		} else {
			Log.d(TAG, "POST " + casBaseURL + CAS_LOGIN_URL_PART + "?service="
					+ serviceUrl);
			HttpPost httpPost = new HttpPost(casBaseURL + CAS_LOGIN_URL_PART
					+ "?service=" + serviceUrl);
			try {
				// Add form parameters to request body
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("_eventId", "submit"));
				nvps.add(new BasicNameValuePair("username", username));
				nvps.add(new BasicNameValuePair("gateway", "true"));
				nvps.add(new BasicNameValuePair("password", password));
				nvps.add(new BasicNameValuePair("lt", lt));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));

				// Execute post method
				HttpResponse response = httpClient.execute(httpPost);
				Log.d(TAG, "POST RESPONSE STATUS="
						+ response.getStatusLine().getStatusCode() + ":"
						+ response.getStatusLine().toString());

				Header headers[] = response.getHeaders("Location");
				if (headers != null && headers.length > 0)
					serviceTicket = extractServiceTicket(headers[0].getValue());
				HttpEntity entity = response.getEntity();
				entity.consumeContent();

				if (serviceTicket == null) {
					Log.i(TAG, "Authentication to service '" + serviceUrl
							+ "' unsuccessful for username '" + username + "'.");
					throw new CasAuthenticationException(
							"Authentication to service '" + serviceUrl
									+ "' unsuccessful for username '"
									+ username + "'.");
				} else {
					Log.i(TAG, "Authentication to service '" + serviceUrl
							+ "' successful for username '" + username + "'.");
				}
			} catch (IOException e) {
				Log.d(TAG, "IOException trying to login : " + e.getMessage());
				throw new CasProtocolException("IOException trying to login: "
						+ e.getMessage());
			}
			return serviceTicket;
		}

	}

	/**
	 * Logout from the CAS Server. This destroys all the local authentication
	 * cookies and any tickets stored on the server.
	 * 
	 * @return <code>true</false> if the logout is acknowledged by the CAS server
	 */
	public boolean logout() {
		boolean logoutSuccess = false;
		HttpGet httpGet = new HttpGet(casBaseURL + CAS_LOGOUT_URL_PART);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			logoutSuccess = (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
			Log.d(TAG, response.getStatusLine().toString());
		} catch (Exception e) {
			Log.d(TAG, "Exception trying to logout : " + e.getMessage());
			logoutSuccess = false;
		}
		return logoutSuccess;
	}

	/**
	 * Validate the specified service ticket against the specified service. If
	 * the ticket is valid, this will yield the clear text user name of the
	 * authenticated user.
	 * 
	 * Note that each service ticket issued by CAS can be used exactly once to
	 * validate.
	 * 
	 * @param serviceUrl
	 *            The serviceUrl to validate against.
	 * @param serviceTicket
	 *            The service ticket (previously provided by the CAS) for the
	 *            serviceUrl.
	 * @return Clear text username of the authenticated user.
	 * @throws CasProtocolexception
	 *             if a protocol or communication error occurs.
	 * @throws CasClientValidationException
	 *             if the CAS server refuses the ticket for the service.
	 */
	public String validate(String serviceUrl, String serviceTicket)
			throws CasAuthenticationException, CasProtocolException {
		HttpPost httpPost = new HttpPost(casBaseURL
				+ CAS_SERVICE_VALIDATE_URL_PART);
		Log.d(TAG, "VALIDATE : " + httpPost.getRequestLine());
		String username = null;
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("service", serviceUrl));
			nvps.add(new BasicNameValuePair("ticket", serviceTicket));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse response = httpClient.execute(httpPost);
			Log.d(TAG, "VALIDATE RESPONSE : "
					+ response.getStatusLine().toString());
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.d(TAG, "Could not validate: " + response.getStatusLine());
				throw new CasAuthenticationException(
						"Could not valdate serviec: "
								+ response.getStatusLine());
			} else {
				HttpEntity entity = response.getEntity();
				username = extractUser(entity.getContent());
				Log.d(TAG, "VALIDATE OK YOU ARE : " + username);
				entity.consumeContent();
			}
		} catch (Exception e) {
			Log.d(TAG, "Could not validate: " + e.getMessage());
			throw new CasProtocolException("Could not validate : "
					+ e.getMessage());
		}
		return username;
	}

	/**
	 * This method requests the original login form from CAS. This form contains
	 * an LT, an initial token that must be presented to CAS upon sending it an
	 * authentication request with credentials.
	 * 
	 * If the (optional) service URL is provided, this method will construct the
	 * URL such that CAS will correctly authenticate against the specified
	 * service when a subsequent authentication request is sent (with the login
	 * method).
	 * 
	 * @param serviceUrl
	 * @return The LT token if it could be extracted from the CAS response, else
	 *         null.
	 */
	protected String getLTFromLoginForm(String serviceUrl) {
		HttpGet httpGet = new HttpGet(casBaseURL + CAS_LOGIN_URL_PART
				+ "?service=" + serviceUrl);

		String lt = null;
		try {
			Log.d(TAG, "Ready to get LT from " + casBaseURL
					+ CAS_LOGIN_URL_PART + "?service=" + serviceUrl);
			HttpResponse response = httpClient.execute(httpGet);
			Log.d(TAG, "Response = " + response.getStatusLine().toString());
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				Log.d(TAG, "Could not obtain LT token from CAS: "
						+ response.getStatusLine().getStatusCode());
			} else {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					lt = extractLt(entity.getContent());
				}
				entity.consumeContent();
				Log.d(TAG, "LT=" + lt);
			}
		} catch (ClientProtocolException e) {
			Log.d(TAG, "Getting LT client protocol exception", e);
		} catch (IOException e) {
			Log.d(TAG, "Getting LT io exception", e);
		}

		return lt;
	}

	/**
	 * Helper method to extract the user name from a "service validate" call to
	 * CAS.
	 * 
	 * @param data
	 *            Response data.
	 * @return The clear text username, if it could be extracted, null
	 *         otherwise.
	 */
	protected String extractUser(InputStream dataStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				dataStream));
		String user = null;
		try {
			String line = reader.readLine();
			while (user == null && line != null) {
				int start = line.indexOf(CAS_USER_BEGIN);
				if (start >= 0) {
					start += CAS_TICKET_BEGIN.length();
					int end = line.indexOf(CAS_USER_END, start);
					user = line.substring(start, end);
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			Log.d(TAG, e.getLocalizedMessage());
		}
		return user;
	}

	/**
	 * Helper method to extract the service ticket from a login call to CAS.
	 * 
	 * @param data
	 *            Response data.
	 * @return The service ticket, if it could be extracted, null otherwise.
	 */
	protected String extractServiceTicket(String data) {
		String serviceTicket = null;
		int start = data.indexOf(CAS_TICKET_BEGIN);
		if (start > 0) {
			start += CAS_TICKET_BEGIN.length();
			serviceTicket = data.substring(start);
		}
		return serviceTicket;
	}

	/**
	 * Helper method to extract the LT from the login form received from CAS.
	 * 
	 * @param data
	 *            InputStream with HTTP response body.
	 * @return The LT, if it could be extracted, null otherwise.
	 */
	protected String extractLt(InputStream dataStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				dataStream));
		String token = null;
		try {
			String line = reader.readLine();
			while (token == null && line != null) {
				int start = line.indexOf(CAS_LT_BEGIN);
				if (start >= 0) {
					start += CAS_LT_BEGIN.length();
					int end = line.indexOf("\"", start);
					token = line.substring(start, end);
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			Log.d(TAG, e.getMessage());
		}
		return token;
	}
}
