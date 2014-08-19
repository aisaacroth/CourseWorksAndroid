package com.aisaacroth.courseworks.reconstructors;

import com.aisaacroth.courseworks.exceptions.FailedConnectionException;
import com.aisaacroth.courseworks.structures.User;

/**
 * Reconstructs the user's identity via XML returned from the Courseworks API.
 * 
 * @author Alexander Roth
 * @date 2014-07-21
 */
public class UserReconstructor extends Reconstructor {

    public UserReconstructor() {
        this.user = new User();
        this.dataString = null;
    }

    private void setUser() {
        user.setUni(parseFromTag("displayId"));
        user.setUserID(parseFromTag("id"));
        user.setDisplayName(parseFromTag("displayName"));
        user.setEmailAddress(parseFromTag("email"));
    }

    public User constructUser(String url, String sessionID)
            throws FailedConnectionException {
        setDataString(url, sessionID);
        setUser();
        return user;
    }

}