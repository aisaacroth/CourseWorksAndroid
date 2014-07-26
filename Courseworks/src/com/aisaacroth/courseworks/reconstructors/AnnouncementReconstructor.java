package com.aisaacroth.courseworks.reconstructors;

import java.io.IOException;
import java.util.ArrayList;

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
        announcement.setPostedDate(parseDateString());
        announcement.setTitle(parseFromTag("title"));
        announcement.setProfessorName(parseFromTag("createdByDisplayName"));
        announcement.setBodyText(parseFromTag("body"));
        announcement.setClassId(parseFromTag("siteId"));
    }

    public ArrayList<Announcement> constructAnnouncements(String url)
            throws ClientProtocolException, IOException {
        setXMLString(url);
        setAnnouncement();
        return null;
    }

    public String parseDateString() {
        String dateSubTag = parseFromTag("createdOn");
        String dateAttribute = "date=\"";
        int startIndexDate = dateSubTag.indexOf(dateAttribute);
        int endIndexDate = dateSubTag.indexOf("T");
        int startIndexLength = startIndexDate + dateAttribute.length();
        return dateSubTag.substring(startIndexLength, endIndexDate);
    }
    
    public String[] parseAnnouncementStrings() {
        removeCollectionTag();
        String[] announcementXMLs = xmlString.split("</announcement>");
        removeAnnouncementTag(announcementXMLs);
        return announcementXMLs;
    }
    
    private void removeCollectionTag() {
        int startIndex = this.xmlString.indexOf(">") + 1;
        int endIndex = this.xmlString.indexOf("</announcement_collection");
        this.xmlString = this.xmlString.substring(startIndex, endIndex);
    }
    
    private void removeAnnouncementTag(String[] announcements) {
        for (int i = 0; i < announcements.length; i++) {
            int startIndex = announcements[i].indexOf(">") + 1;
            announcements[i] = announcements[i].substring(startIndex);
        }
    }
    
}