package com.aisaacroth.courseworks.views;

import java.io.File;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.auth.AuthPreferences;
import com.aisaacroth.courseworks.services.RestAuthService;

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
 * Initial activity which handles the UI elements for the user and delegates
 * login protocols to the necessary services.
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

        if (checkLoggedInBefore(loginSettings)) {
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

    /**
     * Sets up the Login Form.
     */
    private void setUpLoginForm() {
        uniTextField = (EditText) findViewById(R.id.uni);
        passwordTextField = (EditText) findViewById(R.id.password);
        rememberMeCheckBox = (CheckBox) findViewById(R.id.remember_me);
        loginFormView = findViewById(R.id.login_form);
        loginStatusView = findViewById(R.id.login_status);
        loginStatusMessageField = (TextView) findViewById(R.id.login_status_message);
    }

    /**
     * Creates a new xml file ("auth.xml") which is used to store user
     * information if requested by the user.
     * 
     * @return the newly created "auth.xml" file
     */
    private AuthPreferences createLoginPreferences() {
        return new AuthPreferences(this, "auth",
                "Mh3C67M4IhHlx0BuMf5i2hWFtUtfAzl6", true);
    }

    /**
     * Searches for the "auth.xml" file if the user has previously specified
     * that he/she wishes to store user information.
     * 
     * @return the auth.xml file.
     */
    private File locateLoginSettings() {
        context = this;
        String path = locateFilePath(context);
        File loginAuth = new File(path + "/shared_prefs/auth.xml");
        return loginAuth;
    }

    /**
     * Locates the path to a device's user file directory.
     * 
     * @param acitivty
     *            The current activity.
     * @return A path leading to the device's user file directory.
     */
    private String locateFilePath(Context acitivty) {
        String dirtyPath = context.getFilesDir().toString();
        String path = dirtyPath.substring(0, dirtyPath.indexOf("file"));
        return path;
    }

    /**
     * Checks if a user has previously logged in and stored their information to
     * the auth.xml file.
     * 
     * @param loginSettings
     *            The user's file containing their login settings.
     * @return True: if the file exists and contains the necessary information.
     *         False: if the file does not exist.
     */
    private boolean checkLoggedInBefore(File loginSettings) {
        if (loginSettings.exists() && hasUNI() && hasPassword()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the Login Preferences file contains a password.
     * 
     * @return True: if the password value exists. False: if the password value
     *         does not exist.
     */
    private boolean hasPassword() {
        return (loginPreferences.getString("password") != null) ? true : false;
    }

    /**
     * Checks if the Login Preferences file contains a UNI.
     * 
     * @return True: if the UNI value exists. False: if the UNI value does not
     *         exist.
     */
    private boolean hasUNI() {
        return (loginPreferences.getString("uni") != null) ? true : false;
    }

    /**
     * Automatically fills the UNI and Password TextViews.
     */
    private void autoFillTextField() {
        fillUniTextField();
        fillPasswordTextField();
    }

    /**
     * Automatically fills the Password TextView.
     */
    private void fillPasswordTextField() {
        passwordTextField.setText(loginPreferences.getString("password"));
    }

    /**
     * Automatically fills the UNI TextView.
     */
    private void fillUniTextField() {
        uniTextField.setText(loginPreferences.getString("uni"));
    }

    /**
     * Stores the user's information on request to the login preferences file.
     */
    private void storeLoginPreferences() {
        uni = retrieveTextFromTextField(uniTextField);
        password = retrieveTextFromTextField(passwordTextField);
        loginPreferences.put("uni", uni);
        loginPreferences.put("password", password);
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

        boolean error = false;
        View focusView = null;

        if (TextUtils.isEmpty(uni)) {
            focusView = missingFieldFailedLogin(uniTextField, focusView);
            error = true;
        }

        if (TextUtils.isEmpty(password)) {
            focusView = missingFieldFailedLogin(passwordTextField, focusView);
            error = true;
        } else if (checkPasswordIsShort()) {
            focusView = invalidPasswordFails(focusView);
            error = true;
        }

        if (error) {
            focusView.requestFocus();
        } else {
            proceedWithLogin();
        }
    }

    /**
     * Resets Error Notifications
     */
    private void resetErrorNotification() {
        uniTextField.setError(null);
        passwordTextField.setError(null);
    }

    /**
     * Retrieves text from the given textField
     * 
     * @param textField
     *            The TextView that holds text.
     * @return A string containing the text from the specified Text Field.
     */
    private String retrieveTextFromTextField(TextView textField) {
        return textField.getText().toString();
    }

    /**
     * Checks if the password is too short.
     * 
     * @return True: if the password is too short. False: if the password meets
     *         the length requirement.
     */
    private boolean checkPasswordIsShort() {
        return password.length() < 8 ? true : false;
    }

    /**
     * Sets missing filed error message for a given TextView and focuses on said
     * TextView.
     * 
     * @param textField
     *            The TextView with the error
     * @param focusView
     *            The View that will be focused on
     * @return The View which is being focused on.
     */
    private View missingFieldFailedLogin(TextView textField, View focusView) {
        textField.setError(getString(R.string.error_field_required));
        focusView = textField;
        return focusView;
    }

    /**
     * Sets invalid error message for a given TextView and focuses on said
     * TextView.
     * 
     * @param textField
     *            The TextView with the error
     * @param focusView
     *            The View that will be focused on
     * @return The View which is being focused on.
     */
    private View invalidPasswordFails(View focusView) {
        passwordTextField.setError(getString(R.string.error_invalid_password));
        focusView = passwordTextField;
        return focusView;
    }

    /**
     * Proceed with Login method.
     */
    private void proceedWithLogin() {
        loginStatusMessageField.setText(R.string.login_progress_signing_in);
        showProgress(true);
        loginTask = new UserLoginTask();
        loginTask.execute((Void) null);
        Intent main = new Intent(Login.this, Main.class);
        startActivity(main);
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
            // TODO: Secure the login method for when the authentication
            // actually works.

            Intent loginIntent = new Intent(getApplicationContext(),
                    RestAuthService.class);
            loginIntent.putExtra("uni", uni);
            loginIntent.putExtra("password", password);
            startService(loginIntent);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

}