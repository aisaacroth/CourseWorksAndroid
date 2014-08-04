package com.aisaacroth.courseworks.test;

import java.io.File;

import com.aisaacroth.courseworks.adapters.SharedPreferencesAdapter;
import com.aisaacroth.courseworks.views.*;
import com.aisaacroth.courseworks.R;
import com.robotium.solo.Solo;

import android.app.Instrumentation;
import android.test.*;
import android.test.suitebuilder.annotation.*;
import android.widget.*;

/**
 * The test suite for the UI elements of the Login Activity.
 * 
 * @author Alexander Roth
 * @date 2014-07-09
 */
public class LoginTest extends ActivityInstrumentationTestCase2<Login> {
    private Button signInButton;
    private CheckBox rememberCheckBox;
    private EditText uniTextField;
    private EditText passwordTextField;
    private Login testLoginActivity;
    private Solo testSolo;
    private SharedPreferencesAdapter testPreferences;

    public LoginTest() {
        super(Login.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        initTestObjects();
        cleanLoginFile();
    }
    
    private void initTestObjects() {
        testLoginActivity = getActivity();
        passwordTextField = (EditText) testLoginActivity
                .findViewById(R.id.password);
        rememberCheckBox = (CheckBox) testLoginActivity
                .findViewById(R.id.remember_me);
        signInButton = (Button) testLoginActivity
                .findViewById(R.id.sign_in_button);
        testSolo = new Solo(getInstrumentation(), getActivity());
        uniTextField = (EditText) testLoginActivity.findViewById(R.id.uni);
    }
    
    private void cleanLoginFile() {
        testPreferences = new SharedPreferencesAdapter(testLoginActivity, "auth");
        testPreferences.clear();
    }

    @LargeTest
    public void testAuthFileExists() {
        clickRememberMe();
        sendToLoginForm(false);
        File testFile = testPreferences.locateLoginSettings(testLoginActivity);
        assertNotNull(testFile);
    }
    
    @SmallTest
    public void testEmptyFields() {
        assertTrue(getStringFromEditText(uniTextField).equals(""));
        assertTrue(getStringFromEditText(passwordTextField).equals(""));
        assertTrue(!rememberCheckBox.isChecked());
    }

    @LargeTest
    public void testErrorMessageForPassword() throws InterruptedException {
        sendToLoginForm(true);
        assertEquals("This password is too short",
                getStringFromErrorField(passwordTextField));
    }

    @MediumTest
    public void testErrorMessagesAppearOnClick() {
        String testErrorMessage = "This field is required";
        TouchUtils.clickView(this, signInButton);
        assertEquals(testErrorMessage, getStringFromErrorField(uniTextField));
        assertEquals(testErrorMessage,
              getStringFromErrorField(passwordTextField));
        testSolo.getCurrentActivity().finish();
    }

    private String getStringFromErrorField(EditText textField) {
        return textField.getError().toString();
    }

//      @SmallTest
//    public void testLayoutCompletely() {
//        testLayoutExist();
//        testEmptyFields();
//    }

    @SmallTest
    public void testLayoutExist() {
        assertNotNull(uniTextField);
        assertNotNull(passwordTextField);
        assertNotNull(rememberCheckBox);
        assertNotNull(signInButton);
    }

    @LargeTest
    public void testLoginIsFinished() {
        sendToLoginForm(false);
        testSolo.assertCurrentActivity("Did not leave Login class", HiddenWebView.class);
        assertTrue(testLoginActivity.isFinishing());
        testSolo.getCurrentActivity().finish();
    }

    @LargeTest
    public void testLoginSuccessful() {
        sendToLoginForm(false);
        testSolo.assertCurrentActivity("Did not leave Login Class", HiddenWebView.class);
        testSolo.getCurrentActivity().finish();
    }

    private void sendToLoginForm(boolean error) {
        Instrumentation passwordField = getInstrumentation();
        if (error) {
            sendKeys("A I R 2 1 1 2 ENTER");
            passwordField.sendStringSync("testerr");
        } else {
            sendKeys("A I R 2 1 1 2 ENTER");
            passwordField.sendStringSync("BA115hp34");
        }

        TouchUtils.clickView(this, signInButton);

    }

    @SmallTest
    public void testPostPasswordToField() {
        String testPassword = "password";
        sendKeys("ENTER");
        sendKeys("P A S S W O R D + ENTER");
        assertEquals("This field is required",
                getStringFromErrorField(uniTextField));
        assertEquals(testPassword, getStringFromEditText(passwordTextField));
    }

    @SmallTest
    public void testPostUniToField() {
        String testUNI = "air2112";
        sendKeys("A I R 2 1 1 2");
        assertEquals(testUNI, getStringFromEditText(uniTextField));
    }

    private String getStringFromEditText(EditText textField) {
        return textField.getText().toString();
    }

    @MediumTest
    public void testSelectCheckBox() {
        clickRememberMe();
        assertTrue(rememberCheckBox.isChecked());
    }
   

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    private void clickRememberMe() {
        TouchUtils.clickView(this, rememberCheckBox);
    }

}