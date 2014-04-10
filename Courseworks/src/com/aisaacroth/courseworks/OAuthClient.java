package com.aisaacroth.courseworks;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/*******************************************************************************
 * An Oauth Client for the Courseworks Android application. Interacts with the
 * web version of Courseworks and accepts user-based information. Performs
 * authentication based on Oauth specifications.
 * 
 * @author: Alexander Roth
 * @date: 2014-04-01
 * @version: 0.1
 ******************************************************************************/
public class OAuthClient {

	public static void login(AccountManager accountManager, Context context) {
		Bundle options = new Bundle();

		// TODO: This is a place holder. When we have it prepared for Columbia
		// servers, we will adjust as necessary.
		Account[] profiles = accountManager.getAccountsByType("Columbia");
		Account user = profiles[0];
		// accountManager.getAuthToken(user, "oauth2:", null, context, new
		// OnTokenAcquired(), null);
	}

	// private class OnTokenAcquired implements AccountManagerCallback<Bundle> {

	// @Override
	// public void run(AccountManagerFuture<Bundle> result) {
	// // try {
	// // Bundle bundle = result.getResult();
	//
	// Intent launch = (Intent) bundle.get(AccountManager.KEY_INTENT);
	// if (launch != null) {
	// // Some form of start activity. Yet to figure out what.
	// } else {
	// String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
	//
	// }
	// // }
	//
	// }

	// }
}
