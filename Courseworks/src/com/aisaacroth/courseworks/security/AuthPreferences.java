package com.aisaacroth.courseworks.security;

import android.content.*;

/**
 * Had to stream this file down because there was something going wrong with the
 * encryption algorithm. Now there is no form of encryption.
 * 
 * @author Alexander Roth
 * @date 2014-07-21
 */
public class AuthPreferences {

    private SharedPreferences preferences;

    public AuthPreferences(Context context, String preferenceName) {
        this.preferences = context.getSharedPreferences(preferenceName,
                Context.MODE_PRIVATE);
    }

    public void put(String key, String value) {
        preferences.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        if (preferences.contains(key)) {
            return preferences.getString(key, "");
        }
        return null;
    }

    public void clear() {
        preferences.edit().clear().commit();
    }

}