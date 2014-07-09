package com.aisaacroth.courseworks.test;

import com.aisaacroth.courseworks.views.Login;
import com.aisaacroth.courseworks.views.Main;
import com.aisaacroth.courseworks.R;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginTest extends ActivityInstrumentationTestCase2<Login> {
    private Login testLoginActivity;
    private EditText uniTextField;
    private EditText passwordTextField;
    private CheckBox rememberCheckBox;
    private Button signInButton;

    public LoginTest() {
        super(Login.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        testLoginActivity = getActivity();
        uniTextField = (EditText) testLoginActivity.findViewById(R.id.uni);
        passwordTextField = (EditText) testLoginActivity
                .findViewById(R.id.password);
        rememberCheckBox = (CheckBox) testLoginActivity
                .findViewById(R.id.remember_me);
        signInButton = (Button) testLoginActivity
                .findViewById(R.id.sign_in_button);
    }

    public void testErrorMessageForPassword() {
        sendToLoginForm(true);
        assertEquals("This password is too short",
                getStringFromErrorField(passwordTextField));
    }

    public void testErrorMessagesAppearOnClick() {
        String testErrorMessage = "This field is required";
        TouchUtils.clickView(this, signInButton);
        assertEquals(getStringFromErrorField(uniTextField), testErrorMessage);
        assertEquals(getStringFromErrorField(passwordTextField),
                testErrorMessage);
    }

    private String getStringFromErrorField(EditText textField) {
        return textField.getError().toString();
    }

    public void testLayoutCompletely() {
        testLayoutExist();
        testEmptyFields();
    }
    
    public void testEmptyFields() {
        assertTrue(getStringFromEditText(uniTextField).equals(""));
        assertTrue(getStringFromEditText(passwordTextField).equals(""));
        assertTrue(!rememberCheckBox.isChecked());
    }

    public void testLayoutExist() {
        assertNotNull(uniTextField);
        assertNotNull(passwordTextField);
        assertNotNull(rememberCheckBox);
        assertNotNull(signInButton);
    }

    public void testPostPasswordToField() {
        String testPassword = "password";
        focusOnTextField(passwordTextField);
        this.sendKeys("P A S S W O R D");
        assertEquals(testPassword, getStringFromEditText(passwordTextField));
    }

    public void testPostUniToField() {
        String testUNI = "air2112";
        focusOnTextField(uniTextField);
        this.sendKeys("A I R 2 1 1 2");
        assertEquals(testUNI, getStringFromEditText(uniTextField));
    }
    
    private String getStringFromEditText(EditText textField) {
        return textField.getText().toString();
    }

    public void testSelectCheckBox() {
        testLoginActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                rememberCheckBox.requestFocus();
            }
        });

        this.select();
        assertTrue(rememberCheckBox.isChecked());
    }

    public void testSuccessfulLogin() {
        ActivityMonitor monitor = getInstrumentation().addMonitor(
                Main.class.getName(), null, false);
        sendToLoginForm(false);
        Main mainActivity = (Main) monitor.waitForActivityWithTimeout(2000);
        assertNotNull(mainActivity);
    }

    private void sendToLoginForm(boolean error) {
        if (error) {
            focusOnTextField(uniTextField);
            this.sendKeys("A I R 2 1 1 2");
            focusOnTextField(passwordTextField);
            this.sendKeys("T E S T E R R");
        } else {
            focusOnTextField(uniTextField);
            this.sendKeys("A I R 2 1 1 2");
            focusOnTextField(passwordTextField);
            this.sendKeys("P A S S W O R D");
        }

        TouchUtils.clickView(this, signInButton);
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
    }

}