package com.aisaacroth.courseworks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

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
	private HttpClient httpClient;
	private String casBaseUrl;

	/***************************************************************************
	 * Instantiates a new cas client2.
	 * 
	 * @param httpClient
	 *            the http client
	 * @param casBaseURL
	 *            the cas base url
	 **************************************************************************/
	public CasClient2(String casBaseURL) {
		//this.httpClient = this.sslClient(httpClient);
		this.casBaseUrl = casBaseURL;
	}

	/***************************************************************************
	 * Login.
	 * 
	 * @param serviceUrl
	 *            the service url
	 * @param credentials
	 *            the credentials
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws KeyStoreException
	 *             the key store exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws KeyManagementException
	 *             the key management exception
	 **************************************************************************/
	public String login(String serviceUrl,
			UsernamePasswordCredentials credentials) throws IOException,
			KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		// 0. Establish a secure connection with the CAS Sever.
		// TODO: Look into implementing a secure version of HttpsURLConnection.

		// 1. Grab the Ticket Granting Ticket (TGT)
		String lt = getLT(this.casBaseUrl + CAS_LOGIN_URL_PART + "?service="
				+ serviceUrl);
		HttpPost post = new HttpPost(this.casBaseUrl + CAS_LOGIN_URL_PART
				+ "?service=" + serviceUrl);

		// Add all the values to gain access to the server.
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("_eventId", "submit"));
		params.add(new BasicNameValuePair("username", credentials.getUserName()));
		params.add(new BasicNameValuePair("password", credentials.getPassword()));
		params.add(new BasicNameValuePair("lt", lt));
		post.setEntity(new UrlEncodedFormEntity(params));

		HttpResponse response = httpClient.execute(post);
		for (Header h : response.getAllHeaders()) {
			Log.d(TAG,
					"POST RESPONSE STATUS="
							+ response.getStatusLine().getStatusCode() + ":"
							+ response.getStatusLine().toString() + " PARAMS: "
							+ h.toString());
		}
		Header[] headers = response.getHeaders("Set-Cookie");
		for (Header header : headers) {
			Log.d(TAG, "HEADERS =" + header.toString());
		}
		System.out.println(EntityUtils.toString(response.getEntity()));
		// 2. Grab a service ticket (ST) for a CAS protected service.

		// 3. Grab the protected document.
		String serviceTicket = null;
		return serviceTicket;
	}

	private String getLT(String URL) {
		String lt = null;
		HttpGet get = new HttpGet(URL);
		try {
			HttpResponse response = this.httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				lt = extractLT(entity.getContent());
			}
			entity.consumeContent();
			Log.d(TAG, "LT=" + lt);
		} catch (ClientProtocolException e) {
			Log.d(TAG, "Getting LT Client Protocol exception", e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(TAG, "Getting LT io exception", e);
			e.printStackTrace();
		}
		return lt;
	}

	/***************************************************************************
	 * Helper method to extract the LT from the login form received from CAS.
	 * 
	 * @param data
	 *            InputStream with HTTP response body.
	 * @return The LT, if it could be extracted, null otherwise.
	 **************************************************************************/
	private String extractLT(InputStream dataStream) {
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

	/***************************************************************************
	 * Implementing this method because there is an issue with the SSL Peer
	 * certification. Necessary to set up because the test emulator does not
	 * have a valid SSL certificate causing a
	 * javax.net.ssl.SSLPeerUnverifiedException to be thrown. TODO: Document
	 * heavily.
	 * 
	 * NOTE: REMOVE BEFORE PUSHING TO PRODUCTION CODE! THIS IS SOLEY FOR TESTING
	 * PURPOSES!!!!!!!!!
	 * 
	 * @param client
	 * @return
	 **************************************************************************/
	private HttpClient sslClient(HttpClient client) {
		try {
			X509TrustManager tm = new X509TrustManager() {

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkServerTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
				}

				@Override
				public void checkClientTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
				}
			};
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new BetterSSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = client.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", ssf, 443));
			return new DefaultHttpClient(ccm, client.getParams());
		} catch (Exception ex) {
			return null;
		}
	}
}
