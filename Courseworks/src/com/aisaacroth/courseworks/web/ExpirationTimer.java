package com.aisaacroth.courseworks.web;

import android.app.IntentService;
import android.content.Intent;

import com.aisaacroth.courseworks.adapters.*;

public class ExpirationTimer extends IntentService {

    private static final int WEEK = 1000 * 60 * 60 * 24 * 7;

    public ExpirationTimer(String name) {
        super("ExpirationTimer");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        long logoutTime = setWeekTime();
        if (longerThanWeek(logoutTime)) {
            synchronized (this) {
                try {
                    wait(logoutTime - System.currentTimeMillis());
                    CASRestAuthenticator.logout();
                    SharedPreferencesAdapter preferences = new SharedPreferencesAdapter(
                            getBaseContext(), "auth.xml");
                    preferences.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private long setWeekTime() {
        return System.currentTimeMillis() + WEEK;
    }
    
    private boolean longerThanWeek(long time) {
        return (System.currentTimeMillis() > time) ? true : false;
    }

}
