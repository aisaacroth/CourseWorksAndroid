package com.aisaacroth.courseworks.test;

import com.aisaacroth.courseworks.views.Login;
import com.aisaacroth.courseworks.R;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginTest extends ActivityInstrumentationTestCase2<Login> {

	private Login testLoginActivity;
	private EditText uniTextField;
	private EditText passwordTextField;
	private CheckBox rememberMe;

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
		rememberMe = (CheckBox) testLoginActivity
				.findViewById(R.id.remember_me);
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
		testLoginActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				uniTextField.requestFocus();
			}
		});

		String testUNI = "air2112";
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		this.sendKeys("A I R 2 1 1 2");

		assertEquals(getStringFromEditText(uniTextField), testUNI);
	}

	public void testPasswordTextField() {
		testLoginActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				passwordTextField.requestFocus();
			}
		});
		
		String testPassword = "password";
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
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
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		assertTrue(rememberMe.isChecked());
	}
	
	public void testCompleteLogin() {

	}

}
