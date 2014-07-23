package com.aisaacroth.courseworks.web;

import java.io.IOException;

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
        this.response = null;
    }

    public User constructUser(String url) {
        try {
            setResponse(url);
            setUserXml();
            setUser();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    private void setUser() {
        user.setUni(parseFromTag("displayId"));
        user.setUserID(parseFromTag("id"));
        user.setDisplayName(parseFromTag("displayName"));
        user.setEmailAddress(parseFromTag("email"));
    }
}