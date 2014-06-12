package com.aisaacroth.courseworks;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/*******************************************************************************
 * The Home class. TODO: Will act as the homepage for the Courseworks app.
 * 
 * @author Alexander Roth
 * @date 2014-02-24
 ******************************************************************************/
public class Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}