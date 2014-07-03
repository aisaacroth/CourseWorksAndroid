package com.aisaacroth.courseworks.views;

import java.io.File;
import java.io.IOException;

import org.apache.http.auth.UsernamePasswordCredentials;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.auth.AuthPreferences;
import com.aisaacroth.courseworks.auth.RestGrant;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Login Activity which displays a login screen to the user and controls login
 * protocols.
 * 
 * @author Alexander Roth
 * @date 2014-02-25
 */
public class Login extends Activity {

	private UserLoginTask loginTask = null;

	private String uni;
	private String password;

	private AuthPreferences loginPreferences;
	private Context context;
	private UsernamePasswordCredentials credentials;

	private EditText uniTextField;
	private EditText passwordTextField;
	private View loginFormView;
	private View loginStatusView;
	private TextView loginStatusMessageField;
	private CheckBox rememberMeCheckBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setUpLoginForm();

		loginPreferences = createLoginPreferences();
		File loginSettings = locateLoginSettings();

		if (checkLoginedBefore(loginSettings)) {
			autoFillTextField();
		}

		passwordTextField
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {

						if (rememberMeCheckBox.isChecked()) {
							storeLoginPreferences();
						} else {
							loginPreferences.clear();
						}
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in the account specified by the login form. If there are
	 * form errors (invalid UNI, missing fields, etc.), the errors are presented
	 * and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (loginTask != null) {
			return;
		}

		resetErrorNotification();

		// Store values at the time of the login attempt.
		uni = retrieveTextFromTextField(uniTextField);
		password = retrieveTextFromTextField(passwordTextField);
		credentials = new UsernamePasswordCredentials(uni, password);

		boolean error = false;
		View focusView = null;

		if (TextUtils.isEmpty(password)) {
			focusView = missingFieldFailedLogin(passwordTextField, focusView,
					error);
		} else if (checkPasswordIsShort()) {
			focusView = invalidPasswordFails(focusView, error);
		}

		if (TextUtils.isEmpty(uni)) {
			focusView = missingFieldFailedLogin(uniTextField, focusView, error);
		}

		if (error) {
			focusView.requestFocus();
		} else {
			procceedWithLogin();
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			loginStatusView.setVisibility(View.VISIBLE);
			loginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							loginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			loginFormView.setVisibility(View.VISIBLE);
			loginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							loginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			loginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				RestGrant.login(credentials);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			loginTask = null;
			showProgress(false);

			if (success) {
				finish();
			} else {
				passwordTextField
						.setError(getString(R.string.error_incorrect_password));
				passwordTextField.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			loginTask = null;
			showProgress(false);
		}
	}

	private void autoFillTextField() {
		fillUniTextField();
		fillPasswordTextField();
	}

	private boolean checkLoginedBefore(File loginSettings) {
		if (loginSettings.exists() && hasUNI() && hasPassword()) {
			return true;
		}
		return false;
	}

	private boolean checkPasswordIsShort() {
		return password.length() < 4 ? true : false;
	}

	private AuthPreferences createLoginPreferences() {
		return new AuthPreferences(this, "auth",
				"Mh3C67M4IhHlx0BuMf5i2hWFtUtfAzl6", true);
	}

	private void fillPasswordTextField() {
		passwordTextField.setText(loginPreferences.getString("password"));
	}

	private void fillUniTextField() {
		uniTextField.setText(loginPreferences.getString("uni"));
	}

	private boolean hasPassword() {
		return (loginPreferences.getString("password") != null) ? true : false;
	}

	private boolean hasUNI() {
		return (loginPreferences.getString("uni") != null) ? true : false;
	}

	private View invalidPasswordFails(View focusView, boolean error) {
		passwordTextField.setError(getString(R.string.error_invalid_password));
		focusView = passwordTextField;
		error = true;
		return focusView;
	}

	private String locateFilePath(Context acitivty) {
		String dirtyPath = context.getFilesDir().toString();
		String path = dirtyPath.substring(0, dirtyPath.indexOf("file"));
		return path;
	}

	private File locateLoginSettings() {
		context = this;
		String path = locateFilePath(context);
		File loginAuth = new File(path + "/shared_prefs/auth.xml");
		return loginAuth;
	}

	private View missingFieldFailedLogin(TextView textField, View focusView,
			boolean error) {
		textField.setError(getString(R.string.error_field_required));
		focusView = textField;
		error = true;
		return focusView;
	}

	private void procceedWithLogin() {
		loginStatusMessageField.setText(R.string.login_progress_signing_in);
		showProgress(true);
		loginTask = new UserLoginTask();
		loginTask.execute((Void) null);
		Intent main = new Intent(Login.this, Main.class);
		startActivity(main);
	}

	private void resetErrorNotification() {
		uniTextField.setError(null);
		passwordTextField.setError(null);
	}

	private String retrieveTextFromTextField(TextView textField) {
		return textField.getText().toString();
	}

	private void setUpLoginForm() {
		uniTextField = (EditText) findViewById(R.id.uni);
		passwordTextField = (EditText) findViewById(R.id.password);
		rememberMeCheckBox = (CheckBox) findViewById(R.id.remember_me);
		loginFormView = findViewById(R.id.login_form);
		loginStatusView = findViewById(R.id.login_status);
		loginStatusMessageField = (TextView) findViewById(R.id.login_status_message);
	}

	private void storeLoginPreferences() {
		uni = retrieveTextFromTextField(uniTextField);
		password = retrieveTextFromTextField(passwordTextField);
		loginPreferences.put("uni", uni);
		loginPreferences.put("password", password);
	}

}