package com.aisaacroth.courseworks.reconstructors;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.aisaacroth.courseworks.structures.Announcement;
import com.aisaacroth.courseworks.structures.User;

/**
 * Reconstructs the current User's Announcements XML file returned from the
 * Courseworks API.
 * 
 * @author Alexander Roth
 * @date 2014-07-24
 */
public class AnnouncementReconstructor extends Reconstructor {
    
    private Announcement announcement;

    public AnnouncementReconstructor(User user) throws ClientProtocolException,
            IOException {
        this.user = user;
        this.xmlString = null;
        this.announcement = null;
    }
    
    private void setAnnouncement() {
        
    }
    
    public Announcement constructAnnouncement(String url) throws ClientProtocolException, IOException {
        setXMLString(url);
        setAnnouncement();
        return announcement;
    }

    public String parseDateString() {
        String dateSubTag = parseFromTag("createdOn");
        String dateAttribute = "date=\"";
        int startIndexDate = dateSubTag.indexOf(dateAttribute);
        int endIndexDate = dateSubTag.indexOf("T");
        return dateSubTag.substring(startIndexDate + dateAttribute.length(),
                endIndexDate);
    }

}