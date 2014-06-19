package com.aisaacroth.courseworks.views;

import com.aisaacroth.courseworks.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/*******************************************************************************
 * The Splash screen for the CourseWorks Android application. When the
 * application is called, this splash screen will hold while the program gets
 * retrieves all necessary information. TODO: Implement so that the main
 * activity checks whether there is a file that already contains login
 * information.
 * 
 * @author Alexander Roth
 * @date 2014-02-24
 ******************************************************************************/
public class Splash extends Activity {

	// Simulates the load time.
	private static int SPLASH_TIME_OUT = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// Go to the Login screen after this is implemented.
				// TODO: Move back to Login class. Only set to Main for dev.
				Intent i = new Intent(Splash.this, Main.class);
				startActivity(i);

				// Close this activity
				finish();

			}
		}, SPLASH_TIME_OUT);

	}

}