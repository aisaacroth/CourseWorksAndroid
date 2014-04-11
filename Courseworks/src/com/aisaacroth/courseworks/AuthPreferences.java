package com.aisaacroth.courseworks;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/*******************************************************************************
 * Writes Authentication information for Oauth to a shared preference file. This
 * preference file will contain the password and user information for the
 * application.
 * 
 * @author:  Alexander Roth
 * @Date:    2014-04-04
 * @version: 0.1
 ******************************************************************************/
public class AuthPreferences {

	private static final String KEY_USER = "uni";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_REMEMBER = "remember";
	private SharedPreferences preferences;

	/***************************************************************************
	 * Creates a new file named "auth", which will house all the information
	 * related to the Oauth authentication process.
	 *
	 * @param context the current context
	 **************************************************************************/
	public AuthPreferences(Context context) {
		preferences = context
				.getSharedPreferences("auth", Context.MODE_PRIVATE);
	}

	/***************************************************************************
	 * Sets the user of the current session for Oauth authentication.
	 *
	 * @param user the user credential.
	 **************************************************************************/
	public void setUser(String user) {
		Editor editor = preferences.edit();
		editor.putString(KEY_USER, user);
		editor.commit();
	}

	/***************************************************************************
	 * Sets the password credentials for the Oauth authentication process.
	 *
	 * @param password the new token
	 **************************************************************************/
	public void setToken(String password) {
		Editor editor = preferences.edit();
		editor.putString(KEY_PASSWORD, password);
		editor.commit();
	}
	
	//TODO: Add Documentation
	public void isRemembered(boolean remember) {
		Editor editor = preferences.edit();
		editor.putBoolean(KEY_REMEMBER, remember);
		editor.commit();
	}
	
	/***************************************************************************
	 * Clears the information in the "auth" file in case the user does not want
	 * to save his or her credential information.
	 **************************************************************************/
	public void clear() {
		Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
	}

	/***************************************************************************
	 * Gets the user information
	 *
	 * @return the user
	 **************************************************************************/
	public String getUser() {
		return preferences.getString(KEY_USER, null);
	}

	/***************************************************************************
	 * Gets the password information.
	 *
	 * @return the token
	 **************************************************************************/
	public String getToken() {
		return preferences.getString(KEY_PASSWORD, null);
	}
	
	//TODO: Add Documentation.
	public boolean hasRemembered() {
		return preferences.getBoolean(KEY_REMEMBER, false);
	}
}