package edu.columbia.cuit.courseworks.views;

import edu.columbia.cuit.courseworks.R;
import edu.columbia.cuit.courseworks.adapters.SharedPreferencesAdapter;
import edu.columbia.cuit.courseworks.dialogs.NetworkDialog;
import edu.columbia.cuit.courseworks.dialogs.NetworkDialog.NetworkDialogListener;
import edu.columbia.cuit.courseworks.exceptions.FailedConnectionException;
import edu.columbia.cuit.courseworks.utils.*;

import android.animation.*;

import android.annotation.TargetApi;
import android.app.*;
import android.content.*;
import android.graphics.Typeface;
import android.os.*;
import android.text.*;
import android.text.method.LinkMovementMethod;
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
public class LoginView extends Activity implements NetworkDialogListener {

    private UserLoginTask loginTask = null;

    private String uni;
    private String password;
    private String grantingTicket;
    private final String FORGOT_PASSWORD_LINK = "<a href=\"https://uniapp.cc.columbia.edu/acctmanage/resetpasswd\">Forgot Password</a>";

    private SharedPreferencesAdapter loginPreferences;

    private EditText uniTextField;
    private EditText passwordTextField;
    private View loginFormView;
    private View loginStatusView;
    private TextView loginStatusMessageField;
    private CheckBox rememberMeCheckBox;
    private Button signInButton;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        setUpLoginForm();

        loginPreferences = new SharedPreferencesAdapter(this, "auth");

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
                        storeRememberBooleanIfChecked();
                        attemptLogin();
                    }
                });
    }

    private void storeRememberBooleanIfChecked() {
        loginPreferences.put("rememberMe",
                String.valueOf(rememberMeCheckBox.isChecked()));
    }

    private void setUpLoginForm() {
        setViews();
        setFonts();
        setForgotPasswordLink();
    }

    private void setViews() {
        uniTextField = (EditText) findViewById(R.id.uni);
        passwordTextField = (EditText) findViewById(R.id.password);
        rememberMeCheckBox = (CheckBox) findViewById(R.id.remember_me);
        loginFormView = findViewById(R.id.login_form);
        loginStatusView = findViewById(R.id.login_status);
        loginStatusMessageField = (TextView) findViewById(R.id.login_status_message);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);
    }

    private void setFonts() {
        setLightFonts();
        setMediumFonts();
    }

    private void setLightFonts() {
        Typeface lightFont = Typeface.createFromAsset(getAssets(),
                "Roboto-Light.ttf");
        uniTextField.setTypeface(lightFont);
        passwordTextField.setTypeface(lightFont);
        rememberMeCheckBox.setTypeface(lightFont);
        forgotPassword.setTypeface(lightFont);
    }

    private void setMediumFonts() {
        Typeface mediumFont = Typeface.createFromAsset(getAssets(),
                "Roboto-Medium.ttf");
        signInButton.setTypeface(mediumFont);
    }

    private void setForgotPasswordLink() {
        forgotPassword.setText(Html.fromHtml(FORGOT_PASSWORD_LINK));
        forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
    }
    
    public void attemptLogin() {
        if (loginTask != null) {
            return;
        }

        resetErrorNotification();

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
                login();
            } catch (FailedConnectionException e) {
                e = new FailedConnectionException(
                        "There appears to be a connection error. Please try again later");
                showNetworkDialog();

            }

            if (serviceTicket != null)
                worked = true;
            return worked;
        }

        private void login() throws FailedConnectionException {
            grantingTicket = CASAuthUtil.getGrantingTicket(uni, password);
            startTimer();
            serviceTicket = CASAuthUtil.login(uni, grantingTicket);
        }

        private void startTimer() {
            Intent timeoutIntent = new Intent(LoginView.this,
                    ExpirationTimer.class);
            startService(timeoutIntent);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            loginTask = null;
            showProgress(false);

            if (success) {
                finish();
                Intent mainIntent = new Intent(LoginView.this,
                        HiddenWebView.class);
                mainIntent.putExtra("ServiceTicket", serviceTicket);
                LoginView.this.startActivity(mainIntent);
            } else {
                passwordTextField
                        .setError(getString(R.string.error_incorrect_signin));
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
        getMenuInflater().inflate(R.menu.login_view, menu);
        return true;
    }

    public void showNetworkDialog() {
        DialogFragment dialog = new NetworkDialog();
        dialog.show(getFragmentManager(), "NetworkDialogFragment");
    }

    public void onDialogPositiveClick(DialogFragment dialog) {
    }
}