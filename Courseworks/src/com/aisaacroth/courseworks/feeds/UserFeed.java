package com.aisaacroth.courseworks.feeds;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.aisaacroth.courseworks.reconstructors.UserReconstructor;
import com.aisaacroth.courseworks.structures.User;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Accesses the Courseworks API and pulls in information related to the User's
 * profile.
 * 
 * @author Alexander Roth
 * @date 2014-07-30
 */
public class UserFeed extends AsyncTask<String, Void, User> {

    private User currentUser;
    private UserReconstructor userReconstructor;
    private String url;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        //TODO: Switch to Production servers.
        url = "https://sakaidev.cc.columbia.edu/direct/user/current.xml";
        try {
            userReconstructor = new UserReconstructor();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected User doInBackground(String... params) {
        try {
            currentUser = userReconstructor.constructUser(url, params[0]);
            logUser();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentUser;
    }

    private void logUser() {
        Log.d("USER", "NAME: " + currentUser.getDisplayName());
        Log.d("USER", "UNI: " + currentUser.getUni());
        Log.d("USER", "EMAIL: " + currentUser.getEmailAddress());
        Log.d("USER", "ENTITY ID: " + currentUser.getUserID());
    }

}
