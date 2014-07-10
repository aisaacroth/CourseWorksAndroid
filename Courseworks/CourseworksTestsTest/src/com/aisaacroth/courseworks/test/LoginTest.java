package com.aisaacroth.courseworks.test;

import com.aisaacroth.courseworks.views.Login;
import com.aisaacroth.courseworks.views.Main;
import com.aisaacroth.courseworks.R;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginTest extends ActivityInstrumentationTestCase2<Login> {
    private Login testLoginActivity;
    private EditText uniTextField;
    private EditText passwordTextField;
    private CheckBox rememberCheckBox;
    private Button signInButton;
    private Solo testSolo;

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
        testSolo = new Solo(getInstrumentation(), getActivity());
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
        assertTrue(testSolo.searchText("This password is too short"));
    }
    

    @MediumTest
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
        testSolo.assertCurrentActivity("Did not leave Login Class", Main.class);
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
            testSolo.typeText(uniTextField, "air2112");
            testSolo.typeText(passwordTextField, "testerr");
        } else {
            testSolo.typeText(uniTextField, "air2112");
            testSolo.typeText(passwordTextField, "password");
        }

        testSolo.clickOnView(getRObject(R.id.sign_in_button));
    }

    @SmallTest
    public void testPostPasswordToField() {
        String testPassword = "password";
        testSolo.typeText(passwordTextField, testPassword);
        assertEquals(testPassword, getStringFromEditText(passwordTextField));
    }

    @SmallTest
    public void testPostUniToField() {
        String testUNI = "air2112";
        testSolo.typeText(uniTextField, testUNI);
        assertEquals(testUNI, getStringFromEditText(uniTextField));
    }

    private String getStringFromEditText(EditText textField) {
        return textField.getText().toString();
    }

    @MediumTest
    public void testSelectCheckBox() {
        testSolo.clickOnView(getRObject(R.id.remember_me));
        assertTrue(testSolo.isCheckBoxChecked(0));
    }

    private View getRObject(int RValue) {
        return testSolo.getView(RValue);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}