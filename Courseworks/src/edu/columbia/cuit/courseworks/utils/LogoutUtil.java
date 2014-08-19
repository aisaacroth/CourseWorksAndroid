package edu.columbia.cuit.courseworks.utils;

import android.content.*;


import edu.columbia.cuit.courseworks.adapters.SharedPreferencesAdapter;
import edu.columbia.cuit.courseworks.views.LoginView;

/**
 * Handles the Log Out option on the option menu bar for all the activities.
 * 
 * @author Alexander Roth
 * @date 2014-08-18
 */
public class LogoutUtil {

    private static SharedPreferencesAdapter sharedPreferences;

    public static void logout(Context context) {
        sharedPreferences = new SharedPreferencesAdapter(context, "auth");
        removeSessionCookie();
        sendToLoginScreen(context);
    }

    private static void sendToLoginScreen(Context context) {
        Intent logoutIntent = new Intent(context, LoginView.class);
        context.startActivity(logoutIntent);
    }

    private static void removeSessionCookie() {
        sharedPreferences.clear();
    }

}
