package com.aisaacroth.courseworks;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;


import android.annotation.SuppressLint;
import android.util.Log;

/*******************************************************************************
 * An Oauth Client for the Courseworks Android application. Interacts with the
 * web version of Courseworks and accepts user-based information. Performs
 * authentication based on Oauth specifications.
 * 
 * @author: Alexander Roth
 * @date: 2014-04-01
 * @version: 0.1
 ******************************************************************************/
public class OAuthClient {

	@SuppressLint("TrulyRandom")
	public static void login(UsernamePasswordCredentials credentials,
			String stringUrl) throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException {
		
		URL url = new URL(stringUrl);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

		// Create the SSL connection
		SSLContext sc;
		sc = SSLContext.getInstance("TLS");
		sc.init(null, null, new java.security.SecureRandom());
		conn.setSSLSocketFactory(sc.getSocketFactory());
		
		// Set Timeout and method
		conn.setReadTimeout(7000);
		conn.setConnectTimeout(7000);
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		
		// Add the data here.
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("grant_type", "password"));
		params.add(new BasicNameValuePair("username", credentials.getUserName()));
		params.add(new BasicNameValuePair("password", credentials.getPassword()));
		params.add(new BasicNameValuePair("client_id", "CLIENT_ID"));
		
		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		writer.write(getQuery(params));
		writer.flush();
		writer.close();
		os.close();
		
		conn.connect();
		
		Log.d("LOGIN", conn.getRequestMethod() + " " + conn.getResponseCode() + " " + conn.getResponseMessage());
	}
	
	private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		
		for (NameValuePair pair: params) {
			if (first) 
				first = false;
			else
				result.append("&");
			
			result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
		}
		Log.d("LOGIN", result.toString());
		return result.toString();
	}
	
}
