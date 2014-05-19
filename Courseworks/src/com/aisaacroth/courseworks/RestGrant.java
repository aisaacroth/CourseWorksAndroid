package com.aisaacroth.courseworks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class RestGrant {

	public static void login(String url, UsernamePasswordCredentials credentials) {
		String username = credentials.getUserName();
		String password = credentials.getPassword();

	}

	public static String getGrantingTicket(String url, String username,
			String password) throws IOException {
		String ticket = null;
		HttpResponse response = postToServer(username, password);
		HttpParams param = response.getParams();
		if (param.getParameter("Location") != null
				&& response.getStatusLine().getStatusCode() == 201) {
			String paramString = param.getParameter("Locatin").toString();
			ticket = paramString.substring(paramString.lastIndexOf('/'));
		}
		return ticket;
	}

	public static HttpResponse postToServer(String username, String password)
			throws ClientProtocolException, IOException {
		// Create a new HttpClient and Post Header
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://cas.columbia.edu/cas/v1/tickets");

		// Add the necessary data
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		httpPost.setEntity(new UrlEncodedFormEntity(params));

		Log.d("Courseworks", httpPost.toString());
		Log.d("Courseworks", httpPost.getEntity().toString());

		// Execute HTTP Post Request
		HttpResponse response = httpClient.execute(httpPost);
		return response;
	}

}
