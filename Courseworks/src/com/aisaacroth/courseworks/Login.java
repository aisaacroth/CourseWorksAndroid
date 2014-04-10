package com.aisaacroth.courseworks;

import java.io.File;

import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/*******************************************************************************
 * Login Activity which displays a login screen to the user. TODO: Implement
 * either OAuth or CAS for user authentication. TODO: Implement with
 * AccountManager for tokens for CAS or OAuth. TODO: Update the login method.
 * 
 * @author: Alexander Roth
 * @Date: 2014-02-25
 * @version: 0.2
 ******************************************************************************/
public class Login extends Activity {


	@SuppressLint("SetJavaScriptEnabled")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Allow the title bar to show loading progress.
		requestWindowFeature(Window.FEATURE_PROGRESS);

		String url = "https://cas.columbia.edu/cas/login?service=https%3A%2F%2Fcourseworks.columbia.edu%2Fsakai-login-tool%2Fcontainer";

		WebView webview = new WebView(this);
		webview.loadUrl(url);
		setContentView(webview);
		webview.getSettings().setJavaScriptEnabled(true);

	}

	// /***************************************************************************
	// * Keep track of the login task to ensure we can cancel it if requested.
	// **************************************************************************/
	// private UserLoginTask mAuthTask = null;
	//
	// // Values for email and password at the time of the login attempt.
	// private String mUNI;
	// private String mPassword;
	//
	// // AccountManager for Oauth authentication.
	// private AccountManager accountManager;
	// private AuthPreferences loginPreferences;
	// private Context context;
	//
	// // UI references.
	// private EditText mUNIView;
	// private EditText mPasswordView;
	// private View mLoginFormView;
	// private View mLoginStatusView;
	// private TextView mLoginStatusMessageView;
	// private CheckBox rememberMe;
	//
	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	//
	// setContentView(R.layout.activity_login);
	//
	// // Set up the login form.
	// mUNIView = (EditText) findViewById(R.id.uni);
	// mPasswordView = (EditText) findViewById(R.id.password);
	// rememberMe = (CheckBox) findViewById(R.id.remember_me);
	// mLoginFormView = findViewById(R.id.login_form);
	// mLoginStatusView = findViewById(R.id.login_status);
	// mLoginStatusMessageView = (TextView)
	// findViewById(R.id.login_status_message);
	//
	// accountManager = AccountManager.get(this);
	// loginPreferences = new AuthPreferences(this);
	//
	// mPasswordView
	// .setOnEditorActionListener(new TextView.OnEditorActionListener() {
	// @Override
	// public boolean onEditorAction(TextView textView, int id,
	// KeyEvent keyEvent) {
	// if (id == R.id.login || id == EditorInfo.IME_NULL) {
	// attemptLogin();
	// return true;
	// }
	// return false;
	// }
	// });
	//
	// findViewById(R.id.sign_in_button).setOnClickListener(
	// new View.OnClickListener() {
	// @Override
	// public void onClick(View view) {
	//
	// // TODO: Check to make sure that this block is working
	// // and saving to SharedPreferences.
	// if (rememberMe.isChecked()) {
	// loginPreferences.setUser(mUNI);
	// loginPreferences.setToken(mPassword);
	// } else {
	// loginPreferences.clear();
	// }
	// attemptLogin();
	// }
	// });
	//
	// // If there exists a Shared Preference file.
	// context = this;
	// String dirtyPath = context.getFilesDir().toString();
	// String path = dirtyPath.substring(0, dirtyPath.indexOf("files"));
	// File loginAuth = new File(path + "/shared_prefs/auth.xml");
	// if (loginAuth.exists()) {
	// mUNIView.setText(loginPreferences.getUser());
	// mPasswordView.setText(loginPreferences.getToken());
	// rememberMe.setChecked(true);
	// }
	// }
	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// super.onCreateOptionsMenu(menu);
	// getMenuInflater().inflate(R.menu.login, menu);
	// return true;
	// }
	//
	// /***************************************************************************
	// * Attempts to sign in the account specified by the login form. If there
	// are
	// * form errors (invalid uni, missing fields, etc.), the errors are
	// presented
	// * and no actual login attempt is made.
	// **************************************************************************/
	// public void attemptLogin() {
	// if (mAuthTask != null) {
	// return;
	// }
	// // Reset errors.
	// mUNIView.setError(null);
	// mPasswordView.setError(null);
	//
	// // Store values at the time of the login attempt.
	// mUNI = mUNIView.getText().toString();
	// mPassword = mPasswordView.getText().toString();
	//
	// boolean cancel = false;
	// View focusView = null;
	//
	// // Check for a valid password.
	// if (TextUtils.isEmpty(mPassword)) {
	// mPasswordView.setError(getString(R.string.error_field_required));
	// focusView = mPasswordView;
	// cancel = true;
	// } else if (mPassword.length() < 4) {
	// mPasswordView.setError(getString(R.string.error_invalid_password));
	// focusView = mPasswordView;
	// cancel = true;
	// }
	//
	// // Check for a valid uni.
	// if (TextUtils.isEmpty(mUNI)) {
	// mUNIView.setError(getString(R.string.error_field_required));
	// focusView = mUNIView;
	// cancel = true;
	// }
	//
	// if (cancel) {
	// // There was an error; don't attempt login and focus the first
	// // form field with an error.
	// focusView.requestFocus();
	// } else {
	// // Show a progress spinner, and kick off a background task to
	// // perform the user login attempt.
	// mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
	// showProgress(true);
	// mAuthTask = new UserLoginTask();
	// mAuthTask.execute((Void) null);
	// Intent main = new Intent(Login.this, Main.class);
	// startActivity(main);
	// }
	// }
	//
	// /***************************************************************************
	// * Shows the progress UI and hides the login form.
	// **************************************************************************/
	// @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	// private void showProgress(final boolean show) {
	// // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
	// // for very easy animations. If available, use these APIs to fade-in
	// // the progress spinner.
	// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
	// int shortAnimTime = getResources().getInteger(
	// android.R.integer.config_shortAnimTime);
	//
	// mLoginStatusView.setVisibility(View.VISIBLE);
	// mLoginStatusView.animate().setDuration(shortAnimTime)
	// .alpha(show ? 1 : 0)
	// .setListener(new AnimatorListenerAdapter() {
	// @Override
	// public void onAnimationEnd(Animator animation) {
	// mLoginStatusView.setVisibility(show ? View.VISIBLE
	// : View.GONE);
	// }
	// });
	//
	// mLoginFormView.setVisibility(View.VISIBLE);
	// mLoginFormView.animate().setDuration(shortAnimTime)
	// .alpha(show ? 0 : 1)
	// .setListener(new AnimatorListenerAdapter() {
	// @Override
	// public void onAnimationEnd(Animator animation) {
	// mLoginFormView.setVisibility(show ? View.GONE
	// : View.VISIBLE);
	// }
	// });
	// } else {
	// // The ViewPropertyAnimator APIs are not available, so simply show
	// // and hide the relevant UI components.
	// mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
	// mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
	// }
	// }
	//
	// /***************************************************************************
	// * Represents an asynchronous login/registration task used to authenticate
	// * the user.
	// **************************************************************************/
	// public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
	// @Override
	// protected Boolean doInBackground(Void... params) {
	// context = getApplicationContext();
	// OAuthClient.login(accountManager, context);
	// return true;
	// }
	//
	// @Override
	// protected void onPostExecute(final Boolean success) {
	// mAuthTask = null;
	// showProgress(false);
	//
	// if (success) {
	// finish();
	// } else {
	// mPasswordView
	// .setError(getString(R.string.error_incorrect_password));
	// mPasswordView.requestFocus();
	// }
	// }
	//
	// @Override
	// protected void onCancelled() {
	// mAuthTask = null;
	// showProgress(false);
	// }
	// }

}
