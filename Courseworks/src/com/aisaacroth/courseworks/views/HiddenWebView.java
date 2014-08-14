package com.aisaacroth.courseworks.views;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.adapters.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.*;

/**
 * Opens a non-visible WebView that parses the Session cookie to secure a web
 * connection
 * 
 * @author Alexander Roth
 * @date 2014-08-04
 */
@SuppressLint("SetJavaScriptEnabled")
public class HiddenWebView extends Activity {

    private SharedPreferencesAdapter preferenceAdapter;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_web_view);

        String ticket = getServiceTicketFromIntent();
        prepareWebView();
        accessPage(ticket);
        preferenceAdapter = new SharedPreferencesAdapter(this, "auth");

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                String cookies = CookieManager.getInstance().getCookie(url);
                Log.d("COOKIES", "Cookies: " + cookies);
                storeCookieIfRemembered(cookies);
                finish();
                startMainWithIntent(cookies);
            }
        });
    }

    private String getServiceTicketFromIntent() {
        Intent serviceTicketIntet = getIntent();
        return serviceTicketIntet.getStringExtra("ServiceTicket");
    }

    private void prepareWebView() {
        webView = (WebView) findViewById(R.id.hiddenWebView);
        webView.getSettings().setJavaScriptEnabled(true);
    }

    private void accessPage(String ticket) {
        // TODO: Move to Production Server
        String url = "https://sakaidev.cc.columbia.edu/sakai-login-tool/"
                + "container?force.login=yes&ticket=" + ticket;
        webView.loadUrl(url);
        Log.d("HIDDEN WEB VIEW URL", url);
    }

    private void storeCookieIfRemembered(String cookies) {
        boolean remembered = Boolean.parseBoolean(preferenceAdapter
                .getString("rememberMe"));
        if (remembered) {
            preferenceAdapter.put("sessionId", cookies);
        }

    }
    
    private void startMainWithIntent(String cookies) {
        Intent mainIntent = new Intent(HiddenWebView.this, Main.class);
        mainIntent.putExtra("JSESSION", cookies);
        startActivity(mainIntent);
    }
}