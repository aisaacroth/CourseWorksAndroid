package com.aisaacroth.courseworks.auth;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.views.Main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.graphics.Bitmap;

/*******************************************************************************
 * An Oauth Client for the Courseworks Android application. Interacts with the
 * web version of Courseworks and accepts user-based information. Performs
 * authentication based on Oauth specifications. Uses the Implicit Grant method
 * of Oauth2.0.
 * 
 * @author: Alexander Roth
 * @Date: 2014-04-25
 ******************************************************************************/
public class ImplicitGrant extends Activity {
	private static final String TAG = "ImplicitGrant";
	public static final String CLIENT_ID = "CLIENT_ID";
	public static final String REDIRECT_URI = "REDIRECT_URI";
	private AuthPreferences loginPreferences;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		loginPreferences = new AuthPreferences(this, "auth",
				"Mh3C67M4IhHlx0BuMf5i2hWFtUtfAzl6", true);

		String url = "https://cas.columbia.edu/ouath2/authenticate?+ "
				+ "response_type=token" + "&client_id=" + CLIENT_ID
				+ "&redirect_uri=" + REDIRECT_URI;

		WebView webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				String accessTokenFragment = "access_token=";
				int start = url.indexOf(accessTokenFragment);
				if (start > -1) {
					String accessToken = url.substring(start
							+ accessTokenFragment.length(), url.length());
					Log.v(TAG, "OAuth complete, token: [" + accessToken + "].");

					Toast.makeText(ImplicitGrant.this, "Token: " + accessToken,
							Toast.LENGTH_SHORT).show();
					loginPreferences.put("Access_Token", accessToken);
					startActivity(new Intent(ImplicitGrant.this, Main.class));
				}
			}
		});
		webView.loadUrl(url);
	}
}
