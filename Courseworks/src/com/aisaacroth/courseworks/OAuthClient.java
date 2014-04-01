package com.aisaacroth.courseworks;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;


/**
 * An Oauth Client for the Courseworks Android application. Interacts with the
 * web version of Courseworks and accepts user-based information. Performs
 * authentication based on Oauth specifications.
 * 
 * @author Alexander Roth
 * @date 2014-04-01
 * @version 0.1
 */
public class OAuthClient {

	/*
	 * TODO: Implement a custom API for CAS and Courseworks, if I wanted to use
	 * Scribe. TODO: Look into using Google's signpost library; seems more
	 * readily available at the current moment.
	 */
	private OAuthConsumer mConsumer;
	private OAuthProvider mProvider;
	private String mCallbackUrl;

	public OAuthClient(String consumerKey, String consumerSecret, String scope,
			String callbackUrl) {
		mConsumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);

	}

}
