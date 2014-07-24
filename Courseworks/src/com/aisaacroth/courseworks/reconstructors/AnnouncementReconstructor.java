package com.aisaacroth.courseworks.reconstructors;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.aisaacroth.courseworks.structures.User;

/**
 * Reconstructs the current User's Announcements XML file returned from the
 * Courseworks API.
 * 
 * @author Alexander Roth
 * @date 2014-07-24
 */
public class AnnouncementReconstructor extends Reconstructor {

    public AnnouncementReconstructor(User user) throws ClientProtocolException,
            IOException {
        this.user = user;
        this.xmlString = null;
    }

    public String parseDateString() {
        String dateSubTag = parseFromTag("createdOn");
        int startIndexDate = dateSubTag.indexOf("date=");
        int endIndexDate = dateSubTag.indexOf("T");
        return null;
    }

}