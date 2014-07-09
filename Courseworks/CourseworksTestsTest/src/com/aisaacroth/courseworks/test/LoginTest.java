package com.aisaacroth.courseworks.test;

import com.aisaacroth.courseworks.views.Login;
import com.aisaacroth.courseworks.R;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginTest extends ActivityInstrumentationTestCase2<Login> {
    private Solo solo;
    private Login testLoginActivity;
    private EditText uniTextField;
    private EditText passwordTextField;
    private CheckBox rememberMe;
    private Button signInButton;

    public LoginTest() {
        super(Login.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        setActivityInitialTouchMode(false);
        testLoginActivity = getActivity();
        uniTextField = (EditText) testLoginActivity.findViewById(R.id.uni);
        passwordTextField = (EditText) testLoginActivity
                .findViewById(R.id.password);
        rememberMe = (CheckBox) testLoginActivity
                .findViewById(R.id.remember_me);
        signInButton = (Button) testLoginActivity
                .findViewById(R.id.sign_in_button);
    }

    public void testEmptyFields() {
        assertTrue(getStringFromEditText(uniTextField).equals(""));
        assertTrue(getStringFromEditText(passwordTextField).equals(""));
        assertTrue(!rememberMe.isChecked());
    }

    private String getStringFromEditText(EditText textField) {
        return textField.getText().toString();
    }

    public void testUniTextField() {
        String testUNI = "air2112";

        focusOnTextField(uniTextField);
        this.sendKeys("A I R 2 1 1 2");

        assertEquals(getStringFromEditText(uniTextField), testUNI);
    }

    public void testPasswordTextField() {
        String testPassword = "password";

        focusOnTextField(passwordTextField);
        this.sendKeys("P A S S W O R D");

        assertEquals(getStringFromEditText(passwordTextField), testPassword);
    }

    public void testCheckBox() {
        testLoginActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                rememberMe.requestFocus();
            }
        });

        this.select();

        assertTrue(rememberMe.isChecked());
    }

    public void testMissingPassword() {

    }

    public void testPasswordMissing() {

    }

    public void testPasswordIsTooShort() {

    }

    public void testUniIsMissing() {

    }

    public void testUniIsTooShort() {

    }

    private void focusOnTextField(final EditText textField) {
        testLoginActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                textField.requestFocus();
            }
        });
    }

    private void select() {
        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        solo.finishOpenedActivities();
    }

}
