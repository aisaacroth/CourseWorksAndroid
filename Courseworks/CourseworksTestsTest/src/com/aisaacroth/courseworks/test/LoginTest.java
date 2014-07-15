package com.aisaacroth.courseworks.test;

import com.aisaacroth.courseworks.views.Login;
import com.aisaacroth.courseworks.views.Main;
import com.aisaacroth.courseworks.R;
import com.robotium.solo.Solo;

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

    public LoginTest() {
        super(Login.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
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
    }

    private String getStringFromErrorField(EditText textField) {
        return textField.getError().toString();
    }

    @SmallTest
    public void testLayoutCompletely() {
        testLayoutExist();
        testEmptyFields();
    }

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
        testSolo.assertCurrentActivity("Did not leave Login class", Main.class);
        assertTrue(testLoginActivity.isFinishing());
        testSolo.getCurrentActivity().finish();
    }

    @LargeTest
    public void testLoginSuccessful() {
        sendToLoginForm(false);
        testSolo.assertCurrentActivity("Did not leave Login Class", Main.class);
        testSolo.getCurrentActivity().finish();
    }

    private void sendToLoginForm(boolean error) {
        if (error) {
            sendKeys("A I R 2 1 1 2 ENTER");
            sendKeys("T E S T E R R");
        } else {
            sendKeys("A I R 2 1 1 2 ENTER");
            sendKeys("P A S S W O R D");
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
        TouchUtils.clickView(this, rememberCheckBox);
        assertTrue(rememberCheckBox.isChecked());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}