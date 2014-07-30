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
    private ArrayList<Announcement> announcementList;

    public AnnouncementReconstructor(User user) throws ClientProtocolException,
            IOException {
        this.xmlString = null;
        this.announcement = null;
    }

    public ArrayList<Announcement> constructAnnouncements(String url)
            throws ClientProtocolException, IOException {
        String[] xmlArray = prepareXML(url);
        announcementList = fetchAnnouncements(xmlArray);
        return announcementList;
    }

    private String[] prepareXML(String url) throws ClientProtocolException,
            IOException {
        // setXMLString(url);
        this.xmlString = prepareXML();
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

        for (int i = 0; i < xmlArray.length; i++) {
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
        announcement.setBodyText(parseFromTag("body"));
        announcement.setClassId(parseFromTag("siteId"));
    }

    public String parseDateString() {
        String dateSubTag = getDateLine();
        return getDate(dateSubTag);
    }

    private String getDateLine() {
        String startTag = "<createdOn";
        String endTag = "</createdOn>";
        int startTagLength = xmlString.indexOf(startTag) + startTag.length();
        int endTagIndex = xmlString.indexOf(endTag);
        return xmlString.substring(startTagLength, endTagIndex);
    }

    private String getDate(String dateString) {
        String dateAttritube = "date=\"";
        int startDateLength = dateString.indexOf(dateAttritube)
                + dateAttritube.length();
        int endIndexDate = dateString.indexOf("T");
        return dateString.substring(startDateLength, endIndexDate);
    }
    
    private String prepareXML() {
        return "<announcement_collection entityPrefix=\"announcement\">"
                + "<announcement type=\"bean\" size=\"13\">"
                + "<announcementId>30e50f9d-6e47-4340-b314-f4135cdfc621</announcementId>"
                + "<attachments type=\"collection\" size=\"0\"></attachments>"
                + "<body>"
                + "<p> Dear students:</p> <p> This message will be in English as it is very important. I was just notified that there is a scheduling conflict with our classroom for the final exam. They moved us to the fourth floor. </p> <p> HAM 411 SPAN 1201.001</p> <p> Call me if you have any problems.</p> <p> Cell: 610-420-3822</p> <p> Saludos,</p> <p> Jessica</p>"
                + "</body>" 
                + "<createdByDisplayName>Jessica Gordon-Burroughs</createdByDisplayName>"
                + "<createdOn type=\"date\" date=\"2014-07-02T19:39:24-04:00\">1404344364809</createdOn>"
                + "<id>"
                + "SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</id>"
                + "<siteId>SPANS1201_001_2014_2</siteId>"
                + "<siteTitle>My Workspace</siteTitle>"
                + "<title>FINAL EXAM LOCATION: URGENT</title>"
                + "<entityReference>"
                + "/announcement/SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</entityReference>" 
                + "<entityURL>"
                + "https://courseworks.columbia.edu/direct/announcement/SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</entityURL>"
                + "<entityId>"
                + "SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</entityId>"
                + "<entityTitle>FINAL EXAM LOCATION: URGENT</entityTitle>"
                + "</announcement>"
                + "<announcement type=\"bean\" size=\"13\">"
                + "<announcementId>30e50f9d-6e47-4340-b314-f4135cdfc621</announcementId>"
                + "<attachments type=\"collection\" size=\"0\"></attachments>"
                + "<body>"
                + "<p> Dear students:</p> <p> This message will be in English as it is very important. I was just notified that there is a scheduling conflict with our classroom for the final exam. They moved us to the fourth floor. </p> <p> HAM 411 SPAN 1201.001</p> <p> Call me if you have any problems.</p> <p> Cell: 610-420-3822</p> <p> Saludos,</p> <p> Jessica</p>"
                + "</body>" 
                + "<createdByDisplayName>Jessica Gordon-Burroughs</createdByDisplayName>"
                + "<createdOn type=\"date\" date=\"2014-07-02T19:39:24-04:00\">1404344364809</createdOn>"
                + "<id>"
                + "SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</id>"
                + "<siteId>SPANS1201_001_2014_2</siteId>"
                + "<siteTitle>My Workspace</siteTitle>"
                + "<title>TESTING</title>"
                + "<entityReference>"
                + "/announcement/SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</entityReference>" 
                + "<entityURL>"
                + "https://courseworks.columbia.edu/direct/announcement/SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</entityURL>"
                + "<entityId>"
                + "SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</entityId>"
                + "<entityTitle>FINAL EXAM LOCATION: URGENT</entityTitle>"
                + "</announcement>"
                + "</announcement_collection>";
    }

}