package com.aisaacroth.courseworks.views;

import com.aisaacroth.courseworks.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.*;

@SuppressLint("SetJavaScriptEnabled")
public class HiddenWebView extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_web_view);

        Intent extraIntent = getIntent();
        String ticket = extraIntent.getStringExtra("ServiceTicket");
        webView = (WebView) findViewById(R.id.hiddenWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVisibility(View.INVISIBLE);
        String url = "https://sakaidev.cc.columbia.edu/sakai-login-tool/"
                + "container?force.login=yes&ticket=" + ticket;
        webView.loadUrl(url);
        
        webView.setWebViewClient(new WebViewClient() {
            
            @Override
            public void onPageFinished(WebView view, String url) {
                String cookies = CookieManager.getInstance().getCookie(url);
                Log.d("COOKIES",  "Cookies: " + cookies);
                finish();
                Intent mainIntent = new Intent(HiddenWebView.this, Main.class);
                mainIntent.putExtra("JSESSION", cookies);
                HiddenWebView.this.startActivity(mainIntent);
            }
        });
    }

}
