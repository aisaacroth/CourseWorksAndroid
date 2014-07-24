package com.aisaacroth.courseworks.web;

import com.aisaacroth.courseworks.structures.User;

/**
 * Reconstructs the current User's Announcements XML file returned from the 
 * Courseworks API.
 * 
 * @author Alexander Roth
 * @date 2014-07-24
 */
public class AnnouncementReconstructor extends Reconstructor {

    public AnnouncementReconstructor(User user) {
        this.user = user;
        this.xmlString = null;
        this.response = null;
    }
}
