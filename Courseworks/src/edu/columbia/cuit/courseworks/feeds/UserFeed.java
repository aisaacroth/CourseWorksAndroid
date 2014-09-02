package edu.columbia.cuit.courseworks.feeds;

import edu.columbia.cuit.courseworks.exceptions.FailedConnectionException;
import edu.columbia.cuit.courseworks.reconstructors.UserReconstructor;
import edu.columbia.cuit.courseworks.structures.User;

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

        url = "https://courseworks.columbia.edu/direct/user/current.xml";
        userReconstructor = new UserReconstructor();
    }

    @Override
    protected User doInBackground(String... params) {
        try {
            currentUser = userReconstructor.constructUser(url, params[0]);
        } catch (FailedConnectionException e) {
            e = new FailedConnectionException(
                    "There appears to be a connection error.");
            e.printStackTrace();
        }

        logUser();
        return currentUser;
    }

    private void logUser() {
        Log.d("USER", "NAME: " + currentUser.getDisplayName());
        Log.d("USER", "UNI: " + currentUser.getUni());
        Log.d("USER", "EMAIL: " + currentUser.getEmailAddress());
        Log.d("USER", "ENTITY ID: " + currentUser.getUserID());
    }

}
