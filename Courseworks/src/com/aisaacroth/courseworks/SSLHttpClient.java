package com.aisaacroth.courseworks;

import java.io.InputStream;
import java.security.KeyStore;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

import android.content.Context;

public class SSLHttpClient extends DefaultHttpClient {
	// final Context context;
	//
	// public SSLHttpClient(Context context) {
	// this.context = context;
	// }
	//
	// protected ClientConnectionManager createClientManager() {
	// final SchemeRegistry registry = new SchemeRegistry();
	// registry.register(new Scheme("http", PlainSocketFactory
	// .getSocketFactory(), 80));
	// registry.register(new Scheme("https", newSslSocketFactory(), 443));
	// return new SingleClientConnManager(getParams(), registry);
	// }
	//
	// private SSLSocketFactory newSslSocketFactory() {
	// try {
	// KeyStore trusted = KeyStore.getInstance("BKS");
	// InputStream in =
	// context.getResources().openRawResource(R.raw.coursestore);
	// try {
	// trusted.load(in, "ez24get".toCharArray());
	// } finally {
	// in.close();
	// }
	// return new SSLSocketFactory(trusted);
	// } catch (Exception e) {
	// throw new AssertionError(e);
	// }
	// }
}
