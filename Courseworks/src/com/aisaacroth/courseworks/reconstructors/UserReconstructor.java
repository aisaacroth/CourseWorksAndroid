package com.aisaacroth.courseworks.reconstructors;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.aisaacroth.courseworks.structures.User;

/**
 * Reconstructs the user's identity via XML returned from the Courseworks API.
 * 
 * @author Alexander Roth
 * @date 2014-07-21
 */
public class UserReconstructor extends Reconstructor {

    public UserReconstructor() throws ClientProtocolException, IOException {
        this.user = new User();
        this.xmlString = null;
    }

    private void setUser() {
        user.setUni(parseFromTag("displayId"));
        user.setUserID(parseFromTag("id"));
        user.setDisplayName(parseFromTag("displayName"));
        user.setEmailAddress(parseFromTag("email"));
    }

    public User constructUser(String url, String sessionID)
            throws ClientProtocolException, IOException {
        setXMLString(url, sessionID);
        setUser();
        return user;
    }

}