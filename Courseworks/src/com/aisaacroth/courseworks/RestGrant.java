package com.aisaacroth.courseworks;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import android.annotation.SuppressLint;
import android.util.Log;

/*******************************************************************************
 * A CAS Client that implements CAS' RESTful API in order to communicate with
 * the authentication server. Meant as an alternate to the method than the OAuth
 * protocols that could also be implemented.
 * 
 * @author: Alexander Roth
 * @Date: 2014-05-19
 ******************************************************************************/

public class RestGrant {

	public static String tGT;

	/***************************************************************************
	 * Logins the user into the Columbia's CAS Servers using calls to the CAS
	 * RESTful API.
	 * 
	 * @param credentials
	 *            The user's personal credentials (i.e. username and password).
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 **************************************************************************/
	@SuppressLint("NewApi")
	public static void login(UsernamePasswordCredentials credentials)
			throws IOException {
		String username = credentials.getUserName();
		String password = credentials.getPassword();
		tGT = RestGrant.getGrantingTicket(username, password);

		// Checks that the ticket is not empty and that it exists.
		if (tGT.isEmpty() || tGT == null) {
			throw new NullPointerException();
		}

		String serviceTicket = RestGrant.getServiceTicket(tGT);

		// Checks that the ticket is not empty and that it exists.
		if (serviceTicket.isEmpty() || tGT == null) {
			throw new NullPointerException();
		}
	}

	/***************************************************************************
	 * Logouts the user out of the CAS server by calling DELETE to the RESTful
	 * API.
	 * 
	 * @throws ClientProtocolException
	 *             the client protocol exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 **************************************************************************/
	public static void logout() throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpDelete delete = new HttpDelete(
				"http://cas.columbia.edu/cas/v1/tickets/" + tGT);
		httpClient.execute(delete);
	}

	/***************************************************************************
	 * Makes a POST call to CAS authentication servers in order to return a
	 * Ticket Granting Ticket Resource.
	 * 
	 * @param username
	 *            The user's username (e.g. their uni).
	 * @param password
	 *            The user's password.
	 * @return the ticket granting ticket, which allows for the system to ask
	 *         for a service ticket. Returns null on error or if the ticket was
	 *         not granted.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 **************************************************************************/
	public static String getGrantingTicket(String username, String password)
			throws IOException {
		String ticket = null;
		HttpResponse response = postUserToServer(username, password);
		HttpParams param = response.getParams();

		// Checks that everything returned OK in the call, begins parsing.
		if (param.getParameter("Location") != null
				&& response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_CREATED) {
			String paramString = param.getParameter("Locatin").toString();
			ticket = paramString.substring(paramString.lastIndexOf('/') + 1);
		}
		return ticket;
	}

	/***************************************************************************
	 * Gets the service ticket.
	 * 
	 * @param ticket
	 *            the ticket
	 * @return the service ticket
	 * @throws ClientProtocolException
	 *             the client protocol exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 **************************************************************************/
	public static String getServiceTicket(String ticket)
			throws ClientProtocolException, IOException {
		String serviceTicket = null;
		HttpResponse response = RestGrant.postTicketToServer(ticket);
		
		// Parses the Service Ticket from the HTTP response.
		if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
			serviceTicket = response.getEntity().toString();
		}
		return serviceTicket;
	}

	/***************************************************************************
	 * Post user to server.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return the http response
	 * @throws ClientProtocolException
	 *             the client protocol exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 **************************************************************************/
	private static HttpResponse postUserToServer(String username,
			String password) throws ClientProtocolException, IOException {
		// Create a new HttpClient and Post Header
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://cas.columbia.edu/cas/v1/tickets");

		// Add the necessary data
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		httpPost.setEntity(new UrlEncodedFormEntity(params));

		// Some debugging information
		Log.d("Courseworks", httpPost.toString());
		Log.d("Courseworks", httpPost.getEntity().toString());

		// Execute HTTP Post Request
		HttpResponse response = httpClient.execute(httpPost);
		return response;
	}

	/***************************************************************************
	 * Post ticket to server.
	 * 
	 * @param ticket
	 *            the ticket
	 * @return the http response
	 * @throws ClientProtocolException
	 *             the client protocol exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 **************************************************************************/
	private static HttpResponse postTicketToServer(String ticket)
			throws ClientProtocolException, IOException {
		// Create a new HttpClient and Post Header
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://cas.columbia.edu/cas/v1/tickets/" + ticket);

		// Add the necessary data
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("service",
				"https://courseworks.columbia.edu/portal"));

		// Some debugging information.
		Log.d("Courseworks", httpPost.toString());
		Log.d("Courseworks", httpPost.getAllHeaders().toString());

		// Execute HTTP Post Request
		HttpResponse response = httpClient.execute(httpPost);
		return response;
	}
}
