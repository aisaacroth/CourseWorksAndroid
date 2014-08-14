package com.aisaacroth.courseworks.views;

import java.io.File;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.adapters.SharedPreferencesAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.*;

/**
 * When the application is called, this splash screen will hold while the
 * program gets retrieves all necessary information. TODO: Implement so that the
 * main activity checks whether there is a file that already contains login
 * information.
 * 
 * @author Alexander Roth
 * @date 2014-02-24
 */
public class SplashView extends Activity {
    private SharedPreferencesAdapter preferenceAdapter;
    private File loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_view);

        preferenceAdapter = new SharedPreferencesAdapter(this, "auth");
        loginPreferences = preferenceAdapter.locateLoginSettings(this);

        if (loggedInBefore()) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    startMainWithSessionId();
                    finish();

                }
            }, 0);

        } else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    startLogin();
                    finish();
                }
            }, 0);
        }

    }

    private boolean loggedInBefore() {
        return ((loginPreferences.exists()) && hasSessionId()) ? true : false;
    }

    private boolean hasSessionId() {
        return (preferenceAdapter.getString("sessionId") != null) ? true
                : false;
    }

    private void startMainWithSessionId() {
        Intent mainIntent = new Intent(SplashView.this, Main.class);
        String jsession = preferenceAdapter.getString("sessionId");
        mainIntent.putExtra("JSESSION", jsession);
        startActivity(mainIntent);
    }
    
    private void startLogin() {
        Intent loginIntent = new Intent(SplashView.this, LoginView.class);
        startActivity(loginIntent);
    }
}