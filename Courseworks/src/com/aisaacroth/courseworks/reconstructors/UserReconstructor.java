package com.aisaacroth.courseworks.reconstructors;

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
        this.xmlString = null;
    }

    public User constructUser(String url) {
        // setUserXml();
        setUser();

        return user;
    }

    private void setUser() {
        user.setUni(parseFromTag("displayId"));
        user.setUserID(parseFromTag("id"));
        user.setDisplayName(parseFromTag("displayName"));
        user.setEmailAddress(parseFromTag("email"));
    }
}