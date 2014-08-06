package com.aisaacroth.courseworks.reconstructors;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import com.aisaacroth.courseworks.structures.Announcement;
import com.aisaacroth.courseworks.utils.TextFormatter;

/**
 * Reconstructs the current User's Announcements XML file returned from the
 * Courseworks API.
 * 
 * @author Alexander Roth
 * @date 2014-07-24
 */
public class AnnouncementReconstructor extends Reconstructor {

    private Announcement announcement;
    private ArrayList<Announcement> announcementList;
    private TextFormatter formatter;

    public AnnouncementReconstructor() throws ClientProtocolException,
            IOException {
        this.announcement = null;
        this.formatter = new TextFormatter();
        this.xmlString = null;
    }

    public ArrayList<Announcement> constructAnnouncements(String url,
            String sessionID) throws ClientProtocolException, IOException {
        String[] xmlArray = prepareXML(url, sessionID);
        announcementList = fetchAnnouncements(xmlArray);
        return announcementList;
    }

    private String[] prepareXML(String url, String sessionID)
            throws ClientProtocolException, IOException {
        setXMLString(url, sessionID);
        String[] xmlArray = parseAnnouncementStrings();
        return xmlArray;
    }

    public String[] parseAnnouncementStrings() {
        removeCollectionTag();
        String[] announcementXMLs = splitAnnouncements();
        removeAnnouncementTag(announcementXMLs);
        return announcementXMLs;
    }

    private void removeCollectionTag() {
        int startIndex = this.xmlString.indexOf(">") + 1;
        int endIndex = this.xmlString.indexOf("</announcement_collection");
        this.xmlString = this.xmlString.substring(startIndex, endIndex);
    }

    private String[] splitAnnouncements() {
        return xmlString.split("</announcement>");
    }

    private void removeAnnouncementTag(String[] announcements) {
        for (int i = 0; i < announcements.length; i++) {
            int startIndex = announcements[i].indexOf(">") + 1;
            announcements[i] = announcements[i].substring(startIndex);
        }
    }

    private ArrayList<Announcement> fetchAnnouncements(String[] xmlArray) {
        ArrayList<Announcement> announcements = new ArrayList<Announcement>();

        for (int i = 0; i < xmlArray.length - 1; i++) {
            this.announcement = new Announcement();
            this.xmlString = xmlArray[i];
            setAnnouncement();
            announcements.add(announcement);
        }
        return announcements;
    }

    private void setAnnouncement() {
        announcement.setPostedDate(parseDateString());
        announcement.setTitle(parseFromTag("title"));
        announcement.setProfessorName(parseFromTag("createdByDisplayName"));
        announcement.setBodyText(formatBodyText());
        announcement.setClassId(parseFromTag("siteId"));
    }

    public String parseDateString() {
        String dateSubTag = getDateLine();
        String dashDate = getDate(dateSubTag);
        String dotDate = removeDots(dashDate);
        return dotDate;
    }

    private String getDateLine() {
        String startTag = "<createdOn";
        String endTag = "</createdOn>";
        int startTagLength = xmlString.indexOf(startTag) + startTag.length();
        int endTagIndex = xmlString.indexOf(endTag);
        String dateLine = xmlString.substring(startTagLength, endTagIndex);
        return dateLine;
    }

    private String getDate(String dateString) {
        String dateAttritube = "date=\'";
        int startDateLength = dateString.indexOf(dateAttritube)
                + dateAttritube.length();
        int endIndexDate = dateString.indexOf("T");
        return dateString.substring(startDateLength, endIndexDate);
    }

    private String removeDots(String dateString) {
        return dateString.replace('-', '.');
    }
    
    private String formatBodyText() {
        String xmlBodyString = parseFromTag("body");
        String cleanBodyText = formatter.formatText(xmlBodyString);
        return cleanBodyText;
    }
}