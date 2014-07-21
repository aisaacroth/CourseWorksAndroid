package com.aisaacroth.courseworks.views;

import java.io.File;
import java.io.IOException;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.adapters.SharedPreferencesAdapter;
import com.aisaacroth.courseworks.web.CASRestAuthenticator;

import android.animation.*;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.*;
import android.os.*;
import android.text.TextUtils;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

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
    private String grantingTicket;

    private SharedPreferencesAdapter loginPreferences;
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
            proceedWithLogin();
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
                            storeUsername();
                        } else {
                            loginPreferences.clear();
                        }
                        attemptLogin();
                    }
                });
    }

    private void setUpLoginForm() {
        uniTextField = (EditText) findViewById(R.id.uni);
        passwordTextField = (EditText) findViewById(R.id.password);
        rememberMeCheckBox = (CheckBox) findViewById(R.id.remember_me);
        loginFormView = findViewById(R.id.login_form);
        loginStatusView = findViewById(R.id.login_status);
        loginStatusMessageField = (TextView) findViewById(R.id.login_status_message);
    }

    private SharedPreferencesAdapter createLoginPreferences() {
        return new SharedPreferencesAdapter(this, "auth");
    }

    private File locateLoginSettings() {
        context = this;
        String path = locateFilePath(context);
        File loginAuth = new File(path + "/shared_prefs/auth.xml");
        return loginAuth;
    }

    private String locateFilePath(Context acitivty) {
        String dirtyPath = context.getFilesDir().toString();
        String path = dirtyPath.substring(0, dirtyPath.indexOf("file"));
        return path;
    }

    private boolean checkLoggedInBefore(File loginSettings) {
        if (loginSettings.exists() && hasUNI()) {
            return true;
        }
        return false;
    }

    private boolean hasUNI() {
        return (loginPreferences.getString("uni") != null) ? true : false;
    }

    private void storeUsername() {
        uni = retrieveTextFromTextField(uniTextField);
        loginPreferences.put("uni", uni);
    }

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

    private void resetErrorNotification() {
        uniTextField.setError(null);
        passwordTextField.setError(null);
    }

    private String retrieveTextFromTextField(TextView textField) {
        return textField.getText().toString();
    }

    private boolean checkPasswordIsShort() {
        return password.length() < 8 ? true : false;
    }

    private View missingFieldFailedLogin(TextView textField, View focusView) {
        textField.setError(getString(R.string.error_field_required));
        focusView = textField;
        return focusView;
    }

    private View invalidPasswordFails(View focusView) {
        passwordTextField.setError(getString(R.string.error_invalid_password));
        focusView = passwordTextField;
        return focusView;
    }

    private void proceedWithLogin() {
        loginStatusMessageField.setText(R.string.login_progress_signing_in);
        showProgress(true);
        loginTask = new UserLoginTask();
        loginTask.execute((Void) null);
    }

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

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        String serviceTicket = null;

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: Secure the login method for when the authentication
            // actually works.
            boolean worked = false;

            try {
                if (password != null) {
                    grantingTicket = CASRestAuthenticator.getGrantingTicket(
                            uni, password);
                    storeGrantingTicketIfChecked(grantingTicket);
                } else {
                    grantingTicket = loginPreferences.getString("ticket");
                }
                
                serviceTicket = CASRestAuthenticator.login(uni, grantingTicket);
                if (serviceTicket != null)
                    worked = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return worked;
        }

        private void storeGrantingTicketIfChecked(String grantingTicket) {
            if (rememberMeCheckBox.isChecked()) {
                loginPreferences.put("ticket", grantingTicket);
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            loginTask = null;
            showProgress(false);

            if (success) {
                finish();
                Intent mainIntent = new Intent(Login.this, Main.class);
                mainIntent.putExtra("ServiceTicket", serviceTicket);
                Login.this.startActivity(mainIntent);
            } else {
                passwordTextField
                        .setError(getString(R.string.error_incorrect_login));
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