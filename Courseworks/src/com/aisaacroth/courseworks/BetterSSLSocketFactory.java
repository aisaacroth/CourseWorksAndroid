package com.aisaacroth.courseworks;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

/**
 * Due to the nature of Android's apache versions and SSLSocketFactor. It was
 * necessary to extend SSLFactory to increase functionality for SSL Exceptions.
 * 
 * NOTE: REMOVE BEFORE PRODUCTION CODE! THIS IS FOR TESTING ONLY!!!!
 * 
 * @author Alexander Roth
 * @Date   2014-02-28
 * @version 0.1
 */
public class BetterSSLSocketFactory extends SSLSocketFactory {
	SSLContext sslContext = SSLContext.getInstance("TLS");

	public BetterSSLSocketFactory(KeyStore truststore)
			throws NoSuchAlgorithmException, KeyManagementException,
			UnrecoverableKeyException, KeyStoreException {
		super(truststore);

		TrustManager tm = new X509TrustManager() {

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1)
					throws CertificateException {
			}

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1)
					throws CertificateException {
			}
		};

		sslContext.init(null, new TrustManager[] { tm }, null);
	}

	public BetterSSLSocketFactory(SSLContext context)
			throws KeyManagementException, UnrecoverableKeyException,
			NoSuchAlgorithmException, KeyStoreException {
		super(null);
		sslContext = context;
	}

	@Override
	public Socket createSocket(Socket socket, String host, int port,
			boolean autoClose) throws IOException, UnknownHostException {
		return sslContext.getSocketFactory().createSocket(socket, host, port,
				autoClose);
	}

	@Override
	public Socket createSocket() throws IOException {
		return sslContext.getSocketFactory().createSocket();
	}
}
